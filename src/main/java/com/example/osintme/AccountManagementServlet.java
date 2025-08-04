package com.example.osintme;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;


@WebServlet(name = "AccountManagementServlet", value = "/account-management-servlet")
public class AccountManagementServlet extends HttpServlet {

    private static final String JDBC_DRIVER = "com.mysql.cj.jdbc.Driver";
    private static final String DB_URL = "jdbc:mysql://localhost/osintme";
    private static final String USER = "root"; // Replace with your MySQL username
    private static final String PASS = "or08le49"; // Replace with your MySQL password

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        List<User> users = new ArrayList<>();
        Connection conn = null;
        Statement stmt = null;
        ResultSet rs = null;
        try {

            Class.forName(JDBC_DRIVER);


            conn = DriverManager.getConnection(DB_URL, USER, PASS);
            stmt = conn.createStatement();
            String sql = "SELECT user_id, email,status FROM osintme.user";
            rs = stmt.executeQuery(sql);

            // Extract data from result set
            while (rs.next()) {
                // Retrieve by column name
                int userId = rs.getInt("user_id");
                String email = rs.getString("email");
                String status = rs.getString("status");

                // Create a User object and add it to the list
                User user = new User(userId, email, status);
                users.add(user);
            }

            // Set the list of users as an attribute in the request object
            request.setAttribute("userList", users);

            // Forward the request to JSP page
            request.getRequestDispatcher("/admin_account_management.jsp").forward(request, response);

        }

        catch (SQLException se) {
            se.printStackTrace();
            throw new ServletException("Database access error", se);
        }

        catch (Exception e) {
            e.printStackTrace();
            throw new ServletException("An error occurred", e);

        }
        finally {
            // Close resources
            try { if (rs != null) rs.close(); } catch (SQLException se2) {}
            try { if (stmt != null) stmt.close(); } catch (SQLException se2) {}
            try { if (conn != null) conn.close(); } catch (SQLException se) { se.printStackTrace(); }
        }
    }
}


