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
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import model.DAO.ProductDao;
import model.pojo.Category;
import model.pojo.Product;
import util.JdbcConnection;

/**
 *
 * @author muhammad
 */
public class ProductDaoImp implements ProductDao {

    PreparedStatement stm;
    Connection connection;

    public ProductDaoImp() {
        try {
            connection = JdbcConnection.getConnection();
            if (connection != null) {
                System.out.println("connection successful");
            } else {
                System.out.println("Connection falied");
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    public byte[] getResource(String productName) {
        byte[] content = null;
        try {
            stm = connection.prepareStatement("SELECT * FROM agri_project.product where name=?");
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
        try {
            stm = connection.prepareStatement("delete   FROM agri_project.product where name = ?");
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

    @Override
    public List<Product> getAllProducts() {
        List<Product> products = new ArrayList<>();
        try {
            stm = connection.prepareStatement("select * FROM agri_project.product;");
            ResultSet rs = stm.executeQuery();
            while (rs.next()) {
                Product product = new Product();
                product.setName(rs.getString("name"));
                product.setPrice(rs.getFloat("price"));
                product.setDesc(rs.getString("desc"));
                product.setQuantity(rs.getInt("quantity"));
                products.add(product);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return products;
    }

    @Override
    public boolean addProduct(Product product) {
        try {
            if (isExistProduct(product)) {
                return false;
            }

            if (product != null) {
                stm = connection.prepareStatement("insert into agri_project.product  (name,price,product.desc,category_id,image,quantity) values(?,?,?,?,?,?)");
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
        if (!isExistProduct(product)) 
            return false;
          else {
            try {
                stm = connection.prepareStatement("update  agri_project.product p  set  p.desc = ?,p.quantity = ?,p.price =?   where p.name=?  ");
          
                stm.setString(1, product.getDesc());
                stm.setInt(2, product.getQuantity());
                stm.setFloat(3, product.getPrice());
                
                stm.setString(4, product.getName());
                int res = stm.executeUpdate();
                if (res != 0) 
                    return true;
                

            } catch (SQLException ex) {
                Logger.getLogger(ProductDaoImp.class.getName()).log(Level.SEVERE, null, ex);
                return false;
            }
        }
        return false;
    }

    @Override
    public ArrayList<Product> searchProduct(String name) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Product getProduct(String name) {
        Product product = new Product();

        try {
            stm = connection.prepareStatement("select * FROM agri_project.product where name=?;");
            
            stm.setString(1, name);
            ResultSet rs = stm.executeQuery();
            while (rs.next()) {
                product.setName(rs.getString("name"));
                product.setPrice(rs.getFloat("price"));
                product.setCategoryId(rs.getInt("category_id"));
                product.setDesc(rs.getString("desc"));
                product.setQuantity(rs.getInt("quantity"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return product;
    }

    @Override
    public boolean isExistProduct(Product product) {

        if (!product.getName().equals("")) {

            try {
                stm = connection.prepareStatement("Select * from agri_project.product where name=?");
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

    public static void main(String[] args) {
        ProductDaoImp daoImp = new ProductDaoImp();
        Product product = new Product();
        product.setName("Botatos");
        product.setPrice(2423);
        product.setQuantity(2323);
        product.setDesc("csdcsdcsdc");
         boolean c = daoImp.updateProduct(product);
        System.out.println(c);
    }

}
