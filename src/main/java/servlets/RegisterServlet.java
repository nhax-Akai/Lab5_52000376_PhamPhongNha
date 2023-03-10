package servlets;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;

import dao.UserDAO;
import model.User;

import java.io.IOException;

@WebServlet("/register")
public class RegisterServlet extends HttpServlet {
    public void init() {
        System.out.println("RegisterServlet initialized");
    }

    public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String username = request.getParameter("username");
        String email = request.getParameter("email");
        String password = request.getParameter("password");
        String confirmPassword = request.getParameter("password-confirm");

        try {
            if (username == null || email == null || password == null || confirmPassword == null ||
                    username.isEmpty() || email.isEmpty() || password.isEmpty() || confirmPassword.isEmpty()) {
                request.setAttribute("flash_message", "Please enter all fields");
                request.getRequestDispatcher("register.jsp").forward(request, response);
                return;
            }
            if (!password.equals(confirmPassword)) {
                request.setAttribute("flash_message", "Passwords do not match");
                request.getRequestDispatcher("register.jsp").forward(request, response);
                return;
            }
            UserDAO userDAO = new UserDAO();
            User user = new User(username, email, password);
            if (userDAO.addUser(user)) {
                HttpSession session = request.getSession();
                session.setAttribute("user", user);
                response.sendRedirect("index.jsp");

            } else {
                System.out.println("User could not be added");
                response.sendRedirect("register.jsp");
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

    }
}
