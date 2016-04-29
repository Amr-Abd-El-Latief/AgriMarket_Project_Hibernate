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
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import model.DAO.CategoryDao;
import model.dto.Category;
import util.JdbcConnection;

/**
 *
 * @author muhammad
 */
public class CategoryDaoImp implements CategoryDao {

    PreparedStatement stm;
    Connection connection;

    public CategoryDaoImp() {
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

    @Override
    public ArrayList getAllCategories() {
        
        ArrayList<Category> categories = new ArrayList<>();
        try {
            stm = connection.prepareStatement("SELECT * FROM agri_project.category;");
            
            ResultSet rs = stm.executeQuery();
            while (rs.next()) {
                Category category = new Category();
                category.setId(rs.getInt("id"));
                category.setName(rs.getString("name"));
                

                categories.add(category);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return categories;
    }

    @Override
    public boolean addCategory(String name) {
        
             try {
            if (name != null) {
                stm = connection.prepareStatement("INSERT INTO agri_project.category (name)  VALUES(?)");
               stm.setString(1, name);
                 stm.executeUpdate();
                return true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
        return false;
        
    }
    public static void main(String[] args) {
        
    }
 

}
