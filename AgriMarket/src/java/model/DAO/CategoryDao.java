/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model.DAO;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import model.pojo.Category;
import model.pojo.Product;

/**
 *
 * @author muhammad
 */
public interface CategoryDao {
   public ArrayList<Category> getAllCategories();
   public boolean addCategory(String name);

}
