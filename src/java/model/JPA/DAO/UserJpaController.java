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
import model.pojo.Interests;
import model.pojo.Credit;
import model.pojo.Order1;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import model.JPA.DAO.exceptions.IllegalOrphanException;
import model.JPA.DAO.exceptions.NonexistentEntityException;
import model.JPA.DAO.exceptions.PreexistingEntityException;
import model.pojo.User;

/**
 *
 * @author Amr
 */
public class UserJpaController implements Serializable {

    private static EntityManager em;

    public UserJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public synchronized EntityManager getEntityManager() {
        if (em == null) {
            return emf.createEntityManager();
        }
        return em;
    }

    public void create(User user) throws IllegalOrphanException, PreexistingEntityException, Exception {
        if (user.getOrder1List() == null) {
            user.setOrder1List(new ArrayList<Order1>());
        }
        List<String> illegalOrphanMessages = null;
        Credit creditNumberOrphanCheck = user.getCreditNumber();
        if (creditNumberOrphanCheck != null) {
            User oldUserOfCreditNumber = creditNumberOrphanCheck.getUser();
            if (oldUserOfCreditNumber != null) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("The Credit " + creditNumberOrphanCheck + " already has an item of type User whose creditNumber column cannot be null. Please make another selection for the creditNumber field.");
            }
        }
        if (illegalOrphanMessages != null) {
            throw new IllegalOrphanException(illegalOrphanMessages);
        }
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Interests interests = user.getInterests();
            if (interests != null) {
                interests = em.getReference(interests.getClass(), interests.getEmail());
                user.setInterests(interests);
            }
            Credit creditNumber = user.getCreditNumber();
            if (creditNumber != null) {
                creditNumber = em.getReference(creditNumber.getClass(), creditNumber.getNumber());
                user.setCreditNumber(creditNumber);
            }
            List<Order1> attachedOrder1List = new ArrayList<Order1>();
            for (Order1 order1ListOrder1ToAttach : user.getOrder1List()) {
                order1ListOrder1ToAttach = em.getReference(order1ListOrder1ToAttach.getClass(), order1ListOrder1ToAttach.getId());
                attachedOrder1List.add(order1ListOrder1ToAttach);
            }
            user.setOrder1List(attachedOrder1List);
            em.persist(user);
            if (interests != null) {
                User oldUserOfInterests = interests.getUser();
                if (oldUserOfInterests != null) {
                    oldUserOfInterests.setInterests(null);
                    oldUserOfInterests = em.merge(oldUserOfInterests);
                }
                interests.setUser(user);
                interests = em.merge(interests);
            }
            if (creditNumber != null) {
                creditNumber.setUser(user);
                creditNumber = em.merge(creditNumber);
            }
            for (Order1 order1ListOrder1 : user.getOrder1List()) {
                User oldUserEmailOfOrder1ListOrder1 = order1ListOrder1.getUserEmail();
                order1ListOrder1.setUserEmail(user);
                order1ListOrder1 = em.merge(order1ListOrder1);
                if (oldUserEmailOfOrder1ListOrder1 != null) {
                    oldUserEmailOfOrder1ListOrder1.getOrder1List().remove(order1ListOrder1);
                    oldUserEmailOfOrder1ListOrder1 = em.merge(oldUserEmailOfOrder1ListOrder1);
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findUser(user.getEmail()) != null) {
                throw new PreexistingEntityException("User " + user + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(User user) throws IllegalOrphanException, NonexistentEntityException, Exception {
        
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            User persistentUser = em.find(User.class, user.getEmail());
            Interests interestsOld = persistentUser.getInterests();
            Interests interestsNew = user.getInterests();
            Credit creditNumberOld = persistentUser.getCreditNumber();
            Credit creditNumberNew = user.getCreditNumber();
            List<Order1> order1ListOld = persistentUser.getOrder1List();
            List<Order1> order1ListNew = user.getOrder1List();
            List<String> illegalOrphanMessages = null;
            if (interestsOld != null && !interestsOld.equals(interestsNew)) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("You must retain Interests " + interestsOld + " since its user field is not nullable.");
            }
            if (creditNumberNew != null && !creditNumberNew.equals(creditNumberOld)) {
                User oldUserOfCreditNumber = creditNumberNew.getUser();
                if (oldUserOfCreditNumber != null) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("The Credit " + creditNumberNew + " already has an item of type User whose creditNumber column cannot be null. Please make another selection for the creditNumber field.");
                }
            }
            for (Order1 order1ListOldOrder1 : order1ListOld) {
                if (!order1ListNew.contains(order1ListOldOrder1)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Order1 " + order1ListOldOrder1 + " since its userEmail field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            if (interestsNew != null) {
                interestsNew = em.getReference(interestsNew.getClass(), interestsNew.getEmail());
                user.setInterests(interestsNew);
            }
            if (creditNumberNew != null) {
                creditNumberNew = em.getReference(creditNumberNew.getClass(), creditNumberNew.getNumber());
                user.setCreditNumber(creditNumberNew);
            }
            List<Order1> attachedOrder1ListNew = new ArrayList<Order1>();
            for (Order1 order1ListNewOrder1ToAttach : order1ListNew) {
                order1ListNewOrder1ToAttach = em.getReference(order1ListNewOrder1ToAttach.getClass(), order1ListNewOrder1ToAttach.getId());
                attachedOrder1ListNew.add(order1ListNewOrder1ToAttach);
            }
            order1ListNew = attachedOrder1ListNew;
            user.setOrder1List(order1ListNew);
            user = em.merge(user);
            if (interestsNew != null && !interestsNew.equals(interestsOld)) {
                User oldUserOfInterests = interestsNew.getUser();
                if (oldUserOfInterests != null) {
                    oldUserOfInterests.setInterests(null);
                    oldUserOfInterests = em.merge(oldUserOfInterests);
                }
                interestsNew.setUser(user);
                interestsNew = em.merge(interestsNew);
            }
            if (creditNumberOld != null && !creditNumberOld.equals(creditNumberNew)) {
                creditNumberOld.setUser(null);
                creditNumberOld = em.merge(creditNumberOld);
            }
            if (creditNumberNew != null && !creditNumberNew.equals(creditNumberOld)) {
                creditNumberNew.setUser(user);
                creditNumberNew = em.merge(creditNumberNew);
            }
            for (Order1 order1ListNewOrder1 : order1ListNew) {
                if (!order1ListOld.contains(order1ListNewOrder1)) {
                    User oldUserEmailOfOrder1ListNewOrder1 = order1ListNewOrder1.getUserEmail();
                    order1ListNewOrder1.setUserEmail(user);
                    order1ListNewOrder1 = em.merge(order1ListNewOrder1);
                    if (oldUserEmailOfOrder1ListNewOrder1 != null && !oldUserEmailOfOrder1ListNewOrder1.equals(user)) {
                        oldUserEmailOfOrder1ListNewOrder1.getOrder1List().remove(order1ListNewOrder1);
                        oldUserEmailOfOrder1ListNewOrder1 = em.merge(oldUserEmailOfOrder1ListNewOrder1);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                String id = user.getEmail();
                if (findUser(id) == null) {
                    throw new NonexistentEntityException("The user with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void destroy(String id) throws IllegalOrphanException, NonexistentEntityException {
        
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            User user;
            try {
                user = em.getReference(User.class, id);
                user.getEmail();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The user with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            Interests interestsOrphanCheck = user.getInterests();
            if (interestsOrphanCheck != null) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This User (" + user + ") cannot be destroyed since the Interests " + interestsOrphanCheck + " in its interests field has a non-nullable user field.");
            }
            List<Order1> order1ListOrphanCheck = user.getOrder1List();
            for (Order1 order1ListOrphanCheckOrder1 : order1ListOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This User (" + user + ") cannot be destroyed since the Order1 " + order1ListOrphanCheckOrder1 + " in its order1List field has a non-nullable userEmail field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            Credit creditNumber = user.getCreditNumber();
            if (creditNumber != null) {
                creditNumber.setUser(null);
                creditNumber = em.merge(creditNumber);
            }
            em.remove(user);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<User> findUserEntities() {
        return findUserEntities(true, -1, -1);
    }

    public List<User> findUserEntities(int maxResults, int firstResult) {
        return findUserEntities(false, maxResults, firstResult);
    }

    private List<User> findUserEntities(boolean all, int maxResults, int firstResult) {
        em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(User.class));
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

    public User findUser(String id) {
        em = getEntityManager();
        try {
            return em.find(User.class, id);
        } finally {
            em.close();
        }
    }

    public int getUserCount() {
        em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<User> rt = cq.from(User.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }

}
