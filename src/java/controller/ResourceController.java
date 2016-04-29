/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import model.DAO.ProductDao;
import model.DAOImp.ProductDaoImp;

/**
 *
 * @author muhammad
 */
@WebServlet("/images/*")
public class ResourceController extends HttpServlet {
 
  
    
    
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String proName = request.getPathInfo().substring(1); // Returns "foo.png".
        ProductDao productDao = new ProductDaoImp();
        byte[] image =  productDao.getResource(proName);
                 if (image != null) {
                    response.setContentType(getServletContext().getMimeType(proName));
                    response.setContentLength(image.length);
                    response.getOutputStream().write(image);
                } else {
                    response.sendError(HttpServletResponse.SC_NOT_FOUND); // 404.
                }
    }
    

     
    
    
    
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
