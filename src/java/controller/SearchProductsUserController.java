/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import java.io.IOException;
import java.util.ArrayList;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import model.DAO.ProductDao;
import model.DAOImp.ProductDaoImp;
import model.dto.Product;
import util.Search;

/**
 *
 * @author Amr
 */
@WebServlet(name = "getallproducts")
public class SearchProductsUserController extends HttpServlet {

    
    
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        ArrayList<Product> products = null;
        ProductDao productDao = new ProductDaoImp();
        String categoryName = request.getParameter("category");
        String productPrice = request.getParameter("product-price");
        String proName = request.getParameter("search");
        System.out.println(categoryName);
        System.out.println(productPrice);
        System.out.println(proName);
        String options = request.getParameter("options");
        if (("".equals(proName) || !"".equals(proName)) && (!"Select Category".equals(categoryName) && categoryName != null)) {
            System.out.println("category name"+categoryName+"product name"+proName);  
            products =  productDao.searchProduct(categoryName, Search.CATEGORY);
        }else if (("".equals(proName) || proName != null) && !"Select Price".equals(productPrice)) {
               products =  productDao.searchProduct(productPrice, Search.PRICE);
        } else if (proName != null) {
            products = productDao.searchProduct(proName, Search.NAME);

        } else {
            products = productDao.searchProduct("", Search.ALL);
//            if (request.getServletContext().getAttribute("products") == null) {
                  request.getServletContext().setAttribute("products", products);
//            }
        }

        request.setAttribute("getproducts", products);
        RequestDispatcher dispatcher = request.getRequestDispatcher("index.jsp");
        dispatcher.forward(request, response);
//        response.sendRedirect("index.jsp");
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

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
