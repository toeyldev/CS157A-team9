package com.example.osintme;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;
import java.sql.*;

@WebServlet(name = "AdminDeleteAccountServlet", value = "/admin-delete-account-servlet")
public class AdminDeleteAccountServlet extends HttpServlet {
    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            // Check http session connection
            HttpSession session = request.getSession(false);
            if (session == null || session.getAttribute("userId") == null) {
                response.sendRedirect(request.getContextPath() + "/dashboard.jsp");
                return;
            }

            // Simple validation
            String email = request.getParameter("email");

            // Gets user ID + email
            String userIdString = request.getParameter("userId");
            int userId = Integer.parseInt(userIdString);

            // Sql statements to delete account
            String deleteLocationSql = "DELETE FROM osintme.Location WHERE address_id IN (SELECT address_id FROM osintme.Personal_Information WHERE user_id = ?)";
            String deletePIISql = "DELETE FROM osintme.Personal_Information WHERE user_id = ?";
            String deleteUserSql = "DELETE FROM osintme.User WHERE user_id = ?";

            // Connection to MySQL
            Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/osintme", "root", "helloworld");

            // Deleting Location
            PreparedStatement prepare = connection.prepareStatement(deleteLocationSql);
            prepare.setInt(1, userId);
            int affectedRows = prepare.executeUpdate();

            // Deleting PII
            prepare = connection.prepareStatement(deletePIISql);
            prepare.setInt(1, userId);
            affectedRows = prepare.executeUpdate();

            // Deleting User
            prepare = connection.prepareStatement(deleteUserSql);
            prepare.setInt(1, userId);
            affectedRows = prepare.executeUpdate();

            if (affectedRows > 0) {
                // validation
                session.setAttribute("success", "User " + email + " was successfully deleted.");

                // ADD A ROW TO THE ACTIVITY LOG
                String sqlAddAction = "INSERT INTO osintme.activity_log (timestamp, user_id, action_id) VALUES (?,?,?)";
                prepare = connection.prepareStatement(sqlAddAction);

                Timestamp currentTimeStamp = new Timestamp(System.currentTimeMillis());
                Integer adminID = (Integer) session.getAttribute("userId");
                if (adminID == null) {
                    System.err.println("Admin's userId not found in session for logging activity. Using error flag ID of 0");
                    prepare.setTimestamp(1, currentTimeStamp);
                    prepare.setInt(2, 0); // Using 0 as a default ID
                    prepare.setInt(3, 3); // action_id for Edit
                } else {
                    prepare.setTimestamp(1, currentTimeStamp);
                    prepare.setInt(2, adminID);
                    prepare.setInt(3, 3); // action_id for Edit
                }
                prepare.executeUpdate(); // execute
            } else {
                session.setAttribute("error", "Failed to delete user. Please try again or check if the user ID exists.");
            }
            
            // Close connection
            prepare.close();
            connection.close();
        }
        catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("error", "Error occured: " + e.getMessage());
            request.getRequestDispatcher("/admin_account_management.jsp").forward(request, response);
            // request.getRequestDispatcher("/" + e.getMessage()).forward(request, response); // for debugging
        }
        response.sendRedirect(request.getContextPath() + "/account-management-servlet"); //redirect to servlet so it refreshes page properly
    }
}
