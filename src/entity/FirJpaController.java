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
public class FirJpaController implements Serializable {

    public FirJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Fir fir) {
        if (fir.getPostmortamCollection() == null) {
            fir.setPostmortamCollection(new ArrayList<Postmortam>());
        }
        if (fir.getChargesheetCollection() == null) {
            fir.setChargesheetCollection(new ArrayList<Chargesheet>());
        }
        if (fir.getFirdetailCollection() == null) {
            fir.setFirdetailCollection(new ArrayList<Firdetail>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Firtype firtypeid = fir.getFirtypeid();
            if (firtypeid != null) {
                firtypeid = em.getReference(firtypeid.getClass(), firtypeid.getFirtypeid());
                fir.setFirtypeid(firtypeid);
            }
            Firregion firregionid = fir.getFirregionid();
            if (firregionid != null) {
                firregionid = em.getReference(firregionid.getClass(), firregionid.getFirregionid());
                fir.setFirregionid(firregionid);
            }
            Crimetype crimetypeid = fir.getCrimetypeid();
            if (crimetypeid != null) {
                crimetypeid = em.getReference(crimetypeid.getClass(), crimetypeid.getCrimetypeid());
                fir.setCrimetypeid(crimetypeid);
            }
            Collection<Postmortam> attachedPostmortamCollection = new ArrayList<Postmortam>();
            for (Postmortam postmortamCollectionPostmortamToAttach : fir.getPostmortamCollection()) {
                postmortamCollectionPostmortamToAttach = em.getReference(postmortamCollectionPostmortamToAttach.getClass(), postmortamCollectionPostmortamToAttach.getPmid());
                attachedPostmortamCollection.add(postmortamCollectionPostmortamToAttach);
            }
            fir.setPostmortamCollection(attachedPostmortamCollection);
            Collection<Chargesheet> attachedChargesheetCollection = new ArrayList<Chargesheet>();
            for (Chargesheet chargesheetCollectionChargesheetToAttach : fir.getChargesheetCollection()) {
                chargesheetCollectionChargesheetToAttach = em.getReference(chargesheetCollectionChargesheetToAttach.getClass(), chargesheetCollectionChargesheetToAttach.getChargesheetid());
                attachedChargesheetCollection.add(chargesheetCollectionChargesheetToAttach);
            }
            fir.setChargesheetCollection(attachedChargesheetCollection);
            Collection<Firdetail> attachedFirdetailCollection = new ArrayList<Firdetail>();
            for (Firdetail firdetailCollectionFirdetailToAttach : fir.getFirdetailCollection()) {
                firdetailCollectionFirdetailToAttach = em.getReference(firdetailCollectionFirdetailToAttach.getClass(), firdetailCollectionFirdetailToAttach.getFirdid());
                attachedFirdetailCollection.add(firdetailCollectionFirdetailToAttach);
            }
            fir.setFirdetailCollection(attachedFirdetailCollection);
            em.persist(fir);
            if (firtypeid != null) {
                firtypeid.getFirCollection().add(fir);
                firtypeid = em.merge(firtypeid);
            }
            if (firregionid != null) {
                firregionid.getFirCollection().add(fir);
                firregionid = em.merge(firregionid);
            }
            if (crimetypeid != null) {
                crimetypeid.getFirCollection().add(fir);
                crimetypeid = em.merge(crimetypeid);
            }
            for (Postmortam postmortamCollectionPostmortam : fir.getPostmortamCollection()) {
                Fir oldFiridOfPostmortamCollectionPostmortam = postmortamCollectionPostmortam.getFirid();
                postmortamCollectionPostmortam.setFirid(fir);
                postmortamCollectionPostmortam = em.merge(postmortamCollectionPostmortam);
                if (oldFiridOfPostmortamCollectionPostmortam != null) {
                    oldFiridOfPostmortamCollectionPostmortam.getPostmortamCollection().remove(postmortamCollectionPostmortam);
                    oldFiridOfPostmortamCollectionPostmortam = em.merge(oldFiridOfPostmortamCollectionPostmortam);
                }
            }
            for (Chargesheet chargesheetCollectionChargesheet : fir.getChargesheetCollection()) {
                Fir oldFiridOfChargesheetCollectionChargesheet = chargesheetCollectionChargesheet.getFirid();
                chargesheetCollectionChargesheet.setFirid(fir);
                chargesheetCollectionChargesheet = em.merge(chargesheetCollectionChargesheet);
                if (oldFiridOfChargesheetCollectionChargesheet != null) {
                    oldFiridOfChargesheetCollectionChargesheet.getChargesheetCollection().remove(chargesheetCollectionChargesheet);
                    oldFiridOfChargesheetCollectionChargesheet = em.merge(oldFiridOfChargesheetCollectionChargesheet);
                }
            }
            for (Firdetail firdetailCollectionFirdetail : fir.getFirdetailCollection()) {
                Fir oldFiridOfFirdetailCollectionFirdetail = firdetailCollectionFirdetail.getFirid();
                firdetailCollectionFirdetail.setFirid(fir);
                firdetailCollectionFirdetail = em.merge(firdetailCollectionFirdetail);
                if (oldFiridOfFirdetailCollectionFirdetail != null) {
                    oldFiridOfFirdetailCollectionFirdetail.getFirdetailCollection().remove(firdetailCollectionFirdetail);
                    oldFiridOfFirdetailCollectionFirdetail = em.merge(oldFiridOfFirdetailCollectionFirdetail);
                }
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Fir fir) throws IllegalOrphanException, NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Fir persistentFir = em.find(Fir.class, fir.getFirid());
            Firtype firtypeidOld = persistentFir.getFirtypeid();
            Firtype firtypeidNew = fir.getFirtypeid();
            Firregion firregionidOld = persistentFir.getFirregionid();
            Firregion firregionidNew = fir.getFirregionid();
            Crimetype crimetypeidOld = persistentFir.getCrimetypeid();
            Crimetype crimetypeidNew = fir.getCrimetypeid();
            Collection<Postmortam> postmortamCollectionOld = persistentFir.getPostmortamCollection();
            Collection<Postmortam> postmortamCollectionNew = fir.getPostmortamCollection();
            Collection<Chargesheet> chargesheetCollectionOld = persistentFir.getChargesheetCollection();
            Collection<Chargesheet> chargesheetCollectionNew = fir.getChargesheetCollection();
            Collection<Firdetail> firdetailCollectionOld = persistentFir.getFirdetailCollection();
            Collection<Firdetail> firdetailCollectionNew = fir.getFirdetailCollection();
            List<String> illegalOrphanMessages = null;
            for (Postmortam postmortamCollectionOldPostmortam : postmortamCollectionOld) {
                if (!postmortamCollectionNew.contains(postmortamCollectionOldPostmortam)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Postmortam " + postmortamCollectionOldPostmortam + " since its firid field is not nullable.");
                }
            }
            for (Chargesheet chargesheetCollectionOldChargesheet : chargesheetCollectionOld) {
                if (!chargesheetCollectionNew.contains(chargesheetCollectionOldChargesheet)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Chargesheet " + chargesheetCollectionOldChargesheet + " since its firid field is not nullable.");
                }
            }
            for (Firdetail firdetailCollectionOldFirdetail : firdetailCollectionOld) {
                if (!firdetailCollectionNew.contains(firdetailCollectionOldFirdetail)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Firdetail " + firdetailCollectionOldFirdetail + " since its firid field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            if (firtypeidNew != null) {
                firtypeidNew = em.getReference(firtypeidNew.getClass(), firtypeidNew.getFirtypeid());
                fir.setFirtypeid(firtypeidNew);
            }
            if (firregionidNew != null) {
                firregionidNew = em.getReference(firregionidNew.getClass(), firregionidNew.getFirregionid());
                fir.setFirregionid(firregionidNew);
            }
            if (crimetypeidNew != null) {
                crimetypeidNew = em.getReference(crimetypeidNew.getClass(), crimetypeidNew.getCrimetypeid());
                fir.setCrimetypeid(crimetypeidNew);
            }
            Collection<Postmortam> attachedPostmortamCollectionNew = new ArrayList<Postmortam>();
            for (Postmortam postmortamCollectionNewPostmortamToAttach : postmortamCollectionNew) {
                postmortamCollectionNewPostmortamToAttach = em.getReference(postmortamCollectionNewPostmortamToAttach.getClass(), postmortamCollectionNewPostmortamToAttach.getPmid());
                attachedPostmortamCollectionNew.add(postmortamCollectionNewPostmortamToAttach);
            }
            postmortamCollectionNew = attachedPostmortamCollectionNew;
            fir.setPostmortamCollection(postmortamCollectionNew);
            Collection<Chargesheet> attachedChargesheetCollectionNew = new ArrayList<Chargesheet>();
            for (Chargesheet chargesheetCollectionNewChargesheetToAttach : chargesheetCollectionNew) {
                chargesheetCollectionNewChargesheetToAttach = em.getReference(chargesheetCollectionNewChargesheetToAttach.getClass(), chargesheetCollectionNewChargesheetToAttach.getChargesheetid());
                attachedChargesheetCollectionNew.add(chargesheetCollectionNewChargesheetToAttach);
            }
            chargesheetCollectionNew = attachedChargesheetCollectionNew;
            fir.setChargesheetCollection(chargesheetCollectionNew);
            Collection<Firdetail> attachedFirdetailCollectionNew = new ArrayList<Firdetail>();
            for (Firdetail firdetailCollectionNewFirdetailToAttach : firdetailCollectionNew) {
                firdetailCollectionNewFirdetailToAttach = em.getReference(firdetailCollectionNewFirdetailToAttach.getClass(), firdetailCollectionNewFirdetailToAttach.getFirdid());
                attachedFirdetailCollectionNew.add(firdetailCollectionNewFirdetailToAttach);
            }
            firdetailCollectionNew = attachedFirdetailCollectionNew;
            fir.setFirdetailCollection(firdetailCollectionNew);
            fir = em.merge(fir);
            if (firtypeidOld != null && !firtypeidOld.equals(firtypeidNew)) {
                firtypeidOld.getFirCollection().remove(fir);
                firtypeidOld = em.merge(firtypeidOld);
            }
            if (firtypeidNew != null && !firtypeidNew.equals(firtypeidOld)) {
                firtypeidNew.getFirCollection().add(fir);
                firtypeidNew = em.merge(firtypeidNew);
            }
            if (firregionidOld != null && !firregionidOld.equals(firregionidNew)) {
                firregionidOld.getFirCollection().remove(fir);
                firregionidOld = em.merge(firregionidOld);
            }
            if (firregionidNew != null && !firregionidNew.equals(firregionidOld)) {
                firregionidNew.getFirCollection().add(fir);
                firregionidNew = em.merge(firregionidNew);
            }
            if (crimetypeidOld != null && !crimetypeidOld.equals(crimetypeidNew)) {
                crimetypeidOld.getFirCollection().remove(fir);
                crimetypeidOld = em.merge(crimetypeidOld);
            }
            if (crimetypeidNew != null && !crimetypeidNew.equals(crimetypeidOld)) {
                crimetypeidNew.getFirCollection().add(fir);
                crimetypeidNew = em.merge(crimetypeidNew);
            }
            for (Postmortam postmortamCollectionNewPostmortam : postmortamCollectionNew) {
                if (!postmortamCollectionOld.contains(postmortamCollectionNewPostmortam)) {
                    Fir oldFiridOfPostmortamCollectionNewPostmortam = postmortamCollectionNewPostmortam.getFirid();
                    postmortamCollectionNewPostmortam.setFirid(fir);
                    postmortamCollectionNewPostmortam = em.merge(postmortamCollectionNewPostmortam);
                    if (oldFiridOfPostmortamCollectionNewPostmortam != null && !oldFiridOfPostmortamCollectionNewPostmortam.equals(fir)) {
                        oldFiridOfPostmortamCollectionNewPostmortam.getPostmortamCollection().remove(postmortamCollectionNewPostmortam);
                        oldFiridOfPostmortamCollectionNewPostmortam = em.merge(oldFiridOfPostmortamCollectionNewPostmortam);
                    }
                }
            }
            for (Chargesheet chargesheetCollectionNewChargesheet : chargesheetCollectionNew) {
                if (!chargesheetCollectionOld.contains(chargesheetCollectionNewChargesheet)) {
                    Fir oldFiridOfChargesheetCollectionNewChargesheet = chargesheetCollectionNewChargesheet.getFirid();
                    chargesheetCollectionNewChargesheet.setFirid(fir);
                    chargesheetCollectionNewChargesheet = em.merge(chargesheetCollectionNewChargesheet);
                    if (oldFiridOfChargesheetCollectionNewChargesheet != null && !oldFiridOfChargesheetCollectionNewChargesheet.equals(fir)) {
                        oldFiridOfChargesheetCollectionNewChargesheet.getChargesheetCollection().remove(chargesheetCollectionNewChargesheet);
                        oldFiridOfChargesheetCollectionNewChargesheet = em.merge(oldFiridOfChargesheetCollectionNewChargesheet);
                    }
                }
            }
            for (Firdetail firdetailCollectionNewFirdetail : firdetailCollectionNew) {
                if (!firdetailCollectionOld.contains(firdetailCollectionNewFirdetail)) {
                    Fir oldFiridOfFirdetailCollectionNewFirdetail = firdetailCollectionNewFirdetail.getFirid();
                    firdetailCollectionNewFirdetail.setFirid(fir);
                    firdetailCollectionNewFirdetail = em.merge(firdetailCollectionNewFirdetail);
                    if (oldFiridOfFirdetailCollectionNewFirdetail != null && !oldFiridOfFirdetailCollectionNewFirdetail.equals(fir)) {
                        oldFiridOfFirdetailCollectionNewFirdetail.getFirdetailCollection().remove(firdetailCollectionNewFirdetail);
                        oldFiridOfFirdetailCollectionNewFirdetail = em.merge(oldFiridOfFirdetailCollectionNewFirdetail);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = fir.getFirid();
                if (findFir(id) == null) {
                    throw new NonexistentEntityException("The fir with id " + id + " no longer exists.");
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
            Fir fir;
            try {
                fir = em.getReference(Fir.class, id);
                fir.getFirid();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The fir with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            Collection<Postmortam> postmortamCollectionOrphanCheck = fir.getPostmortamCollection();
            for (Postmortam postmortamCollectionOrphanCheckPostmortam : postmortamCollectionOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Fir (" + fir + ") cannot be destroyed since the Postmortam " + postmortamCollectionOrphanCheckPostmortam + " in its postmortamCollection field has a non-nullable firid field.");
            }
            Collection<Chargesheet> chargesheetCollectionOrphanCheck = fir.getChargesheetCollection();
            for (Chargesheet chargesheetCollectionOrphanCheckChargesheet : chargesheetCollectionOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Fir (" + fir + ") cannot be destroyed since the Chargesheet " + chargesheetCollectionOrphanCheckChargesheet + " in its chargesheetCollection field has a non-nullable firid field.");
            }
            Collection<Firdetail> firdetailCollectionOrphanCheck = fir.getFirdetailCollection();
            for (Firdetail firdetailCollectionOrphanCheckFirdetail : firdetailCollectionOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Fir (" + fir + ") cannot be destroyed since the Firdetail " + firdetailCollectionOrphanCheckFirdetail + " in its firdetailCollection field has a non-nullable firid field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            Firtype firtypeid = fir.getFirtypeid();
            if (firtypeid != null) {
                firtypeid.getFirCollection().remove(fir);
                firtypeid = em.merge(firtypeid);
            }
            Firregion firregionid = fir.getFirregionid();
            if (firregionid != null) {
                firregionid.getFirCollection().remove(fir);
                firregionid = em.merge(firregionid);
            }
            Crimetype crimetypeid = fir.getCrimetypeid();
            if (crimetypeid != null) {
                crimetypeid.getFirCollection().remove(fir);
                crimetypeid = em.merge(crimetypeid);
            }
            em.remove(fir);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Fir> findFirEntities() {
        return findFirEntities(true, -1, -1);
    }

    public List<Fir> findFirEntities(int maxResults, int firstResult) {
        return findFirEntities(false, maxResults, firstResult);
    }

    private List<Fir> findFirEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Fir.class));
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

    public Fir findFir(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Fir.class, id);
        } finally {
            em.close();
        }
    }

    public int getFirCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Fir> rt = cq.from(Fir.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
