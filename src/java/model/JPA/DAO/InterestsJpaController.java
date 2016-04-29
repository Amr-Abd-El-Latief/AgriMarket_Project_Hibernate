/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model.JPA.DAO;

import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import model.pojo.User;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import model.JPA.DAO.exceptions.IllegalOrphanException;
import model.JPA.DAO.exceptions.NonexistentEntityException;
import model.JPA.DAO.exceptions.PreexistingEntityException;
import model.pojo.Interests;

/**
 *
 * @author Amr
 */
public class InterestsJpaController implements Serializable {

    private static EntityManager em ;

    public InterestsJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public synchronized EntityManager getEntityManager() {
        if (em == null) {
            return emf.createEntityManager();
        }
        return em;
    }

    public void create(Interests interests) throws IllegalOrphanException, PreexistingEntityException, Exception {
        List<String> illegalOrphanMessages = null;
        User userOrphanCheck = interests.getUser();
        if (userOrphanCheck != null) {
            Interests oldInterestsOfUser = userOrphanCheck.getInterests();
            if (oldInterestsOfUser != null) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("The User " + userOrphanCheck + " already has an item of type Interests whose user column cannot be null. Please make another selection for the user field.");
            }
        }
        if (illegalOrphanMessages != null) {
            throw new IllegalOrphanException(illegalOrphanMessages);
        }
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            User user = interests.getUser();
            if (user != null) {
                user = em.getReference(user.getClass(), user.getEmail());
                interests.setUser(user);
            }
            em.persist(interests);
            if (user != null) {
                user.setInterests(interests);
                user = em.merge(user);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findInterests(interests.getEmail()) != null) {
                throw new PreexistingEntityException("Interests " + interests + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Interests interests) throws IllegalOrphanException, NonexistentEntityException, Exception {
        
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Interests persistentInterests = em.find(Interests.class, interests.getEmail());
            User userOld = persistentInterests.getUser();
            User userNew = interests.getUser();
            List<String> illegalOrphanMessages = null;
            if (userNew != null && !userNew.equals(userOld)) {
                Interests oldInterestsOfUser = userNew.getInterests();
                if (oldInterestsOfUser != null) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("The User " + userNew + " already has an item of type Interests whose user column cannot be null. Please make another selection for the user field.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            if (userNew != null) {
                userNew = em.getReference(userNew.getClass(), userNew.getEmail());
                interests.setUser(userNew);
            }
            interests = em.merge(interests);
            if (userOld != null && !userOld.equals(userNew)) {
                userOld.setInterests(null);
                userOld = em.merge(userOld);
            }
            if (userNew != null && !userNew.equals(userOld)) {
                userNew.setInterests(interests);
                userNew = em.merge(userNew);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                String id = interests.getEmail();
                if (findInterests(id) == null) {
                    throw new NonexistentEntityException("The interests with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void destroy(String id) throws NonexistentEntityException {
        
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Interests interests;
            try {
                interests = em.getReference(Interests.class, id);
                interests.getEmail();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The interests with id " + id + " no longer exists.", enfe);
            }
            User user = interests.getUser();
            if (user != null) {
                user.setInterests(null);
                user = em.merge(user);
            }
            em.remove(interests);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Interests> findInterestsEntities() {
        return findInterestsEntities(true, -1, -1);
    }

    public List<Interests> findInterestsEntities(int maxResults, int firstResult) {
        return findInterestsEntities(false, maxResults, firstResult);
    }

    private List<Interests> findInterestsEntities(boolean all, int maxResults, int firstResult) {
        em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Interests.class));
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

    public Interests findInterests(String id) {
        em = getEntityManager();
        try {
            return em.find(Interests.class, id);
        } finally {
            em.close();
        }
    }

    public int getInterestsCount() {
        em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Interests> rt = cq.from(Interests.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }

}
