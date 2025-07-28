package com.example.osintme;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;
import java.sql.*;

@WebServlet(name = "UserDashboardServlet", urlPatterns = {"/dashboard"})
public class UserDashboardServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Check http session connection
        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("userId") == null) {
            response.sendRedirect(request.getContextPath() + "/signin.jsp");
            return;
        }

        int userId = (Integer) session.getAttribute("userId");

        String name = "";
        String infosql = "SELECT first_name FROM Personal_Information WHERE user_id = ?";
        try (Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/osintme", "root", "helloworld");

             PreparedStatement prepare = connection.prepareStatement(infosql)) {
            prepare.setInt(1, userId);

            try (ResultSet result = prepare.executeQuery()) {
                if (result.next()) {
                    name = result.getString("first_name");
                }
            }
        } catch (SQLException e) {
            throw new ServletException("DB error", e);
        }

        request.setAttribute("name", name);
        request.getRequestDispatcher("/WEB-INF/user_dashboard.jsp").forward(request, response);
    }
}