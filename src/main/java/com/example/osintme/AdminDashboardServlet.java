package com.example.osintme;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;

@WebServlet(name = "AdminDashboardServlet", urlPatterns = {"/admin-dashboard"})
public class AdminDashboardServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Check http session connection
        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("userId") == null) {
            response.sendRedirect(request.getContextPath() + "/signin.jsp");
            return;
        }

        int userId = (Integer) session.getAttribute("userId");
        request.setAttribute("userId", userId);
        response.sendRedirect(request.getContextPath() + "/account-management-servlet"); //UPDATING PATH FOR CLEARER REQUEST PIPELINE
    }
}