/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model.DAOImp;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import model.DAO.OrderDao;
import model.dto.Order;
import model.dto.Product;
import model.dto.ProductPerOrder;
import util.JdbcConnection;

/**
 *
 * @author muhammad
 */
public class OrderDaoImp implements OrderDao {

    Connection con;

    public OrderDaoImp() {
        try {
            con = JdbcConnection.getConnection();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    public ArrayList<Order> getAllOrders(String userEmail) {
        System.out.println("Get All Orderd");
        ArrayList<Order> userOrders = new ArrayList();
        try {
            Statement ordersStatements = con.createStatement();
            ResultSet ordersResult = ordersStatements.executeQuery("select * from agri_project.order where user_email= \"" + userEmail + "\"");

            System.out.println("select * from agri_project.order where user_email= \"" + userEmail + "\"");
            while (ordersResult.next()) {
                Order order = getOrder(ordersResult.getInt("id"));
                System.out.println("Order="+order);
                if (order != null) {
                    userOrders.add(order);
                }
            }
        } catch (SQLException ex) {
            Logger.getLogger(UserDaoImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
        return userOrders;
    }

    @Override
    public Order getOrder(int orderId) {
        System.out.println("Get Order");
        try {
            Statement orderStatement = con.createStatement();
            ResultSet orderResult = orderStatement.executeQuery("select * from agri_project.order where id=" + orderId);
            System.out.println("select * from agri_project.order where id=" + orderId);
            if (orderResult.next()) {
                Order order = new Order();
                System.out.println("Order"+orderId);
                order.setId(orderId);
                order.setStatus(orderResult.getString("status"));
                order.setDate(orderResult.getDate("date").toLocalDate());
                order.setUser_email(orderResult.getString("user_email"));
                ProductDaoImp products = new ProductDaoImp();
                order.setProducts(products.getAllProducts(orderId));
                
                int total=0;
                for (Product p : order.getProducts()) {
                    total += p.getQuantity() * p.getPrice();
                }
                order.setTotal(total);
                
                order.setItems(getOrderItems(orderId));
                return order;
            }
        } catch (SQLException ex) {
            Logger.getLogger(OrderDaoImp.class.getName()).log(Level.SEVERE, null, ex);
        }

        return null;
    }

//        public ArrayList<Order> getAllOrders(String userEmail) {
//        ArrayList<Order> userOrders = new ArrayList();
//        try {
//            Statement ordersStatements = con.createStatement();
//            ResultSet ordersResult = ordersStatements.executeQuery("select distinct order_id from agri_project.order_product where user_email='" + userEmail + "';");
//
//            while (ordersResult.next()) {
//                double total = 0;
//                Order order = getOrder(ordersResult.getInt("order_id"));
//                ProductDaoImp product = new ProductDaoImp();
//                ArrayList<Product> products = product.getAllProducts(userEmail, ordersResult.getInt("order_id"));
//                for (Product p : products) {
//                    total += p.getQuantity() * p.getPrice();
//                }
//                order.setProducts(products);
//                order.setTotal(total);
//                userOrders.add(order);
//            }
//            return userOrders;
//        } catch (SQLException ex) {
//            Logger.getLogger(UserDaoImpl.class.getName()).log(Level.SEVERE, null, ex);
//        }
//        return null;
//    }
    @Override
    public boolean addOrder(Order order) {
        try {
            PreparedStatement pst = con.prepareStatement("insert into agri_project.order (date,status,user_email) values (?,?,?)");
            pst.setDate(1, Date.valueOf(order.getDate()));
            pst.setString(2, order.getStatus());
            pst.setString(3, order.getUser_email());
            int result = pst.executeUpdate();
            if (result > 0) {
                return true;
            }
        } catch (SQLException ex) {
            Logger.getLogger(OrderDaoImp.class
                    .getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }

    public boolean updateItemQuantity(ProductPerOrder order, int order_id) {
        try {
            PreparedStatement pst = con.prepareStatement("update order_product set quantity=?  where order_id =? and product_id= ?");
            pst.setInt(1, order.getQuantity());
            pst.setInt(2, order_id);
            pst.setString(3, order.getProduct().getName());
            int result = pst.executeUpdate();

            if (result == 0) {
                return false;
            }
        } catch (SQLException ex) {
            Logger.getLogger(OrderDaoImp.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }

        return true;
    }

    public Order getCart(String userMail) {
        try {
            Statement orderStatement = con.createStatement();
            ResultSet orderResult = orderStatement.executeQuery("select id from agri_project.order where user_email='" + userMail + "' and status = 'cart' ");
            if (orderResult.next()) {
                Order order = getOrder(orderResult.getInt("id"));
                return order;
            } else {
                Order orderNew = new Order();
                orderNew.setDate(LocalDate.now());
                orderNew.setUser_email(userMail);
                orderNew.setStatus("cart");
                if (addOrder(orderNew)) {
                    return getCart(userMail);
                }
            }

        } catch (SQLException ex) {
            Logger.getLogger(OrderDaoImp.class.getName()).log(Level.SEVERE, null, ex);
        }

        return null;
    }

    public Order submitOrder(Order order) {
        Order resultOrder = null;
        try {
            PreparedStatement pst = con.prepareStatement("update agri_project.order set status='done'  where id =?");
            pst.setInt(1, order.getId());
            int result = pst.executeUpdate();

            if (result >= 0) {
                resultOrder = new Order();
                resultOrder.setDate(LocalDate.now());
                resultOrder.setUser_email(order.getUser_email());
                resultOrder.setStatus("cart");
                if (addOrder(resultOrder)) {
                    return getCart(order.getUser_email());
                } else {
                    return null;
                }

            }
        } catch (SQLException ex) {
            Logger.getLogger(OrderDaoImp.class
                    .getName()).log(Level.SEVERE, null, ex);
        }
        return resultOrder;
    }

    public boolean addNewItem(ProductPerOrder order, int order_id) {
        try {
            PreparedStatement pst = con.prepareStatement("insert into order_product (product_id,quantity,order_id) values (?,?,?)");
            pst.setInt(2, order.getQuantity());
            pst.setInt(3, order_id);
            pst.setString(1, order.getProduct().getName());
            int result = pst.executeUpdate();

            if (result == 0) {
                return false;

            }
        } catch (SQLException ex) {
            Logger.getLogger(OrderDaoImp.class
                    .getName()).log(Level.SEVERE, null, ex);
            return false;
        }

        return true;
    }

    public boolean deleteItem(String product_id, int order_id) {
        try {
            PreparedStatement pst = con.prepareStatement("delete from order_product where order_id =? and product_id= ?");
            pst.setInt(1, order_id);
            pst.setString(2, product_id);
            int result = pst.executeUpdate();

            if (result == 0) {
                return false;

            }
        } catch (SQLException ex) {
            Logger.getLogger(OrderDaoImp.class
                    .getName()).log(Level.SEVERE, null, ex);
            return false;
        }

        return true;
    }

    @Override
    public boolean updateOrder(Order order) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public ArrayList<ProductPerOrder> getOrderItems(int order_id) {
        ArrayList<ProductPerOrder> items = new ArrayList<>();
        try {
            PreparedStatement pst = con.prepareStatement("select * from order_product where order_id=?");
            pst.setInt(1, order_id);
            ResultSet result = pst.executeQuery();

            while (result.next()) {
                ProductPerOrder item = new ProductPerOrder();
                ProductDaoImp productsDao = new ProductDaoImp();
                item.setProduct(productsDao.getProduct(result.getString("product_id")));
                item.setQuantity(result.getInt("quantity"));
                System.out.println("Order item"+item.getProduct().getName()+" , "+item.getQuantity());
                items.add(item);
            }
        } catch (SQLException ex) {
            Logger.getLogger(OrderDaoImp.class.getName()).log(Level.SEVERE, null, ex);
        }
        return items;
    }

    @Override
    public List<Order> getAllOrders() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}
