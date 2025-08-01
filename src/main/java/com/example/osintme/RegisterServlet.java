package com.example.osintme;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;
import java.sql.*;

// import org.mindrot.jbcrypt.BCrypt;

@WebServlet(name = "RegisterServlet", value = "/register-servlet")
public class RegisterServlet extends HttpServlet {
    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            HttpSession session = request.getSession();
            if (session == null) {
                response.sendRedirect(request.getContextPath() + "/signin.jsp");
                return;
            }

            String code = request.getParameter("code").trim();
            String verify_code = (String) session.getAttribute("verify_code"); // gets the verification code from SendCodeServlet temp attribute

            // If verify_code is empty/wrong, redirect them back to signin.jsp
            if (verify_code == null || !code.equals(verify_code)) {
                // request.getRequestDispatcher(request.getContextPath() + "/register.jsp").forward(request, response);
                response.sendRedirect(request.getContextPath() + "/signin.jsp");
                return;
            }

            // Otherwise, create an account and send them to dashboard
            /*String first_name = request.getParameter("first_name");
            String email = request.getParameter("email");
            String password = request.getParameter("password");*/
            String first_name = (String) session.getAttribute("first_name");
            String email = (String) session.getAttribute("email");
            String password = (String) session.getAttribute("password");
            // Hashing the password?
            // String hashPassword = BCrypt.hashpw(password, BCrypt.gensalt());

            // Connection to MySQL
            Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/osintme", "root", "helloworld");
            
            // Update query to create a email + password in User table
            String registerSql = "INSERT INTO osintme.User (email, password) VALUES (?, ?)";
            PreparedStatement prepare = connection.prepareStatement(registerSql);
            prepare.setString(1, email);
            prepare.setString(2, password);
            int affectedRows = prepare.executeUpdate();

            if (affectedRows > 0) {
                // Update query to add first_name to PII table
                registerSql = "INSERT INTO osintme.Personal_Information (user_id, first_name, last_name, phone) VALUES ((SELECT user_id FROM osintme.User WHERE email = ?), ?, '', '')";
                prepare = connection.prepareStatement(registerSql);
                prepare.setString(1, email);
                prepare.setString(2, first_name);
                affectedRows = prepare.executeUpdate();

                // User created, unique user_id made automatically by the db, redirect to UserDashboardServlet.java
                if (affectedRows > 0) {
                    // Query to check if email + password in Users table
                    registerSql = "SELECT * FROM User WHERE email = ? AND password = ?";
                    prepare = connection.prepareStatement(registerSql);
                    prepare.setString(1, email);
                    prepare.setString(2, password);
                    ResultSet result = prepare.executeQuery();

                    // Redirects user if result is returned
                    if (result.next()) {
                        int userId = result.getInt("user_id");
                        session.setAttribute("userId", userId);
                        response.sendRedirect(request.getContextPath() + "/dashboard");
                    }
                    result.close();
                }
            }
            // DELETE FROM osintme.Personal_Information WHERE pii_id = 11
            // DELETE FROM osintme.User WHERE user_id = 21

            // Close connection
            prepare.close();
            connection.close();
        }
        catch (Exception e) {
            e.printStackTrace();
            request.getRequestDispatcher("/signin.jsp").forward(request, response);
            // request.getRequestDispatcher("/" + e.getMessage()).forward(request, response); // for debugging
        }
    }
}
