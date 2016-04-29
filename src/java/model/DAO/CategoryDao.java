/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model.DAO;

import java.util.ArrayList;
 

/**
 *
 * @author muhammad
 */
public interface CategoryDao {
   public ArrayList getAllCategories();
   public boolean addCategory(String name);

}
