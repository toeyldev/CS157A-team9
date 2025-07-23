package com.example.osintme;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

@WebServlet(name = "loginServlet", value = "/login-servlet")
public class LoginServlet extends HttpServlet {
    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            String email = request.getParameter("email");
            String password = request.getParameter("password");

            // Class.forName("com.mysql.cj.jdbc.Driver");

            // Connection to MySql
            Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/osintme", "root", "helloworld");

            // Query to check if email password in Users table
            String loginSql = "SELECT * FROM User WHERE email = ? AND password = ?";
            PreparedStatement prepare = connection.prepareStatement(loginSql);
            prepare.setString(1, email);
            prepare.setString(2, password);
            ResultSet result = prepare.executeQuery();

            // Redirects user if result is returned
            if (result.next()) {
                int userId = result.getInt("user_id");
                String privilege = result.getString("privilege");

                HttpSession session = request.getSession(true);
                session.setAttribute("userId", userId);

                if (privilege.equals("Admin")) {
                    response.sendRedirect(request.getContextPath() + "/admin-dashboard");
                }
                else if (privilege.equals("User")) {
                    response.sendRedirect(request.getContextPath() + "/dashboard");
                }
            } else {
                request.setAttribute("error", "Invalid email or password");
                request.getRequestDispatcher("signin.jsp").forward(request, response);
            }

            // Close connection
            result.close();
            prepare.close();
            connection.close();
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }
}
