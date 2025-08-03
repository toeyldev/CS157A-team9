package com.example.osintme;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@WebServlet(name="ViewReportServlet", urlPatterns={"/report"})
public class ViewReportServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException
    {
        String sid = req.getParameter("scanId");
        if (sid == null) {
            resp.sendRedirect(req.getContextPath() + "/reports");
            return;
        }
        int scanId = Integer.parseInt(sid);

        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        List<Breach> breaches = new ArrayList<>();

        try {
            conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/osintme","root","helloworld");
            String sql = "SELECT breach_name, breach_date, data_leaked "
                    + "FROM Breach_Details WHERE scan_id=? "
                    + "ORDER BY breach_date DESC";
            ps = conn.prepareStatement(sql);
            ps.setInt(1, scanId);
            rs = ps.executeQuery();

            while (rs.next()) {
                Breach b = new Breach();
                b.setName(rs.getString("breach_name"));
                b.setBreachDate(rs.getDate("breach_date").toString());
                b.setDataClasses(Arrays.asList(rs.getString("data_leaked").split("\\s*,\\s*")));
                breaches.add(b);
            }

            req.setAttribute("breachList", breaches);
            req.setAttribute("scanId", scanId);
            req.getRequestDispatcher("/WEB-INF/breach_reports.jsp").forward(req, resp);
        } catch (Exception e) {
            e.printStackTrace();
            req.setAttribute("error","Cannot load report.");
            req.getRequestDispatcher("/dashboard.jsp").forward(req, resp);
        } finally {
            try { if (rs != null) rs.close(); } catch(Exception ignored){}
            try { if (ps != null) ps.close(); } catch(Exception ignored){}
            try { if (conn != null) conn.close(); } catch(Exception ignored){}
        }
    }
}
