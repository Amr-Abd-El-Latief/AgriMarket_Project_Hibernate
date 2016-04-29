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
import model.pojo.Category;
import model.pojo.OrderProduct;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import model.JPA.DAO.exceptions.IllegalOrphanException;
import model.JPA.DAO.exceptions.NonexistentEntityException;
import model.JPA.DAO.exceptions.PreexistingEntityException;
import model.pojo.Product;

/**
 *
 * @author Amr
 */
public class ProductJpaController implements Serializable {

    private static EntityManager em;

    public ProductJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public synchronized EntityManager getEntityManager() {
        if (em == null) {
            return emf.createEntityManager();
        } else {
            return em;
        }
    }

    public void create(Product product) throws PreexistingEntityException, Exception {
        if (product.getOrderProductList() == null) {
            product.setOrderProductList(new ArrayList<OrderProduct>());
        }
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Category categoryId = product.getCategoryId();
            if (categoryId != null) {
                categoryId = em.getReference(categoryId.getClass(), categoryId.getId());
                product.setCategoryId(categoryId);
            }
            List<OrderProduct> attachedOrderProductList = new ArrayList<OrderProduct>();
            for (OrderProduct orderProductListOrderProductToAttach : product.getOrderProductList()) {
                orderProductListOrderProductToAttach = em.getReference(orderProductListOrderProductToAttach.getClass(), orderProductListOrderProductToAttach.getOrderProductPK());
                attachedOrderProductList.add(orderProductListOrderProductToAttach);
            }
            product.setOrderProductList(attachedOrderProductList);
            em.persist(product);
            if (categoryId != null) {
                categoryId.getProductList().add(product);
                categoryId = em.merge(categoryId);
            }
            for (OrderProduct orderProductListOrderProduct : product.getOrderProductList()) {
                Product oldProductOfOrderProductListOrderProduct = orderProductListOrderProduct.getProduct();
                orderProductListOrderProduct.setProduct(product);
                orderProductListOrderProduct = em.merge(orderProductListOrderProduct);
                if (oldProductOfOrderProductListOrderProduct != null) {
                    oldProductOfOrderProductListOrderProduct.getOrderProductList().remove(orderProductListOrderProduct);
                    oldProductOfOrderProductListOrderProduct = em.merge(oldProductOfOrderProductListOrderProduct);
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findProduct(product.getName()) != null) {
                throw new PreexistingEntityException("Product " + product + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Product product) throws IllegalOrphanException, NonexistentEntityException, Exception {

        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Product persistentProduct = em.find(Product.class, product.getName());
            Category categoryIdOld = persistentProduct.getCategoryId();
            Category categoryIdNew = product.getCategoryId();
            List<OrderProduct> orderProductListOld = persistentProduct.getOrderProductList();
            List<OrderProduct> orderProductListNew = product.getOrderProductList();
            List<String> illegalOrphanMessages = null;
            for (OrderProduct orderProductListOldOrderProduct : orderProductListOld) {
                if (!orderProductListNew.contains(orderProductListOldOrderProduct)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain OrderProduct " + orderProductListOldOrderProduct + " since its product field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            if (categoryIdNew != null) {
                categoryIdNew = em.getReference(categoryIdNew.getClass(), categoryIdNew.getId());
                product.setCategoryId(categoryIdNew);
            }
            List<OrderProduct> attachedOrderProductListNew = new ArrayList<OrderProduct>();
            for (OrderProduct orderProductListNewOrderProductToAttach : orderProductListNew) {
                orderProductListNewOrderProductToAttach = em.getReference(orderProductListNewOrderProductToAttach.getClass(), orderProductListNewOrderProductToAttach.getOrderProductPK());
                attachedOrderProductListNew.add(orderProductListNewOrderProductToAttach);
            }
            orderProductListNew = attachedOrderProductListNew;
            product.setOrderProductList(orderProductListNew);
            product = em.merge(product);
            if (categoryIdOld != null && !categoryIdOld.equals(categoryIdNew)) {
                categoryIdOld.getProductList().remove(product);
                categoryIdOld = em.merge(categoryIdOld);
            }
            if (categoryIdNew != null && !categoryIdNew.equals(categoryIdOld)) {
                categoryIdNew.getProductList().add(product);
                categoryIdNew = em.merge(categoryIdNew);
            }
            for (OrderProduct orderProductListNewOrderProduct : orderProductListNew) {
                if (!orderProductListOld.contains(orderProductListNewOrderProduct)) {
                    Product oldProductOfOrderProductListNewOrderProduct = orderProductListNewOrderProduct.getProduct();
                    orderProductListNewOrderProduct.setProduct(product);
                    orderProductListNewOrderProduct = em.merge(orderProductListNewOrderProduct);
                    if (oldProductOfOrderProductListNewOrderProduct != null && !oldProductOfOrderProductListNewOrderProduct.equals(product)) {
                        oldProductOfOrderProductListNewOrderProduct.getOrderProductList().remove(orderProductListNewOrderProduct);
                        oldProductOfOrderProductListNewOrderProduct = em.merge(oldProductOfOrderProductListNewOrderProduct);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                String id = product.getName();
                if (findProduct(id) == null) {
                    throw new NonexistentEntityException("The product with id " + id + " no longer exists.");
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
            Product product;
            try {
                product = em.getReference(Product.class, id);
                product.getName();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The product with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            List<OrderProduct> orderProductListOrphanCheck = product.getOrderProductList();
            for (OrderProduct orderProductListOrphanCheckOrderProduct : orderProductListOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Product (" + product + ") cannot be destroyed since the OrderProduct " + orderProductListOrphanCheckOrderProduct + " in its orderProductList field has a non-nullable product field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            Category categoryId = product.getCategoryId();
            if (categoryId != null) {
                categoryId.getProductList().remove(product);
                categoryId = em.merge(categoryId);
            }
            em.remove(product);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Product> findProductEntities() {
        return findProductEntities(true, -1, -1);
    }

    public List<Product> findProductEntities(int maxResults, int firstResult) {
        return findProductEntities(false, maxResults, firstResult);
    }

    private List<Product> findProductEntities(boolean all, int maxResults, int firstResult) {
        em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Product.class));
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

    public Product findProduct(String id) {
        em = getEntityManager();
        try {
            return em.find(Product.class, id);
        } finally {
            em.close();
        }
    }

    public int getProductCount() {
        em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Product> rt = cq.from(Product.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }

}
