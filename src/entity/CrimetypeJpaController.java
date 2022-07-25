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
public class CrimetypeJpaController implements Serializable {

    public CrimetypeJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Crimetype crimetype) {
        if (crimetype.getCriminalCollection() == null) {
            crimetype.setCriminalCollection(new ArrayList<Criminal>());
        }
        if (crimetype.getHostoryCollection() == null) {
            crimetype.setHostoryCollection(new ArrayList<Hostory>());
        }
        if (crimetype.getFirCollection() == null) {
            crimetype.setFirCollection(new ArrayList<Fir>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Collection<Criminal> attachedCriminalCollection = new ArrayList<Criminal>();
            for (Criminal criminalCollectionCriminalToAttach : crimetype.getCriminalCollection()) {
                criminalCollectionCriminalToAttach = em.getReference(criminalCollectionCriminalToAttach.getClass(), criminalCollectionCriminalToAttach.getCriminalid());
                attachedCriminalCollection.add(criminalCollectionCriminalToAttach);
            }
            crimetype.setCriminalCollection(attachedCriminalCollection);
            Collection<Hostory> attachedHostoryCollection = new ArrayList<Hostory>();
            for (Hostory hostoryCollectionHostoryToAttach : crimetype.getHostoryCollection()) {
                hostoryCollectionHostoryToAttach = em.getReference(hostoryCollectionHostoryToAttach.getClass(), hostoryCollectionHostoryToAttach.getHistoryid());
                attachedHostoryCollection.add(hostoryCollectionHostoryToAttach);
            }
            crimetype.setHostoryCollection(attachedHostoryCollection);
            Collection<Fir> attachedFirCollection = new ArrayList<Fir>();
            for (Fir firCollectionFirToAttach : crimetype.getFirCollection()) {
                firCollectionFirToAttach = em.getReference(firCollectionFirToAttach.getClass(), firCollectionFirToAttach.getFirid());
                attachedFirCollection.add(firCollectionFirToAttach);
            }
            crimetype.setFirCollection(attachedFirCollection);
            em.persist(crimetype);
            for (Criminal criminalCollectionCriminal : crimetype.getCriminalCollection()) {
                Crimetype oldCrimetypeidOfCriminalCollectionCriminal = criminalCollectionCriminal.getCrimetypeid();
                criminalCollectionCriminal.setCrimetypeid(crimetype);
                criminalCollectionCriminal = em.merge(criminalCollectionCriminal);
                if (oldCrimetypeidOfCriminalCollectionCriminal != null) {
                    oldCrimetypeidOfCriminalCollectionCriminal.getCriminalCollection().remove(criminalCollectionCriminal);
                    oldCrimetypeidOfCriminalCollectionCriminal = em.merge(oldCrimetypeidOfCriminalCollectionCriminal);
                }
            }
            for (Hostory hostoryCollectionHostory : crimetype.getHostoryCollection()) {
                Crimetype oldCrimetypeidOfHostoryCollectionHostory = hostoryCollectionHostory.getCrimetypeid();
                hostoryCollectionHostory.setCrimetypeid(crimetype);
                hostoryCollectionHostory = em.merge(hostoryCollectionHostory);
                if (oldCrimetypeidOfHostoryCollectionHostory != null) {
                    oldCrimetypeidOfHostoryCollectionHostory.getHostoryCollection().remove(hostoryCollectionHostory);
                    oldCrimetypeidOfHostoryCollectionHostory = em.merge(oldCrimetypeidOfHostoryCollectionHostory);
                }
            }
            for (Fir firCollectionFir : crimetype.getFirCollection()) {
                Crimetype oldCrimetypeidOfFirCollectionFir = firCollectionFir.getCrimetypeid();
                firCollectionFir.setCrimetypeid(crimetype);
                firCollectionFir = em.merge(firCollectionFir);
                if (oldCrimetypeidOfFirCollectionFir != null) {
                    oldCrimetypeidOfFirCollectionFir.getFirCollection().remove(firCollectionFir);
                    oldCrimetypeidOfFirCollectionFir = em.merge(oldCrimetypeidOfFirCollectionFir);
                }
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Crimetype crimetype) throws IllegalOrphanException, NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Crimetype persistentCrimetype = em.find(Crimetype.class, crimetype.getCrimetypeid());
            Collection<Criminal> criminalCollectionOld = persistentCrimetype.getCriminalCollection();
            Collection<Criminal> criminalCollectionNew = crimetype.getCriminalCollection();
            Collection<Hostory> hostoryCollectionOld = persistentCrimetype.getHostoryCollection();
            Collection<Hostory> hostoryCollectionNew = crimetype.getHostoryCollection();
            Collection<Fir> firCollectionOld = persistentCrimetype.getFirCollection();
            Collection<Fir> firCollectionNew = crimetype.getFirCollection();
            List<String> illegalOrphanMessages = null;
            for (Criminal criminalCollectionOldCriminal : criminalCollectionOld) {
                if (!criminalCollectionNew.contains(criminalCollectionOldCriminal)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Criminal " + criminalCollectionOldCriminal + " since its crimetypeid field is not nullable.");
                }
            }
            for (Hostory hostoryCollectionOldHostory : hostoryCollectionOld) {
                if (!hostoryCollectionNew.contains(hostoryCollectionOldHostory)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Hostory " + hostoryCollectionOldHostory + " since its crimetypeid field is not nullable.");
                }
            }
            for (Fir firCollectionOldFir : firCollectionOld) {
                if (!firCollectionNew.contains(firCollectionOldFir)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Fir " + firCollectionOldFir + " since its crimetypeid field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            Collection<Criminal> attachedCriminalCollectionNew = new ArrayList<Criminal>();
            for (Criminal criminalCollectionNewCriminalToAttach : criminalCollectionNew) {
                criminalCollectionNewCriminalToAttach = em.getReference(criminalCollectionNewCriminalToAttach.getClass(), criminalCollectionNewCriminalToAttach.getCriminalid());
                attachedCriminalCollectionNew.add(criminalCollectionNewCriminalToAttach);
            }
            criminalCollectionNew = attachedCriminalCollectionNew;
            crimetype.setCriminalCollection(criminalCollectionNew);
            Collection<Hostory> attachedHostoryCollectionNew = new ArrayList<Hostory>();
            for (Hostory hostoryCollectionNewHostoryToAttach : hostoryCollectionNew) {
                hostoryCollectionNewHostoryToAttach = em.getReference(hostoryCollectionNewHostoryToAttach.getClass(), hostoryCollectionNewHostoryToAttach.getHistoryid());
                attachedHostoryCollectionNew.add(hostoryCollectionNewHostoryToAttach);
            }
            hostoryCollectionNew = attachedHostoryCollectionNew;
            crimetype.setHostoryCollection(hostoryCollectionNew);
            Collection<Fir> attachedFirCollectionNew = new ArrayList<Fir>();
            for (Fir firCollectionNewFirToAttach : firCollectionNew) {
                firCollectionNewFirToAttach = em.getReference(firCollectionNewFirToAttach.getClass(), firCollectionNewFirToAttach.getFirid());
                attachedFirCollectionNew.add(firCollectionNewFirToAttach);
            }
            firCollectionNew = attachedFirCollectionNew;
            crimetype.setFirCollection(firCollectionNew);
            crimetype = em.merge(crimetype);
            for (Criminal criminalCollectionNewCriminal : criminalCollectionNew) {
                if (!criminalCollectionOld.contains(criminalCollectionNewCriminal)) {
                    Crimetype oldCrimetypeidOfCriminalCollectionNewCriminal = criminalCollectionNewCriminal.getCrimetypeid();
                    criminalCollectionNewCriminal.setCrimetypeid(crimetype);
                    criminalCollectionNewCriminal = em.merge(criminalCollectionNewCriminal);
                    if (oldCrimetypeidOfCriminalCollectionNewCriminal != null && !oldCrimetypeidOfCriminalCollectionNewCriminal.equals(crimetype)) {
                        oldCrimetypeidOfCriminalCollectionNewCriminal.getCriminalCollection().remove(criminalCollectionNewCriminal);
                        oldCrimetypeidOfCriminalCollectionNewCriminal = em.merge(oldCrimetypeidOfCriminalCollectionNewCriminal);
                    }
                }
            }
            for (Hostory hostoryCollectionNewHostory : hostoryCollectionNew) {
                if (!hostoryCollectionOld.contains(hostoryCollectionNewHostory)) {
                    Crimetype oldCrimetypeidOfHostoryCollectionNewHostory = hostoryCollectionNewHostory.getCrimetypeid();
                    hostoryCollectionNewHostory.setCrimetypeid(crimetype);
                    hostoryCollectionNewHostory = em.merge(hostoryCollectionNewHostory);
                    if (oldCrimetypeidOfHostoryCollectionNewHostory != null && !oldCrimetypeidOfHostoryCollectionNewHostory.equals(crimetype)) {
                        oldCrimetypeidOfHostoryCollectionNewHostory.getHostoryCollection().remove(hostoryCollectionNewHostory);
                        oldCrimetypeidOfHostoryCollectionNewHostory = em.merge(oldCrimetypeidOfHostoryCollectionNewHostory);
                    }
                }
            }
            for (Fir firCollectionNewFir : firCollectionNew) {
                if (!firCollectionOld.contains(firCollectionNewFir)) {
                    Crimetype oldCrimetypeidOfFirCollectionNewFir = firCollectionNewFir.getCrimetypeid();
                    firCollectionNewFir.setCrimetypeid(crimetype);
                    firCollectionNewFir = em.merge(firCollectionNewFir);
                    if (oldCrimetypeidOfFirCollectionNewFir != null && !oldCrimetypeidOfFirCollectionNewFir.equals(crimetype)) {
                        oldCrimetypeidOfFirCollectionNewFir.getFirCollection().remove(firCollectionNewFir);
                        oldCrimetypeidOfFirCollectionNewFir = em.merge(oldCrimetypeidOfFirCollectionNewFir);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = crimetype.getCrimetypeid();
                if (findCrimetype(id) == null) {
                    throw new NonexistentEntityException("The crimetype with id " + id + " no longer exists.");
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
            Crimetype crimetype;
            try {
                crimetype = em.getReference(Crimetype.class, id);
                crimetype.getCrimetypeid();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The crimetype with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            Collection<Criminal> criminalCollectionOrphanCheck = crimetype.getCriminalCollection();
            for (Criminal criminalCollectionOrphanCheckCriminal : criminalCollectionOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Crimetype (" + crimetype + ") cannot be destroyed since the Criminal " + criminalCollectionOrphanCheckCriminal + " in its criminalCollection field has a non-nullable crimetypeid field.");
            }
            Collection<Hostory> hostoryCollectionOrphanCheck = crimetype.getHostoryCollection();
            for (Hostory hostoryCollectionOrphanCheckHostory : hostoryCollectionOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Crimetype (" + crimetype + ") cannot be destroyed since the Hostory " + hostoryCollectionOrphanCheckHostory + " in its hostoryCollection field has a non-nullable crimetypeid field.");
            }
            Collection<Fir> firCollectionOrphanCheck = crimetype.getFirCollection();
            for (Fir firCollectionOrphanCheckFir : firCollectionOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Crimetype (" + crimetype + ") cannot be destroyed since the Fir " + firCollectionOrphanCheckFir + " in its firCollection field has a non-nullable crimetypeid field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            em.remove(crimetype);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Crimetype> findCrimetypeEntities() {
        return findCrimetypeEntities(true, -1, -1);
    }

    public List<Crimetype> findCrimetypeEntities(int maxResults, int firstResult) {
        return findCrimetypeEntities(false, maxResults, firstResult);
    }

    private List<Crimetype> findCrimetypeEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Crimetype.class));
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

    public Crimetype findCrimetype(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Crimetype.class, id);
        } finally {
            em.close();
        }
    }

    public int getCrimetypeCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Crimetype> rt = cq.from(Crimetype.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
