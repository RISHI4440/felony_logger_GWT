/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package entity;

import entity.exceptions.IllegalOrphanException;
import entity.exceptions.NonexistentEntityException;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author admin
 */
public class ChargesheetJpaController implements Serializable {

    public ChargesheetJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Chargesheet chargesheet) {
        if (chargesheet.getPrisonerCollection() == null) {
            chargesheet.setPrisonerCollection(new ArrayList<Prisoner>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Fir firid = chargesheet.getFirid();
            if (firid != null) {
                firid = em.getReference(firid.getClass(), firid.getFirid());
                chargesheet.setFirid(firid);
            }
            Collection<Prisoner> attachedPrisonerCollection = new ArrayList<Prisoner>();
            for (Prisoner prisonerCollectionPrisonerToAttach : chargesheet.getPrisonerCollection()) {
                prisonerCollectionPrisonerToAttach = em.getReference(prisonerCollectionPrisonerToAttach.getClass(), prisonerCollectionPrisonerToAttach.getPrisonerid());
                attachedPrisonerCollection.add(prisonerCollectionPrisonerToAttach);
            }
            chargesheet.setPrisonerCollection(attachedPrisonerCollection);
            em.persist(chargesheet);
            if (firid != null) {
                firid.getChargesheetCollection().add(chargesheet);
                firid = em.merge(firid);
            }
            for (Prisoner prisonerCollectionPrisoner : chargesheet.getPrisonerCollection()) {
                Chargesheet oldChargesheetidOfPrisonerCollectionPrisoner = prisonerCollectionPrisoner.getChargesheetid();
                prisonerCollectionPrisoner.setChargesheetid(chargesheet);
                prisonerCollectionPrisoner = em.merge(prisonerCollectionPrisoner);
                if (oldChargesheetidOfPrisonerCollectionPrisoner != null) {
                    oldChargesheetidOfPrisonerCollectionPrisoner.getPrisonerCollection().remove(prisonerCollectionPrisoner);
                    oldChargesheetidOfPrisonerCollectionPrisoner = em.merge(oldChargesheetidOfPrisonerCollectionPrisoner);
                }
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Chargesheet chargesheet) throws IllegalOrphanException, NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Chargesheet persistentChargesheet = em.find(Chargesheet.class, chargesheet.getChargesheetid());
            Fir firidOld = persistentChargesheet.getFirid();
            Fir firidNew = chargesheet.getFirid();
            Collection<Prisoner> prisonerCollectionOld = persistentChargesheet.getPrisonerCollection();
            Collection<Prisoner> prisonerCollectionNew = chargesheet.getPrisonerCollection();
            List<String> illegalOrphanMessages = null;
            for (Prisoner prisonerCollectionOldPrisoner : prisonerCollectionOld) {
                if (!prisonerCollectionNew.contains(prisonerCollectionOldPrisoner)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Prisoner " + prisonerCollectionOldPrisoner + " since its chargesheetid field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            if (firidNew != null) {
                firidNew = em.getReference(firidNew.getClass(), firidNew.getFirid());
                chargesheet.setFirid(firidNew);
            }
            Collection<Prisoner> attachedPrisonerCollectionNew = new ArrayList<Prisoner>();
            for (Prisoner prisonerCollectionNewPrisonerToAttach : prisonerCollectionNew) {
                prisonerCollectionNewPrisonerToAttach = em.getReference(prisonerCollectionNewPrisonerToAttach.getClass(), prisonerCollectionNewPrisonerToAttach.getPrisonerid());
                attachedPrisonerCollectionNew.add(prisonerCollectionNewPrisonerToAttach);
            }
            prisonerCollectionNew = attachedPrisonerCollectionNew;
            chargesheet.setPrisonerCollection(prisonerCollectionNew);
            chargesheet = em.merge(chargesheet);
            if (firidOld != null && !firidOld.equals(firidNew)) {
                firidOld.getChargesheetCollection().remove(chargesheet);
                firidOld = em.merge(firidOld);
            }
            if (firidNew != null && !firidNew.equals(firidOld)) {
                firidNew.getChargesheetCollection().add(chargesheet);
                firidNew = em.merge(firidNew);
            }
            for (Prisoner prisonerCollectionNewPrisoner : prisonerCollectionNew) {
                if (!prisonerCollectionOld.contains(prisonerCollectionNewPrisoner)) {
                    Chargesheet oldChargesheetidOfPrisonerCollectionNewPrisoner = prisonerCollectionNewPrisoner.getChargesheetid();
                    prisonerCollectionNewPrisoner.setChargesheetid(chargesheet);
                    prisonerCollectionNewPrisoner = em.merge(prisonerCollectionNewPrisoner);
                    if (oldChargesheetidOfPrisonerCollectionNewPrisoner != null && !oldChargesheetidOfPrisonerCollectionNewPrisoner.equals(chargesheet)) {
                        oldChargesheetidOfPrisonerCollectionNewPrisoner.getPrisonerCollection().remove(prisonerCollectionNewPrisoner);
                        oldChargesheetidOfPrisonerCollectionNewPrisoner = em.merge(oldChargesheetidOfPrisonerCollectionNewPrisoner);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = chargesheet.getChargesheetid();
                if (findChargesheet(id) == null) {
                    throw new NonexistentEntityException("The chargesheet with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void destroy(Integer id) throws IllegalOrphanException, NonexistentEntityException {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Chargesheet chargesheet;
            try {
                chargesheet = em.getReference(Chargesheet.class, id);
                chargesheet.getChargesheetid();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The chargesheet with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            Collection<Prisoner> prisonerCollectionOrphanCheck = chargesheet.getPrisonerCollection();
            for (Prisoner prisonerCollectionOrphanCheckPrisoner : prisonerCollectionOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Chargesheet (" + chargesheet + ") cannot be destroyed since the Prisoner " + prisonerCollectionOrphanCheckPrisoner + " in its prisonerCollection field has a non-nullable chargesheetid field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            Fir firid = chargesheet.getFirid();
            if (firid != null) {
                firid.getChargesheetCollection().remove(chargesheet);
                firid = em.merge(firid);
            }
            em.remove(chargesheet);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Chargesheet> findChargesheetEntities() {
        return findChargesheetEntities(true, -1, -1);
    }

    public List<Chargesheet> findChargesheetEntities(int maxResults, int firstResult) {
        return findChargesheetEntities(false, maxResults, firstResult);
    }

    private List<Chargesheet> findChargesheetEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Chargesheet.class));
            Query q = em.createQuery(cq);
            if (!all) {
                q.setMaxResults(maxResults);
                q.setFirstResult(firstResult);
            }
            return q.getResultList();
        } finally {
            em.close();
        }
    }

    public Chargesheet findChargesheet(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Chargesheet.class, id);
        } finally {
            em.close();
        }
    }

    public int getChargesheetCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Chargesheet> rt = cq.from(Chargesheet.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
