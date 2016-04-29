/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import model.DAOImp.ProductDaoImp;
import model.pojo.Product;

/**
 *
 * @author muhammad
 */
public class GetProductsContoller extends HttpServlet {
 

     
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        PrintWriter out = response.getWriter();
        ProductDaoImp daoImp = new ProductDaoImp();
        List<Product>  products = daoImp.getAllProducts();
        request.setAttribute("products", products);
        System.out.println("new product");
        RequestDispatcher dispatcher = request.getRequestDispatcher("admin.jsp");
        dispatcher.forward(request, response);
     }
 
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
     }
 
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
