/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model.DAOImp;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import model.DAO.ProductDao;
import model.dto.Order;
import model.dto.Product;
import model.dto.ProductPerOrder;
import util.JdbcConnection;
import util.Search;

/**
 *
 * @author muhammad
 */
public class ProductDaoImp implements ProductDao {

    Connection con;

    public ProductDaoImp() {
        try {
            this.con = JdbcConnection.getConnection();
        } catch (SQLException ex) {
            Logger.getLogger(ProductDaoImp.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public ArrayList<Product> getAllProducts(int order_id) {

        ArrayList<Product> userProducts = new ArrayList();
        try {
            Statement productsStatement = con.createStatement();
            ResultSet productsResult = productsStatement.executeQuery("select * from agri_project.order_product where order_id=" + order_id);
            System.out.println("select * from agri_project.order_product where order_id=" + order_id);
            while (productsResult.next()) {
                Product product = getProduct(productsResult.getString("product_id"));
                System.out.println("Product id " + product.getName());
                product.setQuantity(productsResult.getInt("quantity"));
                userProducts.add(product);
            }
            return userProducts;
        } catch (SQLException ex) {
            Logger.getLogger(UserDaoImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    @Override
    public ArrayList<Product> searchProduct(String name, Search type) {
        ArrayList<Product> products = new ArrayList<>();

        PreparedStatement stm;

        ResultSet result = null;
        try {
            if (Search.CATEGORY == type) {
                stm = con.prepareStatement("select * from product where category_id=?");
                stm.setInt(1, Integer.valueOf(name));
            } else if (Search.NAME == type) {
                stm = con.prepareStatement("select * from product where name like '%" + name + "%' ");
//                stm.setString(1, name);
            } else if (Search.PRICE == type) {
                stm = con.prepareStatement("select * from product where price <= ?");
                stm.setFloat(1, Float.valueOf(name));
            } else {
                stm = con.prepareStatement("select * from product ");

            }
            result = stm.executeQuery();

            while (result.next()) {
                Product product = new Product();
                product.setCategoryId(result.getInt("category_id"));
                product.setPrice(result.getFloat("price"));
                product.setImage(result.getBytes("image"));
                product.setName(result.getString("name"));
                product.setDesc(result.getString("desc"));
                product.setQuantity(result.getInt("quantity"));

                products.add(product);
            }
        } catch (SQLException ex) {
            Logger.getLogger(UserDaoImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
        return products;
    }

    @Override
    public List<Product> getAllProducts() {

        PreparedStatement pst;
        List<Product> products = new ArrayList<>();

        try {
            pst = con.prepareStatement("select * FROM agri_project.product;");
            ResultSet rs = pst.executeQuery();
            while (rs.next()) {
                Product product = new Product();
                product.setName(rs.getString("name"));
                product.setPrice(rs.getFloat("price"));
                product.setDesc(rs.getString("desc"));
                product.setQuantity(rs.getInt("quantity"));
                product.setImage(rs.getBytes("image"));

                products.add(product);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return products;
    }

    @Override
    public byte[] getResource(String productName) {

        PreparedStatement stm;

        byte[] content = null;
        try {
            stm = con.prepareStatement("SELECT * FROM agri_project.product where name=?");
            stm.setString(1, productName);

            ResultSet rs = stm.executeQuery();
            while (rs.next()) {
//             byte[] content = rs.getBytes("image");
                content = rs.getBytes("image");

            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return content;
    }

    @Override
    public boolean removeProduct(String name) {
        PreparedStatement stm;

        try {
            stm = con.prepareStatement("delete   FROM agri_project.product where name = ?");
            stm.setString(1, name);

            int res = stm.executeUpdate();
            if (res != 0) {
                return true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public ArrayList<Product> getAllProductsForUserOrder(int order_id) {

        ArrayList<Product> userProducts = new ArrayList();
        try {
            Statement productsStatement = con.createStatement();
            ResultSet productsResult = productsStatement.executeQuery("select * from agri_project.order_product where order_id=" + order_id);
            while (productsResult.next()) {
                Product product = getProduct(productsResult.getString("product_id"));
                product.setQuantity(productsResult.getInt("quantity"));
                userProducts.add(product);
            }
            return userProducts;
        } catch (SQLException ex) {
            Logger.getLogger(UserDaoImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

//    public ArrayList<Product> getAllProducts(int order_id) {
//
//        ArrayList<Product> userProducts = new ArrayList();
//        try {
//            Statement productsStatement = con.createStatement();
//            ResultSet productsResult = productsStatement.executeQuery("select * from agri_project.order_product where order_id=" + order_id);
//            while (productsResult.next()) {
//                Product product = getProduct(productsResult.getString("product_id"));
//                product.setQuantity(productsResult.getInt("quantity"));
//                userProducts.add(product);
//            }
//            return userProducts;
//        } catch (SQLException ex) {
//            Logger.getLogger(UserDaoImpl.class.getName()).log(Level.SEVERE, null, ex);
//        }
//        return null;
//    }
    @Override
    public boolean addProduct(Product product) {
        PreparedStatement stm;

        try {
            if (isExistProduct(product)) {
                return false;
            }

            if (product != null) {
                stm = con.prepareStatement("insert into agri_project.product  (name,price,product.desc,category_id,image,quantity) values(?,?,?,?,?,?)");
                stm.setString(1, product.getName());
                stm.setFloat(2, product.getPrice());
                stm.setString(3, product.getDesc());
                stm.setInt(4, product.getCategoryId());
                stm.setBytes(5, product.getImage());
                stm.setInt(6, product.getQuantity());
                stm.executeUpdate();
                return true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
        return false;

//      st.setBinaryStream(1, file.getInputStream(), (int) file.getSize());
    }

    @Override
    public boolean updateProduct(Product product) {
        PreparedStatement stm;

        if (!isExistProduct(product)) {
            return false;
        } else {
            try {
                stm = con.prepareStatement("update  agri_project.product p  set  p.desc = ?,p.quantity = ?,p.price =?   where p.name=?  ");

                stm.setString(1, product.getDesc());
                stm.setInt(2, product.getQuantity());
                stm.setFloat(3, product.getPrice());

                stm.setString(4, product.getName());
                int res = stm.executeUpdate();
                if (res != 0) {
                    return true;
                }

            } catch (SQLException ex) {
                Logger.getLogger(ProductDaoImp.class.getName()).log(Level.SEVERE, null, ex);
                return false;
            }
        }
        return false;
    }

    @Override
    public Product getProduct(String name) {
        try {
            Statement productStatement = con.createStatement();
            ResultSet productResult = productStatement.executeQuery("select * from agri_project.product where name='" + name + "'");
            productResult.next();
            Product product = new Product();
            product.setCategoryId(productResult.getInt("category_id"));
            product.setPrice(productResult.getFloat("price"));
            product.setImage(productResult.getBytes("image"));
            product.setDesc(productResult.getString("desc"));
            product.setQuantity(productResult.getInt("quantity"));
            product.setName(name);
            return product;
        } catch (SQLException ex) {
            Logger.getLogger(UserDaoImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    @Override
    public boolean isExistProduct(Product product) {

        PreparedStatement stm;

        if (!product.getName().equals("")) {

            try {
                stm = con.prepareStatement("Select * from agri_project.product where name=?");
                stm.setString(1, product.getName());
                ResultSet rs = stm.executeQuery();
                if (rs.next()) {
                    return true;
                }
            } catch (SQLException ex) {
                ex.printStackTrace();
                return false;
            }
        }
        return false;

    }

    public void emptyCart(Order order) {
        for (int i = 0; i < order.getItems().size(); i++) {
            ProductPerOrder get = order.getItems().get(i);
            get.getProduct().setQuantity(get.getProduct().getQuantity() - get.getQuantity());
            if (get.getProduct().getQuantity() < 0) {
                get.getProduct().setQuantity(0);
            }
            System.out.println("New Quantity "+get.getProduct().getQuantity());
            updateProduct(get.getProduct());
        }
    }

}
