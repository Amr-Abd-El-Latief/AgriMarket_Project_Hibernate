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
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import model.DAO.ProductDao;
import model.DAOImp.ProductDaoImp;
import model.pojo.Product;

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
        System.out.println(product.getName());
        System.out.println(product.getPrice());
        GsonBuilder builder = new GsonBuilder();
        Gson gson = builder.create();
        out.print(gson.toJson(product));

    }
//update product
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        PrintWriter out = response.getWriter();
        System.out.println("dopost product");
        String name = request.getParameter("name");
        String price = request.getParameter("price");
        String quantity = request.getParameter("quantity");
        String desc = request.getParameter("desc");
        String categoryId = request.getParameter("pcategory");
        System.out.println("name" + name);
        Product product = new Product();
        product.setName(name);
        product.setPrice(Float.parseFloat(price));
        product.setQuantity(Integer.valueOf(quantity));
        product.setDesc(desc);
        ProductDaoImp daoImp = new ProductDaoImp();
        daoImp.updateProduct(product);
        response.sendRedirect("http://" + request.getServerName() + ":" + request.getServerPort() + "/AgriMarket/admin/getProducts?#product-div");

//        RequestDispatcher dispatcher = request.getRequestDispatcher("admin.jsp");
//        dispatcher.forward(request, response);
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
