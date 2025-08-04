package com.example.osintme;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;
import java.sql.*;

@WebServlet(name = "ForgotServlet", value = "/forgot-servlet")
public class ForgotServlet extends HttpServlet {
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

            // If verify_code is empty/wrong, dispatch them to forgot_password.jsp
            if (verify_code == null || !code.equals(verify_code)) {
                request.setAttribute("error", "Verify code was incorrect. Try again.");
                request.getRequestDispatcher("/forgot_password.jsp").forward(request, response);
                // this ^ doesn't need request.getContextPath() because it doesn't lead to a different url
                return;
            }

            // Otherwise, query their user_id from email + send them to dashboard
            String email = (String) session.getAttribute("email");

            // Connection to MySQL
            Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/osintme", "root", "helloworld");
            
            // Query to select user_id in User table
            String forgotSql = "SELECT user_id FROM osintme.User WHERE email = ?";
            PreparedStatement prepare = connection.prepareStatement(forgotSql);
            prepare.setString(1, email);
            ResultSet result = prepare.executeQuery();

            // User found, redirect to UserDashboardServlet.java
            if (result.next()) {
                int userId = result.getInt("user_id");
                session.setAttribute("userId", userId);
                response.sendRedirect(request.getContextPath() + "/dashboard");
            }
            
            // Close connection
            result.close();
            prepare.close();
            connection.close();
        }
        catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("error", "Error occured: " + e.getMessage());
            request.getRequestDispatcher("/forgot_password.jsp").forward(request, response);
            // request.getRequestDispatcher("/" + e.getMessage()).forward(request, response); // for debugging
        }
    }
}
