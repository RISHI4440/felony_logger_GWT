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
public class PostmortamJpaController implements Serializable {

    public PostmortamJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Postmortam postmortam) {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Fir firid = postmortam.getFirid();
            if (firid != null) {
                firid = em.getReference(firid.getClass(), firid.getFirid());
                postmortam.setFirid(firid);
            }
            em.persist(postmortam);
            if (firid != null) {
                firid.getPostmortamCollection().add(postmortam);
                firid = em.merge(firid);
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Postmortam postmortam) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Postmortam persistentPostmortam = em.find(Postmortam.class, postmortam.getPmid());
            Fir firidOld = persistentPostmortam.getFirid();
            Fir firidNew = postmortam.getFirid();
            if (firidNew != null) {
                firidNew = em.getReference(firidNew.getClass(), firidNew.getFirid());
                postmortam.setFirid(firidNew);
            }
            postmortam = em.merge(postmortam);
            if (firidOld != null && !firidOld.equals(firidNew)) {
                firidOld.getPostmortamCollection().remove(postmortam);
                firidOld = em.merge(firidOld);
            }
            if (firidNew != null && !firidNew.equals(firidOld)) {
                firidNew.getPostmortamCollection().add(postmortam);
                firidNew = em.merge(firidNew);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = postmortam.getPmid();
                if (findPostmortam(id) == null) {
                    throw new NonexistentEntityException("The postmortam with id " + id + " no longer exists.");
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
            Postmortam postmortam;
            try {
                postmortam = em.getReference(Postmortam.class, id);
                postmortam.getPmid();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The postmortam with id " + id + " no longer exists.", enfe);
            }
            Fir firid = postmortam.getFirid();
            if (firid != null) {
                firid.getPostmortamCollection().remove(postmortam);
                firid = em.merge(firid);
            }
            em.remove(postmortam);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Postmortam> findPostmortamEntities() {
        return findPostmortamEntities(true, -1, -1);
    }

    public List<Postmortam> findPostmortamEntities(int maxResults, int firstResult) {
        return findPostmortamEntities(false, maxResults, firstResult);
    }

    private List<Postmortam> findPostmortamEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Postmortam.class));
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

    public Postmortam findPostmortam(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Postmortam.class, id);
        } finally {
            em.close();
        }
    }

    public int getPostmortamCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Postmortam> rt = cq.from(Postmortam.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
