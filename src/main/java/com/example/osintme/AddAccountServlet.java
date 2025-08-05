package com.example.osintme;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.sql.*;

/**
 * Servlet for handling the creation of a new user account.
 * This servlet expects a POST request with 'email', 'password', 'status' and 'privilege' parameters.
 */
@WebServlet(name = "AddAccountServlet", value = "/add-account-servlet")
public class AddAccountServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        String email = request.getParameter("email");
        String password = request.getParameter("password");
        String status = request.getParameter("status");
        String privilege = request.getParameter("privilege");

        HttpSession session = request.getSession();

        // Simple validation
        if (email == null || email.isEmpty() || password == null || password.isEmpty() || status == null || status.isEmpty() || privilege == null || privilege.isEmpty()) {
            session.setAttribute("error", "All fields are required to create a new user.");
            response.sendRedirect(request.getContextPath() + "/admin_account_management.jsp");
            return;
        }

        Connection connection = null;
        PreparedStatement preparedStatement = null; //user table query
        PreparedStatement preparedStatement2 = null; //activity log query

        try {

            connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/osintme", "root", "or08le49");

            String insertUserSql = "INSERT INTO osintme.User (email, password, status, privilege) VALUES (?, ?, ?, ?)";

            preparedStatement = connection.prepareStatement(insertUserSql);
            preparedStatement.setString(1, email);
            preparedStatement.setString(2, password); // Remember to hash this!
            preparedStatement.setString(3, status);
            preparedStatement.setString(4, privilege);

            int affectedRows = preparedStatement.executeUpdate();
            if (affectedRows > 0) {
                // validation
                session.setAttribute("success", "User " + email + " was successfully created.");
                //ADD A ROW TO THE ACTIVITY LOG
                String sqlAddAction = "INSERT INTO osintme.activity_log (timestamp, user_id, action_id) VALUES (?,?,?)";

                preparedStatement2 = connection.prepareStatement(sqlAddAction);
                Timestamp currentTimeStamp = new Timestamp(System.currentTimeMillis()); //retrieve current timestamp
                Integer adminID = (Integer) session.getAttribute("userId");
                if (adminID == null){
                    System.err.println("Admin's userId not found in session for logging activity.");
                }

                preparedStatement2.setTimestamp(1,currentTimeStamp); //set time stamp
                preparedStatement2.setInt(2,adminID);
                preparedStatement2.setInt(3,1);

                preparedStatement2.executeUpdate(); // execute
            }

            else {
                // validation
                session.setAttribute("error", "Failed to create user. Please try again.");
            }



        } catch (SQLIntegrityConstraintViolationException e) {
            // This exception typically indicates a duplicate key, like a unique email
            e.printStackTrace();
            session.setAttribute("error", "A user with that email already exists.");
        } catch (Exception e) {
            // Catch any other exceptions
            e.printStackTrace();
            session.setAttribute("error", "An error occurred while creating the user: " + e.getMessage());




        } finally {
            // Close database resources in a finally block to ensure they are always closed
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
        // response.sendRedirect(request.getContextPath() + "/admin-dashboard"); //lead back to admin dashboard for a refresh
        response.sendRedirect(request.getContextPath() + "/admin-dashboard");
    }
}
