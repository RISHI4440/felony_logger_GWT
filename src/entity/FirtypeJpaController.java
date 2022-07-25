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
public class FirtypeJpaController implements Serializable {

    public FirtypeJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Firtype firtype) {
        if (firtype.getFirCollection() == null) {
            firtype.setFirCollection(new ArrayList<Fir>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Collection<Fir> attachedFirCollection = new ArrayList<Fir>();
            for (Fir firCollectionFirToAttach : firtype.getFirCollection()) {
                firCollectionFirToAttach = em.getReference(firCollectionFirToAttach.getClass(), firCollectionFirToAttach.getFirid());
                attachedFirCollection.add(firCollectionFirToAttach);
            }
            firtype.setFirCollection(attachedFirCollection);
            em.persist(firtype);
            for (Fir firCollectionFir : firtype.getFirCollection()) {
                Firtype oldFirtypeidOfFirCollectionFir = firCollectionFir.getFirtypeid();
                firCollectionFir.setFirtypeid(firtype);
                firCollectionFir = em.merge(firCollectionFir);
                if (oldFirtypeidOfFirCollectionFir != null) {
                    oldFirtypeidOfFirCollectionFir.getFirCollection().remove(firCollectionFir);
                    oldFirtypeidOfFirCollectionFir = em.merge(oldFirtypeidOfFirCollectionFir);
                }
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Firtype firtype) throws IllegalOrphanException, NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Firtype persistentFirtype = em.find(Firtype.class, firtype.getFirtypeid());
            Collection<Fir> firCollectionOld = persistentFirtype.getFirCollection();
            Collection<Fir> firCollectionNew = firtype.getFirCollection();
            List<String> illegalOrphanMessages = null;
            for (Fir firCollectionOldFir : firCollectionOld) {
                if (!firCollectionNew.contains(firCollectionOldFir)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Fir " + firCollectionOldFir + " since its firtypeid field is not nullable.");
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
            firtype.setFirCollection(firCollectionNew);
            firtype = em.merge(firtype);
            for (Fir firCollectionNewFir : firCollectionNew) {
                if (!firCollectionOld.contains(firCollectionNewFir)) {
                    Firtype oldFirtypeidOfFirCollectionNewFir = firCollectionNewFir.getFirtypeid();
                    firCollectionNewFir.setFirtypeid(firtype);
                    firCollectionNewFir = em.merge(firCollectionNewFir);
                    if (oldFirtypeidOfFirCollectionNewFir != null && !oldFirtypeidOfFirCollectionNewFir.equals(firtype)) {
                        oldFirtypeidOfFirCollectionNewFir.getFirCollection().remove(firCollectionNewFir);
                        oldFirtypeidOfFirCollectionNewFir = em.merge(oldFirtypeidOfFirCollectionNewFir);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = firtype.getFirtypeid();
                if (findFirtype(id) == null) {
                    throw new NonexistentEntityException("The firtype with id " + id + " no longer exists.");
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
            Firtype firtype;
            try {
                firtype = em.getReference(Firtype.class, id);
                firtype.getFirtypeid();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The firtype with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            Collection<Fir> firCollectionOrphanCheck = firtype.getFirCollection();
            for (Fir firCollectionOrphanCheckFir : firCollectionOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Firtype (" + firtype + ") cannot be destroyed since the Fir " + firCollectionOrphanCheckFir + " in its firCollection field has a non-nullable firtypeid field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            em.remove(firtype);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Firtype> findFirtypeEntities() {
        return findFirtypeEntities(true, -1, -1);
    }

    public List<Firtype> findFirtypeEntities(int maxResults, int firstResult) {
        return findFirtypeEntities(false, maxResults, firstResult);
    }

    private List<Firtype> findFirtypeEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Firtype.class));
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

    public Firtype findFirtype(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Firtype.class, id);
        } finally {
            em.close();
        }
    }

    public int getFirtypeCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Firtype> rt = cq.from(Firtype.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
