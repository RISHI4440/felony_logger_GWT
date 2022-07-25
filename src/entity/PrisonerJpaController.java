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
public class PrisonerJpaController implements Serializable {

    public PrisonerJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Prisoner prisoner) {
        if (prisoner.getHostoryCollection() == null) {
            prisoner.setHostoryCollection(new ArrayList<Hostory>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Chargesheet chargesheetid = prisoner.getChargesheetid();
            if (chargesheetid != null) {
                chargesheetid = em.getReference(chargesheetid.getClass(), chargesheetid.getChargesheetid());
                prisoner.setChargesheetid(chargesheetid);
            }
            Collection<Hostory> attachedHostoryCollection = new ArrayList<Hostory>();
            for (Hostory hostoryCollectionHostoryToAttach : prisoner.getHostoryCollection()) {
                hostoryCollectionHostoryToAttach = em.getReference(hostoryCollectionHostoryToAttach.getClass(), hostoryCollectionHostoryToAttach.getHistoryid());
                attachedHostoryCollection.add(hostoryCollectionHostoryToAttach);
            }
            prisoner.setHostoryCollection(attachedHostoryCollection);
            em.persist(prisoner);
            if (chargesheetid != null) {
                chargesheetid.getPrisonerCollection().add(prisoner);
                chargesheetid = em.merge(chargesheetid);
            }
            for (Hostory hostoryCollectionHostory : prisoner.getHostoryCollection()) {
                Prisoner oldPrisoneridOfHostoryCollectionHostory = hostoryCollectionHostory.getPrisonerid();
                hostoryCollectionHostory.setPrisonerid(prisoner);
                hostoryCollectionHostory = em.merge(hostoryCollectionHostory);
                if (oldPrisoneridOfHostoryCollectionHostory != null) {
                    oldPrisoneridOfHostoryCollectionHostory.getHostoryCollection().remove(hostoryCollectionHostory);
                    oldPrisoneridOfHostoryCollectionHostory = em.merge(oldPrisoneridOfHostoryCollectionHostory);
                }
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Prisoner prisoner) throws IllegalOrphanException, NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Prisoner persistentPrisoner = em.find(Prisoner.class, prisoner.getPrisonerid());
            Chargesheet chargesheetidOld = persistentPrisoner.getChargesheetid();
            Chargesheet chargesheetidNew = prisoner.getChargesheetid();
            Collection<Hostory> hostoryCollectionOld = persistentPrisoner.getHostoryCollection();
            Collection<Hostory> hostoryCollectionNew = prisoner.getHostoryCollection();
            List<String> illegalOrphanMessages = null;
            for (Hostory hostoryCollectionOldHostory : hostoryCollectionOld) {
                if (!hostoryCollectionNew.contains(hostoryCollectionOldHostory)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Hostory " + hostoryCollectionOldHostory + " since its prisonerid field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            if (chargesheetidNew != null) {
                chargesheetidNew = em.getReference(chargesheetidNew.getClass(), chargesheetidNew.getChargesheetid());
                prisoner.setChargesheetid(chargesheetidNew);
            }
            Collection<Hostory> attachedHostoryCollectionNew = new ArrayList<Hostory>();
            for (Hostory hostoryCollectionNewHostoryToAttach : hostoryCollectionNew) {
                hostoryCollectionNewHostoryToAttach = em.getReference(hostoryCollectionNewHostoryToAttach.getClass(), hostoryCollectionNewHostoryToAttach.getHistoryid());
                attachedHostoryCollectionNew.add(hostoryCollectionNewHostoryToAttach);
            }
            hostoryCollectionNew = attachedHostoryCollectionNew;
            prisoner.setHostoryCollection(hostoryCollectionNew);
            prisoner = em.merge(prisoner);
            if (chargesheetidOld != null && !chargesheetidOld.equals(chargesheetidNew)) {
                chargesheetidOld.getPrisonerCollection().remove(prisoner);
                chargesheetidOld = em.merge(chargesheetidOld);
            }
            if (chargesheetidNew != null && !chargesheetidNew.equals(chargesheetidOld)) {
                chargesheetidNew.getPrisonerCollection().add(prisoner);
                chargesheetidNew = em.merge(chargesheetidNew);
            }
            for (Hostory hostoryCollectionNewHostory : hostoryCollectionNew) {
                if (!hostoryCollectionOld.contains(hostoryCollectionNewHostory)) {
                    Prisoner oldPrisoneridOfHostoryCollectionNewHostory = hostoryCollectionNewHostory.getPrisonerid();
                    hostoryCollectionNewHostory.setPrisonerid(prisoner);
                    hostoryCollectionNewHostory = em.merge(hostoryCollectionNewHostory);
                    if (oldPrisoneridOfHostoryCollectionNewHostory != null && !oldPrisoneridOfHostoryCollectionNewHostory.equals(prisoner)) {
                        oldPrisoneridOfHostoryCollectionNewHostory.getHostoryCollection().remove(hostoryCollectionNewHostory);
                        oldPrisoneridOfHostoryCollectionNewHostory = em.merge(oldPrisoneridOfHostoryCollectionNewHostory);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = prisoner.getPrisonerid();
                if (findPrisoner(id) == null) {
                    throw new NonexistentEntityException("The prisoner with id " + id + " no longer exists.");
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
            Prisoner prisoner;
            try {
                prisoner = em.getReference(Prisoner.class, id);
                prisoner.getPrisonerid();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The prisoner with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            Collection<Hostory> hostoryCollectionOrphanCheck = prisoner.getHostoryCollection();
            for (Hostory hostoryCollectionOrphanCheckHostory : hostoryCollectionOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Prisoner (" + prisoner + ") cannot be destroyed since the Hostory " + hostoryCollectionOrphanCheckHostory + " in its hostoryCollection field has a non-nullable prisonerid field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            Chargesheet chargesheetid = prisoner.getChargesheetid();
            if (chargesheetid != null) {
                chargesheetid.getPrisonerCollection().remove(prisoner);
                chargesheetid = em.merge(chargesheetid);
            }
            em.remove(prisoner);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Prisoner> findPrisonerEntities() {
        return findPrisonerEntities(true, -1, -1);
    }

    public List<Prisoner> findPrisonerEntities(int maxResults, int firstResult) {
        return findPrisonerEntities(false, maxResults, firstResult);
    }

    private List<Prisoner> findPrisonerEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Prisoner.class));
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

    public Prisoner findPrisoner(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Prisoner.class, id);
        } finally {
            em.close();
        }
    }

    public int getPrisonerCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Prisoner> rt = cq.from(Prisoner.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
