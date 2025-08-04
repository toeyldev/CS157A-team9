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

            // If verify_code is empty/wrong, dispatch them to register.jsp
            if (verify_code == null || !code.equals(verify_code)) {
                request.setAttribute("error", "Verify code was incorrect. Send another code.");
                request.getRequestDispatcher("/register.jsp").forward(request, response);
                // this ^ doesn't need request.getContextPath() because it doesn't lead to a different url
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

            // Update query to create an email + password in User table
            String registerSql = "INSERT INTO osintme.User (email, password) VALUES (?, ?)";
            PreparedStatement prepare = connection.prepareStatement(registerSql);
            prepare.setString(1, email);
            prepare.setString(2, password);
            int affectedRows = prepare.executeUpdate();

            // Update query to create an empty row in Location table related to the user_id
            if (affectedRows > 0) {
                registerSql = "INSERT INTO osintme.Location (address, city, state, zip_code) VALUES ('', '', '', '')";
                prepare = connection.prepareStatement(registerSql, Statement.RETURN_GENERATED_KEYS);
                affectedRows = prepare.executeUpdate();

                ResultSet result = prepare.getGeneratedKeys();
                int address_id = -1;
                if (result.next()) {
                    address_id = result.getInt(1);
                }

                prepare.close();
                result.close();
                if (affectedRows > 0) {
                    // Update query to add first_name to PII table
                    registerSql = "INSERT INTO osintme.Personal_Information (user_id, first_name, last_name, phone, address_id) VALUES ((SELECT user_id FROM osintme.User WHERE email = ?), ?, '', '', ?)";
                    prepare = connection.prepareStatement(registerSql);
                    prepare.setString(1, email);
                    prepare.setString(2, first_name);
                    prepare.setInt(3, address_id);
                    affectedRows = prepare.executeUpdate();

                    prepare.close();
                    // User created, unique user_id made automatically by the db
                    if (affectedRows > 0) {
                        // Query to check if email + password in Users table
                        registerSql = "SELECT * FROM User WHERE email = ? AND password = ?";
                        prepare = connection.prepareStatement(registerSql);
                        prepare.setString(1, email);
                        prepare.setString(2, password);
                        result = prepare.executeQuery();

                        // Redirects user to UserDashboardServlet.java if result is returned
                        if (result.next()) {
                            int userId = result.getInt("user_id");
                            session.setAttribute("userId", userId);
                            response.sendRedirect(request.getContextPath() + "/dashboard");
                        }
                        result.close();
                        prepare.close();
                    }
                }
            }
            // DELETE FROM osintme.Personal_Information WHERE pii_id = 11
            // DELETE FROM osintme.User WHERE user_id = 21

            // Close connection
            connection.close();
        }
        catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("error", "Error occured: " + e.getMessage());
            request.getRequestDispatcher("/register.jsp").forward(request, response);
            // request.getRequestDispatcher("/" + e.getMessage()).forward(request, response); // for debugging
        }
    }
}
