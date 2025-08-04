package com.example.osintme;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;
import java.sql.*;
import java.util.*;

@WebServlet(name="ViewReportServlet", urlPatterns={"/report"})
public class ViewReportServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
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

            Map<String,List<String>> masterGuide = new LinkedHashMap<>();
            masterGuide.put("Email addresses", Arrays.asList(
                    "Reset your email password immediately.",
                    "Enable two-factor authentication (2FA) on your email account.",
                    "Review and update account recovery settings."
            ));
            masterGuide.put("Passwords", Arrays.asList(
                    "Reset passwords for all affected accounts immediately.",
                    "Use a trusted password manager to generate and store strong unique passwords.",
                    "Avoid re-using passwords across sites.",
                    "Enable two-factor authentication (2FA)."
            ));
            masterGuide.put("Social media profiles", Arrays.asList(
                    "Avoid sharing sensitive information.",
                    "Review and update friend/follower lists.",
                    "Update visibility settings from public to private.",
                    "Revoke third-party app permissions you no longer use."
            ));
            masterGuide.put("Phone numbers", Arrays.asList(
                    "Use app-based authentication over SMS when possible.",
                    "Considering using a secondary number such as via Google Voice.",
                    "Only share phone number with trusted applications."
            ));
            masterGuide.put("IP addresses", Arrays.asList(
                    "Use a trusted VPN to mask real IP address.",
                    "Change router’s admin password from the factory default to a strong password.",
                    "Avoid sharing sensitive information over unsecure internet connection."
            ));
            masterGuide.put("Physical addresses", Arrays.asList(
                    "Remove your address from people-search and data-broker sites.",
                    "Place a fraud alert with credit bureaus."
            ));
            masterGuide.put("Usernames", Arrays.asList(
                    "Avoid re-using the same username across different sites.",
                    "Choose nondescriptive username that doesn't reveal personal information.",
                    "Change username and password."
            ));
            masterGuide.put("Geographic locations", Arrays.asList(
                    "Limit location sharing on apps."
            ));

            Map<String,List<String>> mitigationMap = new LinkedHashMap<>();
            for (Breach b : breaches) {
                for (String dc : b.getDataClasses()) {
                    if (masterGuide.containsKey(dc) && !mitigationMap.containsKey(dc)) {
                        mitigationMap.put(dc, masterGuide.get(dc));
                    }
                }
            }

            req.setAttribute("mitigationMap", mitigationMap);
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
