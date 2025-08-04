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
        int breachedCount= 0;
        int activeCount= 0;

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
            request.setAttribute("userList", users);  //END OF FIRST QUERY
            if (rs != null) rs.close();
            if (stmt != null) stmt.close(); // close statement and results

            //QUERY 2 Populate total users stat card
            stmt = conn.createStatement();
            String sqlTotalUsers = "SELECT COUNT(*) AS total FROM osintme.user";
            rs= stmt.executeQuery(sqlTotalUsers);
            if (rs.next()) {
                request.setAttribute("totalUsers", rs.getInt("total"));
            }
            if (rs != null) rs.close();
            if (stmt != null) stmt.close();

            //QUERY 3 Populate the active users stat card
            stmt = conn.createStatement();
            String sqlActiveUsers = "SELECT COUNT(*) AS active FROM osintme.user WHERE STATUS = 'Active' ";
            rs= stmt.executeQuery(sqlActiveUsers);
            if (rs.next()) {
                request.setAttribute("activeUsers", rs.getInt("active"));
                activeCount = rs.getInt("active");
            }

            if (rs != null) rs.close();
            if (stmt != null) stmt.close();

            //QUERY 4 Get unique users in scan table to see who's breached
            stmt = conn.createStatement();
            String sqlBreachedUsers = "SELECT COUNT(DISTINCT user_id) AS breached FROM osintme.scan";
            rs= stmt.executeQuery(sqlBreachedUsers);
            if (rs.next()) {
                request.setAttribute("breachedUsers", rs.getInt("breached"));
                breachedCount = rs.getInt("breached");
            }

            if (rs != null) rs.close();
            if (stmt != null) stmt.close();

            int secureCount = activeCount - breachedCount;
            request.setAttribute("secureAccounts", secureCount );




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


