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
public class FirregionJpaController implements Serializable {

    public FirregionJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Firregion firregion) {
        if (firregion.getFirCollection() == null) {
            firregion.setFirCollection(new ArrayList<Fir>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Collection<Fir> attachedFirCollection = new ArrayList<Fir>();
            for (Fir firCollectionFirToAttach : firregion.getFirCollection()) {
                firCollectionFirToAttach = em.getReference(firCollectionFirToAttach.getClass(), firCollectionFirToAttach.getFirid());
                attachedFirCollection.add(firCollectionFirToAttach);
            }
            firregion.setFirCollection(attachedFirCollection);
            em.persist(firregion);
            for (Fir firCollectionFir : firregion.getFirCollection()) {
                Firregion oldFirregionidOfFirCollectionFir = firCollectionFir.getFirregionid();
                firCollectionFir.setFirregionid(firregion);
                firCollectionFir = em.merge(firCollectionFir);
                if (oldFirregionidOfFirCollectionFir != null) {
                    oldFirregionidOfFirCollectionFir.getFirCollection().remove(firCollectionFir);
                    oldFirregionidOfFirCollectionFir = em.merge(oldFirregionidOfFirCollectionFir);
                }
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Firregion firregion) throws IllegalOrphanException, NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Firregion persistentFirregion = em.find(Firregion.class, firregion.getFirregionid());
            Collection<Fir> firCollectionOld = persistentFirregion.getFirCollection();
            Collection<Fir> firCollectionNew = firregion.getFirCollection();
            List<String> illegalOrphanMessages = null;
            for (Fir firCollectionOldFir : firCollectionOld) {
                if (!firCollectionNew.contains(firCollectionOldFir)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Fir " + firCollectionOldFir + " since its firregionid field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            Collection<Fir> attachedFirCollectionNew = new ArrayList<Fir>();
            for (Fir firCollectionNewFirToAttach : firCollectionNew) {
                firCollectionNewFirToAttach = em.getReference(firCollectionNewFirToAttach.getClass(), firCollectionNewFirToAttach.getFirid());
                attachedFirCollectionNew.add(firCollectionNewFirToAttach);
            }
            firCollectionNew = attachedFirCollectionNew;
            firregion.setFirCollection(firCollectionNew);
            firregion = em.merge(firregion);
            for (Fir firCollectionNewFir : firCollectionNew) {
                if (!firCollectionOld.contains(firCollectionNewFir)) {
                    Firregion oldFirregionidOfFirCollectionNewFir = firCollectionNewFir.getFirregionid();
                    firCollectionNewFir.setFirregionid(firregion);
                    firCollectionNewFir = em.merge(firCollectionNewFir);
                    if (oldFirregionidOfFirCollectionNewFir != null && !oldFirregionidOfFirCollectionNewFir.equals(firregion)) {
                        oldFirregionidOfFirCollectionNewFir.getFirCollection().remove(firCollectionNewFir);
                        oldFirregionidOfFirCollectionNewFir = em.merge(oldFirregionidOfFirCollectionNewFir);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = firregion.getFirregionid();
                if (findFirregion(id) == null) {
                    throw new NonexistentEntityException("The firregion with id " + id + " no longer exists.");
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
            Firregion firregion;
            try {
                firregion = em.getReference(Firregion.class, id);
                firregion.getFirregionid();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The firregion with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            Collection<Fir> firCollectionOrphanCheck = firregion.getFirCollection();
            for (Fir firCollectionOrphanCheckFir : firCollectionOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Firregion (" + firregion + ") cannot be destroyed since the Fir " + firCollectionOrphanCheckFir + " in its firCollection field has a non-nullable firregionid field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            em.remove(firregion);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Firregion> findFirregionEntities() {
        return findFirregionEntities(true, -1, -1);
    }

    public List<Firregion> findFirregionEntities(int maxResults, int firstResult) {
        return findFirregionEntities(false, maxResults, firstResult);
    }

    private List<Firregion> findFirregionEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Firregion.class));
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

    public Firregion findFirregion(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Firregion.class, id);
        } finally {
            em.close();
        }
    }

    public int getFirregionCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Firregion> rt = cq.from(Firregion.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
