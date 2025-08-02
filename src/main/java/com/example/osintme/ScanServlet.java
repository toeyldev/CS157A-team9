package com.example.osintme;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@WebServlet(name="ScanServlet", urlPatterns={"/scan"})
public class ScanServlet extends HttpServlet {
    private HIBPService hibp = null;
    private ObjectMapper mapper = new ObjectMapper();

    @Override
    public void init() throws ServletException {
        hibp = new HIBPService(getServletContext());
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("userId") == null) {
            response.sendRedirect(request.getContextPath() + "/signin.jsp");
            return;
        }

        int userId = (Integer) session.getAttribute("userId");
        int piiId  = (Integer) session.getAttribute("piiId");

        String email = request.getParameter("email").trim();
        String json  = hibp.fetchBreaches(email);
        List<Breach> breaches = new ArrayList<>();
        if (json != null && json.startsWith("[")) {
            breaches = mapper.readValue(
                    json, new TypeReference<List<Breach>>() {}
            );
        }

        Connection conn = null;
        PreparedStatement psScan = null;
        PreparedStatement psDetail = null;
        ResultSet rsScanKey = null;

        try {
            conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/osintme","root","helloworld");

            // Insert in Scan
            String scanSql = "INSERT INTO Scan(user_id, pii_id, status) VALUES (?,?, 'Completed')";
            psScan = conn.prepareStatement(scanSql, Statement.RETURN_GENERATED_KEYS);
            psScan.setInt(1, userId);
            psScan.setInt(2, piiId);
            psScan.executeUpdate();

            rsScanKey = psScan.getGeneratedKeys();
            rsScanKey.next();
            int scanId = rsScanKey.getInt(1);

            // Insert into Breach_Details
            String detailSql = "INSERT INTO Breach_Details"
                    + " (scan_id, breach_name, breach_date, data_leaked)"
                    + " VALUES (?,?,?,?)";
            psDetail = conn.prepareStatement(detailSql);
            for (Breach b : breaches) {
                psDetail.setInt(1, scanId);
                psDetail.setString(2, b.getName());
                psDetail.setDate(3, Date.valueOf(b.getBreachDate()));
                psDetail.setString(4, String.join(", ", b.getDataClasses()));
                psDetail.addBatch();
            }
            psDetail.executeBatch();

            // Go to Breach Report
            response.sendRedirect(request.getContextPath() + "/report?scanId=" + scanId);
            return;
        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("error", "Error running scan.");
            request.getRequestDispatcher("/initiate_scan.jsp").forward(request, response);
            return;
        } finally {
            try { if (rsScanKey != null) rsScanKey.close(); } catch(Exception ignored){}
            try { if (psScan    != null) psScan.close();    } catch(Exception ignored){}
            try { if (psDetail  != null) psDetail.close();  } catch(Exception ignored){}
            try { if (conn      != null) conn.close();      } catch(Exception ignored){}
        }
    }
}
