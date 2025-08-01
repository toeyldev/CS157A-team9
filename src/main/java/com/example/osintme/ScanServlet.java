package com.example.osintme;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;
import java.util.Collections;
import java.util.List;

@WebServlet(name="ScanServlet", urlPatterns={"/scan"})
public class ScanServlet extends HttpServlet {
    private HIBPService hibp;
    private ObjectMapper mapper = new ObjectMapper();

    @Override
    public void init() {
        // Get hibpApiKey and hibpUserAgent from web.xml
        hibp = new HIBPService(getServletContext());
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
    {
        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("userId") == null) {
            response.sendRedirect(request.getContextPath() + "/signin.jsp");
            return;
        }

        String email = request.getParameter("email");
        if (email == null || email.trim().isEmpty()) {
            email = (String) session.getAttribute("email");
        }

        // Call HIBP and parse breaches
        String json = hibp.fetchBreaches(email);
        if (json == null) {
            request.setAttribute("errorMessage", "Unable to reach breach service. Please try again later.");
            request.getRequestDispatcher("/WEB-INF/breach_reports.jsp").forward(request, response);
            return;
        }

        List<Breach> breaches = Collections.emptyList();
        if (json != null && json.startsWith("[")) {
            breaches = mapper.readValue(json, new TypeReference<List<Breach>>() {});
        }

        // Send to breach report
        request.setAttribute("breachList", breaches);
        request.getRequestDispatcher("/WEB-INF/breach_reports.jsp").forward(request, response);
    }
}
