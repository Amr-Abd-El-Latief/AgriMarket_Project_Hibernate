/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model.DAO;

import java.util.ArrayList;
import java.util.List;
import model.dto.Product;
import util.Search;

/**
 *
 * @author muhammad
 */
public interface ProductDao {

    public List<Product> getAllProducts();

    public Product getProduct(String name);

    public boolean addProduct(Product product);

    public boolean updateProduct(Product product);

    public ArrayList<Product> searchProduct(String name, Search type);

    public boolean removeProduct(String name);

    public boolean isExistProduct(Product product);

    public byte[] getResource(String productName);

}
