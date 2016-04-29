/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Iterator;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import model.DAO.ProductDao;
import model.DAOImp.ProductDaoImp;
import model.dto.Product;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

/**
 *
 * @author muhammad
 */
public class ProductController extends HttpServlet {
//delete product

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String productName = request.getParameter("product_Name");
        List<Product> products = (List<Product>) request.getServletContext().getAttribute("products");
        if (products != null) {
            Iterator<Product> iterator = products.iterator();
            while (iterator.hasNext()) {
                if (iterator.next().getName().equals(productName)) {
                    iterator.remove();
                }
            }

            request.getServletContext().setAttribute("products", products);

        }
        ProductDao dao = new ProductDaoImp();
        dao.removeProduct(productName);
        response.sendRedirect("http://" + request.getServerName() + ":" + request.getServerPort() + "/AgriMarket/admin/getProducts?#product-div");

    }

    //add product
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            PrintWriter out = response.getWriter();
            Product product = new Product();
            DiskFileItemFactory factory = new DiskFileItemFactory();
            ServletFileUpload upload = new ServletFileUpload(factory);
            List<FileItem> items = upload.parseRequest(request);
            Iterator<FileItem> it = items.iterator();

            while (it.hasNext()) {
                FileItem item = it.next();
                if (!item.isFormField()) {
                    byte[] image = item.get();
                    if (image != null && image.length != 0) {
                        product.setImage(image);
                    }
                } else {
                    switch (item.getFieldName()) {
                        case "name":
                            product.setName(item.getString());
                            System.out.println("name" + item.getString());
                            break;
                        case "price":
                            product.setPrice(Float.valueOf(item.getString()));
                            break;
                        case "quantity":
                            product.setQuantity(Integer.valueOf(item.getString()));
                            break;
                        case "desc":
                            product.setDesc(item.getString());
                            System.out.println("desc: " + item.getString());
                            break;
                        default:
                            product.setCategoryId(Integer.valueOf(item.getString()));
                    }
                }
            }

            ProductDao daoImp = new ProductDaoImp();
            boolean check = daoImp.addProduct(product);
            if (check) {
                List<Product> products = (List<Product>) request.getServletContext().getAttribute("products");
                if (products != null) {
                    products.add(product);
                    request.getServletContext().setAttribute("products", products);

                }
                response.sendRedirect("http://" + request.getServerName() + ":" + request.getServerPort() + "/AgriMarket/admin/getProducts?success=Successfully#header3-41");
            } else {
                response.sendRedirect("http://" + request.getServerName() + ":" + request.getServerPort() + "/AgriMarket/admin/getProducts?status=Exist!#header3-41");
            }

        } catch (FileUploadException ex) {
            Logger.getLogger(ProductController.class.getName()).log(Level.SEVERE, null, ex);
        }
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
