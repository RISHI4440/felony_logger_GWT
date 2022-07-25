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
public class FirdetailJpaController implements Serializable {

    public FirdetailJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Firdetail firdetail) {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Fir firid = firdetail.getFirid();
            if (firid != null) {
                firid = em.getReference(firid.getClass(), firid.getFirid());
                firdetail.setFirid(firid);
            }
            em.persist(firdetail);
            if (firid != null) {
                firid.getFirdetailCollection().add(firdetail);
                firid = em.merge(firid);
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Firdetail firdetail) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Firdetail persistentFirdetail = em.find(Firdetail.class, firdetail.getFirdid());
            Fir firidOld = persistentFirdetail.getFirid();
            Fir firidNew = firdetail.getFirid();
            if (firidNew != null) {
                firidNew = em.getReference(firidNew.getClass(), firidNew.getFirid());
                firdetail.setFirid(firidNew);
            }
            firdetail = em.merge(firdetail);
            if (firidOld != null && !firidOld.equals(firidNew)) {
                firidOld.getFirdetailCollection().remove(firdetail);
                firidOld = em.merge(firidOld);
            }
            if (firidNew != null && !firidNew.equals(firidOld)) {
                firidNew.getFirdetailCollection().add(firdetail);
                firidNew = em.merge(firidNew);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = firdetail.getFirdid();
                if (findFirdetail(id) == null) {
                    throw new NonexistentEntityException("The firdetail with id " + id + " no longer exists.");
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
            Firdetail firdetail;
            try {
                firdetail = em.getReference(Firdetail.class, id);
                firdetail.getFirdid();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The firdetail with id " + id + " no longer exists.", enfe);
            }
            Fir firid = firdetail.getFirid();
            if (firid != null) {
                firid.getFirdetailCollection().remove(firdetail);
                firid = em.merge(firid);
            }
            em.remove(firdetail);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Firdetail> findFirdetailEntities() {
        return findFirdetailEntities(true, -1, -1);
    }

    public List<Firdetail> findFirdetailEntities(int maxResults, int firstResult) {
        return findFirdetailEntities(false, maxResults, firstResult);
    }

    private List<Firdetail> findFirdetailEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Firdetail.class));
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

    public Firdetail findFirdetail(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Firdetail.class, id);
        } finally {
            em.close();
        }
    }

    public int getFirdetailCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Firdetail> rt = cq.from(Firdetail.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
