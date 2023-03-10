package servlets;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;

import dao.UserDAO;
import model.User;

import java.io.IOException;

@WebServlet("/login")
public class LoginServlet extends HttpServlet {

    public void init() {
        System.out.println("LoginServlet initialized");
    }

    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.getRequestDispatcher("index.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        String rememberMe = request.getParameter("rememberMe");

        try {
            if (username == null || password == null || username.isEmpty() || password.isEmpty()) {
                request.setAttribute("flash_message", "Please enter both username and password");
                request.getRequestDispatcher("index.jsp").forward(request, response);
                return;
            }
            UserDAO userDAO = new UserDAO();
            User user = userDAO.getUserByUsername(username);

            // If the user is not found, redirect to the register page
            if (user != null && !user.getPassword().equals(password)) {
                request.setAttribute("flash_message", "Invalid password");
                request.getRequestDispatcher("index.jsp").forward(request, response);
                return;
            }
            if (user != null && user.getPassword().equals(password)) {
                System.out.println("Login successful");
                HttpSession session = request.getSession();
                session.setAttribute("user", user);
                session.setAttribute("username", user.getUserName());
                if (rememberMe != null) {
                    Cookie cookie = new Cookie("userId", String.valueOf(user.getId()));
                    cookie.setMaxAge(60 * 60 * 24 * 30);
                    response.addCookie(cookie);
                }
                response.sendRedirect("products.jsp");
            } else {
                System.out.println("Login failed");
                response.sendRedirect("register.jsp");
            }

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
}
