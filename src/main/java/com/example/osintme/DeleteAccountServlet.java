package com.example.osintme;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;
import java.sql.*;

@WebServlet(name = "DeleteAccountServlet", value = "/delete-account-servlet")
public class DeleteAccountServlet extends HttpServlet {
    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            // Check http session connection
            HttpSession session = request.getSession(false);
            if (session == null || session.getAttribute("userId") == null) {
                response.sendRedirect(request.getContextPath() + "/dashboard.jsp");
                return;
            }

            // Gets user ID from the session
            int userId = (Integer) session.getAttribute("userId");

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
                // Close connection
                prepare.close();
                connection.close();
                session.invalidate();

                request.setAttribute("error","User successfully deleted.");
                request.getRequestDispatcher("/signin.jsp").forward(request, response);
                return;
            }
        }
        catch (Exception e) {
            e.printStackTrace();
            request.getRequestDispatcher("/" + e.getMessage()).forward(request, response); // for debugging
        }
    }
}
