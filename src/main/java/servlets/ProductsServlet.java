package servlets;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;

import dao.ProductDAO;
import model.Product;

@WebServlet("/products")

public class ProductsServlet extends HttpServlet {

    public void init() {
        System.out.println("ProductsServlet initialized");
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.getRequestDispatcher("products.jsp").forward(request, response);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            if (request.getParameter("delete") != null) {
                int id = Integer.parseInt(request.getParameter("delete"));
                ProductDAO productDAO = new ProductDAO();
                productDAO.deleteProductById(id);
                response.sendRedirect("products.jsp");

            } else {
                String name = request.getParameter("name");
                String price = request.getParameter("price");
                if (name == null || price == null || name.isEmpty() || price.isEmpty()) {
                    request.setAttribute("flash_message", "Please enter both name and price");
                    request.getRequestDispatcher("products.jsp").forward(request, response);
                    return;
                }
                if(Double.parseDouble(price) < 0) {
                    request.setAttribute("flash_message", "Price cannot be negative");
                    request.getRequestDispatcher("products.jsp").forward(request, response);
                    return;
                }
                Product product = new Product();
                product.setName(name);
                product.setPrice(Double.parseDouble(price));
                ProductDAO productDAO = new ProductDAO();
                productDAO.addProduct(product);
                response.sendRedirect("products.jsp");
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
