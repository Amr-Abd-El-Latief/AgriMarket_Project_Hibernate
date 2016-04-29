/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.commons.io.IOUtils;
import util.JdbcConnection;

/**
 *
 * @author mohamed
 */
public class LoadImageServlet extends HttpServlet {
    
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            /* TODO output your page here. You may use following sample code. */
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Servlet LoadImageServlet</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet LoadImageServlet at " + request.getContextPath() + "</h1>");
            out.println("</body>");
            out.println("</html>");
        }
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String iid = request.getParameter("param1");
        byte[] sImageBytes;
        try {
            
            Connection con = JdbcConnection.getConnection();
            String Query = "SELECT image FROM user WHERE email ='" + iid + "';";
            System.out.println("Query is" + Query);
            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery(Query);
            System.out.println("..........1");
            String name = "client_2";
            if (rs.next()) {
                
                sImageBytes = rs.getBytes("image");
                
                
                response.setContentType("image/jpeg");
                response.setContentLength(sImageBytes.length);
                // Give the name of the image in the name variable in the below line   
                response.setHeader("Content-Disposition", "inline; filename=\"" + name + "\"");
                
                BufferedInputStream input = new BufferedInputStream(new ByteArrayInputStream(sImageBytes));
                BufferedOutputStream output = new BufferedOutputStream(response.getOutputStream());
                
                byte[] buffer = new byte[8192];
                int length;
                while ((length = input.read(buffer)) > 0) {
                    output.write(buffer, 0, length);
                    System.out.println(".......3");
                }
                output.flush();
            }
        } catch (Exception ex) {
            System.out.println("error :" + ex);
        }
    }
    
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
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
