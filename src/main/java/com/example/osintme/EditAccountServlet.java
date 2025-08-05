package com.example.osintme;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.Timestamp;

/**
 * Servlet for handling the editing of an existing user account.
 * This servlet expects a POST request with 'userId', 'email', 'status', and 'privilege' parameters.
 * This servlet follows the same logic as the add account to make it easier
 */
@WebServlet(name = "EditAccountServlet", value = "/edit-account-servlet")
public class EditAccountServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        String userIdStr = request.getParameter("userId");
        String email = request.getParameter("email");
        String password = request.getParameter("password");
        String status = request.getParameter("status");
        String privilege = request.getParameter("privilege");

        HttpSession session = request.getSession();

        // Simple validation
        if (userIdStr == null || userIdStr.isEmpty() || email == null || email.isEmpty() || status == null || status.isEmpty() || privilege == null || privilege.isEmpty()) {
            session.setAttribute("error", "All fields are required to edit an account.");
            response.sendRedirect(request.getContextPath() + "/admin-dashboard");
            return;
        }

        Connection connection = null;
        PreparedStatement preparedStatement = null; // user table query
        PreparedStatement preparedStatement2 = null; // activity log query

        try {
            int userId = Integer.parseInt(userIdStr);

            connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/osintme", "root", "or08le49");

            String updateUserSql = "UPDATE osintme.User SET email = ?, status = ?, privilege = ? WHERE user_id = ?";
            // If a new password is provided, include it in the update statement.
            if (password != null && !password.isEmpty()) {
                updateUserSql = "UPDATE osintme.User SET email = ?, password = ?, status = ?, privilege = ? WHERE user_id = ?";
            }

            preparedStatement = connection.prepareStatement(updateUserSql);
            preparedStatement.setString(1, email);

            if (password != null && !password.isEmpty()) {
                preparedStatement.setString(2, password); // Remember to hash this!
                preparedStatement.setString(3, status);
                preparedStatement.setString(4, privilege);
                preparedStatement.setInt(5, userId);
            } else {
                preparedStatement.setString(2, status);
                preparedStatement.setString(3, privilege);
                preparedStatement.setInt(4, userId);
            }

            int affectedRows = preparedStatement.executeUpdate();
            if (affectedRows > 0) {
                // validation
                session.setAttribute("success", "User " + email + " was successfully updated.");

                // ADD A ROW TO THE ACTIVITY LOG
                String sqlAddAction = "INSERT INTO osintme.activity_log (timestamp, user_id, action_id) VALUES (?,?,?)";
                preparedStatement2 = connection.prepareStatement(sqlAddAction);

                Timestamp currentTimeStamp = new Timestamp(System.currentTimeMillis());
                Integer adminID = (Integer) session.getAttribute("userId");
                if (adminID == null) {
                    System.err.println("Admin's userId not found in session for logging activity. Using error flag ID of 0 ");
                    preparedStatement2.setTimestamp(1, currentTimeStamp);
                    preparedStatement2.setInt(2, 0); // Using 0 as a default ID
                    preparedStatement2.setInt(3, 3); // action_id for Edit
                } else {
                    preparedStatement2.setTimestamp(1, currentTimeStamp);
                    preparedStatement2.setInt(2, adminID);
                    preparedStatement2.setInt(3, 3); // action_id for Edit
                }

                preparedStatement2.executeUpdate(); // execute
            } else {
                session.setAttribute("error", "Failed to update user. Please try again or check if the user ID exists.");
            }
        } catch (NumberFormatException e) {
            session.setAttribute("error", "Invalid User ID format.");
        } catch (Exception e) {
            e.printStackTrace();
            session.setAttribute("error", "An error occurred while updating the user: " + e.getMessage());
        } finally {
            // Close database resources in a finally block
            try {
                if (preparedStatement != null)
                    preparedStatement.close();
                if (preparedStatement2 != null)
                    preparedStatement2.close();
                if (connection != null)
                    connection.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        response.sendRedirect(request.getContextPath() + "/account-management-servlet"); //redirect to servlet so it refreshes page properly
    }
}
