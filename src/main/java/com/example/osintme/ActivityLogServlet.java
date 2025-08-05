package com.example.osintme;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Servlet for handling the activity log.
 * A second query is used after every admin action to add to this log.
 */
@WebServlet(name = "ActivityLogServlet", urlPatterns = {"/activity-log-servlet"})
public class ActivityLogServlet extends HttpServlet {

    private static final String JDBC_DRIVER = "com.mysql.cj.jdbc.Driver";
    private static final String DB_URL = "jdbc:mysql://localhost/osintme";
    private static final String USER = "root"; // Replace with your MySQL username
    private static final String PASS = "or08le49"; // Replace with your MySQL password


    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        Connection conn = null;
        Statement stmt = null;
        ResultSet rs = null;

        // Create a list to store the activity log entries
        //WE ARE USING THE SAME LOGIC FROM THE ACCOUNT MANAGEMENT TABLE TO POPULATE THIS TABLE
        List<ActivityLogEntry> activityLogList = new ArrayList<>();

        try {

            Class.forName(JDBC_DRIVER);
            conn = DriverManager.getConnection(DB_URL, USER, PASS);
            stmt = conn.createStatement();
            String sql = "SELECT log_id, timestamp, user_id, action_id FROM osintme.activity_log";
            rs = stmt.executeQuery(sql);


            while (rs.next()) {

                ActivityLogEntry entry = new ActivityLogEntry();
                entry.setLogId(rs.getInt("log_id"));
                entry.setTimestamp(rs.getTimestamp("timestamp"));
                entry.setUserId(rs.getInt("user_id"));
                entry.setActionId(rs.getInt("action_id"));

                activityLogList.add(entry);
            }


            request.setAttribute("activityLogList", activityLogList);

            if (rs != null) rs.close();
            if (stmt != null) stmt.close();


            request.getRequestDispatcher("/activity_log.jsp").forward(request, response);

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