/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package entity;

import entity.exceptions.NonexistentEntityException;
import java.io.Serializable;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

/**
 *
 * @author admin
 */
public class HostoryJpaController implements Serializable {

    public HostoryJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Hostory hostory) {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Prisoner prisonerid = hostory.getPrisonerid();
            if (prisonerid != null) {
                prisonerid = em.getReference(prisonerid.getClass(), prisonerid.getPrisonerid());
                hostory.setPrisonerid(prisonerid);
            }
            Crimetype crimetypeid = hostory.getCrimetypeid();
            if (crimetypeid != null) {
                crimetypeid = em.getReference(crimetypeid.getClass(), crimetypeid.getCrimetypeid());
                hostory.setCrimetypeid(crimetypeid);
            }
            em.persist(hostory);
            if (prisonerid != null) {
                prisonerid.getHostoryCollection().add(hostory);
                prisonerid = em.merge(prisonerid);
            }
            if (crimetypeid != null) {
                crimetypeid.getHostoryCollection().add(hostory);
                crimetypeid = em.merge(crimetypeid);
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Hostory hostory) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Hostory persistentHostory = em.find(Hostory.class, hostory.getHistoryid());
            Prisoner prisoneridOld = persistentHostory.getPrisonerid();
            Prisoner prisoneridNew = hostory.getPrisonerid();
            Crimetype crimetypeidOld = persistentHostory.getCrimetypeid();
            Crimetype crimetypeidNew = hostory.getCrimetypeid();
            if (prisoneridNew != null) {
                prisoneridNew = em.getReference(prisoneridNew.getClass(), prisoneridNew.getPrisonerid());
                hostory.setPrisonerid(prisoneridNew);
            }
            if (crimetypeidNew != null) {
                crimetypeidNew = em.getReference(crimetypeidNew.getClass(), crimetypeidNew.getCrimetypeid());
                hostory.setCrimetypeid(crimetypeidNew);
            }
            hostory = em.merge(hostory);
            if (prisoneridOld != null && !prisoneridOld.equals(prisoneridNew)) {
                prisoneridOld.getHostoryCollection().remove(hostory);
                prisoneridOld = em.merge(prisoneridOld);
            }
            if (prisoneridNew != null && !prisoneridNew.equals(prisoneridOld)) {
                prisoneridNew.getHostoryCollection().add(hostory);
                prisoneridNew = em.merge(prisoneridNew);
            }
            if (crimetypeidOld != null && !crimetypeidOld.equals(crimetypeidNew)) {
                crimetypeidOld.getHostoryCollection().remove(hostory);
                crimetypeidOld = em.merge(crimetypeidOld);
            }
            if (crimetypeidNew != null && !crimetypeidNew.equals(crimetypeidOld)) {
                crimetypeidNew.getHostoryCollection().add(hostory);
                crimetypeidNew = em.merge(crimetypeidNew);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = hostory.getHistoryid();
                if (findHostory(id) == null) {
                    throw new NonexistentEntityException("The hostory with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void destroy(Integer id) throws NonexistentEntityException {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Hostory hostory;
            try {
                hostory = em.getReference(Hostory.class, id);
                hostory.getHistoryid();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The hostory with id " + id + " no longer exists.", enfe);
            }
            Prisoner prisonerid = hostory.getPrisonerid();
            if (prisonerid != null) {
                prisonerid.getHostoryCollection().remove(hostory);
                prisonerid = em.merge(prisonerid);
            }
            Crimetype crimetypeid = hostory.getCrimetypeid();
            if (crimetypeid != null) {
                crimetypeid.getHostoryCollection().remove(hostory);
                crimetypeid = em.merge(crimetypeid);
            }
            em.remove(hostory);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Hostory> findHostoryEntities() {
        return findHostoryEntities(true, -1, -1);
    }

    public List<Hostory> findHostoryEntities(int maxResults, int firstResult) {
        return findHostoryEntities(false, maxResults, firstResult);
    }

    private List<Hostory> findHostoryEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Hostory.class));
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

    public Hostory findHostory(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Hostory.class, id);
        } finally {
            em.close();
        }
    }

    public int getHostoryCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Hostory> rt = cq.from(Hostory.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
