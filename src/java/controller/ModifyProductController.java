/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import model.DAO.ProductDao;
import model.DAOImp.ProductDaoImp;
import model.dto.Product;

/**
 *
 * @author muhammad
 */
public class ModifyProductController extends HttpServlet {
//get product

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        PrintWriter out = response.getWriter();
        String productName = request.getParameter("productName");
        ProductDao productDao = new ProductDaoImp();
        Product product = productDao.getProduct(productName);
        System.out.println(product.getCategoryId());
         GsonBuilder builder = new GsonBuilder();
        Gson gson = builder.create();
        out.print(gson.toJson(product));

    }
//update product
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        PrintWriter out = response.getWriter();
         String name = request.getParameter("name");
        String price = request.getParameter("price");
        String quantity = request.getParameter("quantity");
        String desc = request.getParameter("desc");
        String categoryId = request.getParameter("pcategory");
 
        Product product = new Product();
        product.setName(name);
        product.setPrice(Float.parseFloat(price));
        product.setQuantity(Integer.valueOf(quantity));
        product.setDesc(desc);
        List<Product>  products  =(List<Product>) request.getServletContext().getAttribute("products");
        if ( products != null) {
            for(Product p : products){
                if (p.getName().equals(name)) {
                    p.setDesc(desc);
                    p.setPrice(Float.valueOf(price));
                    p.setQuantity(Integer.valueOf(quantity));
                    request.getServletContext().setAttribute("products",products);
                }
            }
        } 
        ProductDaoImp daoImp = new ProductDaoImp();
        daoImp.updateProduct(product);
        
        response.sendRedirect("http://" + request.getServerName() + ":" + request.getServerPort() + "/AgriMarket/admin/getProducts?#product-div");

    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
