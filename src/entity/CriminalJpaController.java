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
public class CriminalJpaController implements Serializable {

    public CriminalJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Criminal criminal) {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Crimetype crimetypeid = criminal.getCrimetypeid();
            if (crimetypeid != null) {
                crimetypeid = em.getReference(crimetypeid.getClass(), crimetypeid.getCrimetypeid());
                criminal.setCrimetypeid(crimetypeid);
            }
            em.persist(criminal);
            if (crimetypeid != null) {
                crimetypeid.getCriminalCollection().add(criminal);
                crimetypeid = em.merge(crimetypeid);
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Criminal criminal) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Criminal persistentCriminal = em.find(Criminal.class, criminal.getCriminalid());
            Crimetype crimetypeidOld = persistentCriminal.getCrimetypeid();
            Crimetype crimetypeidNew = criminal.getCrimetypeid();
            if (crimetypeidNew != null) {
                crimetypeidNew = em.getReference(crimetypeidNew.getClass(), crimetypeidNew.getCrimetypeid());
                criminal.setCrimetypeid(crimetypeidNew);
            }
            criminal = em.merge(criminal);
            if (crimetypeidOld != null && !crimetypeidOld.equals(crimetypeidNew)) {
                crimetypeidOld.getCriminalCollection().remove(criminal);
                crimetypeidOld = em.merge(crimetypeidOld);
            }
            if (crimetypeidNew != null && !crimetypeidNew.equals(crimetypeidOld)) {
                crimetypeidNew.getCriminalCollection().add(criminal);
                crimetypeidNew = em.merge(crimetypeidNew);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = criminal.getCriminalid();
                if (findCriminal(id) == null) {
                    throw new NonexistentEntityException("The criminal with id " + id + " no longer exists.");
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
            Criminal criminal;
            try {
                criminal = em.getReference(Criminal.class, id);
                criminal.getCriminalid();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The criminal with id " + id + " no longer exists.", enfe);
            }
            Crimetype crimetypeid = criminal.getCrimetypeid();
            if (crimetypeid != null) {
                crimetypeid.getCriminalCollection().remove(criminal);
                crimetypeid = em.merge(crimetypeid);
            }
            em.remove(criminal);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Criminal> findCriminalEntities() {
        return findCriminalEntities(true, -1, -1);
    }

    public List<Criminal> findCriminalEntities(int maxResults, int firstResult) {
        return findCriminalEntities(false, maxResults, firstResult);
    }

    private List<Criminal> findCriminalEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Criminal.class));
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

    public Criminal findCriminal(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Criminal.class, id);
        } finally {
            em.close();
        }
    }

    public int getCriminalCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Criminal> rt = cq.from(Criminal.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
