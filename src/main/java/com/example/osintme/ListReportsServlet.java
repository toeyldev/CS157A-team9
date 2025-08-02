package com.example.osintme;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@WebServlet(name="ListReportsServlet", urlPatterns={"/reports"})
public class ListReportsServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
    {
        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("userId")==null) {
            response.sendRedirect(request.getContextPath() + "/signin.jsp");
            return;
        }
        int userId = (Integer) session.getAttribute("userId");

        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        List<ScanSummary> scans = new ArrayList<>();

        try {
            conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/osintme","root","helloworld"
            );
            String sql = "SELECT scan_id, scan_time, status " + "FROM Scan WHERE user_id=? ORDER BY scan_time DESC";
            ps = conn.prepareStatement(sql);
            ps.setInt(1, userId);
            rs = ps.executeQuery();

            while (rs.next()) {
                scans.add(new ScanSummary(
                        rs.getInt("scan_id"),
                        rs.getTimestamp("scan_time"),
                        rs.getString("status")
                ));
            }

            request.setAttribute("scans", scans);
            request.getRequestDispatcher("/WEB-INF/reports_list.jsp").forward(request, response);
        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("error", "Cannot load reports.");
            request.getRequestDispatcher("/dashboard.jsp").forward(request, response);
        } finally {
            try { if (rs != null) rs.close(); } catch(Exception ignored){}
            try { if (ps != null) ps.close(); } catch(Exception ignored){}
            try { if (conn != null) conn.close(); } catch(Exception ignored){}
        }
    }
}
