package com.example.osintme;

import java.sql.Connection;
import java.sql.Statement;
import java.sql.PreparedStatement;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.annotation.WebServlet;

import java.io.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;

@WebServlet(name = "loginServlet", value = "/login-servlet")
public class LoginServlet extends HttpServlet {
    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            String email = request.getParameter("email");
            String password = request.getParameter("password");

            // Class.forName("com.mysql.cj.jdbc.Driver");

            // Connection to MySql
            Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/osintme", "root", "changeme");

            // Query to check if email password in Users table
            String loginSql = "SELECT * FROM User WHERE email = ? AND password = ?";
            PreparedStatement prepare = connection.prepareStatement(loginSql);
            prepare.setString(1, email);
            prepare.setString(2, password);
            ResultSet result = prepare.executeQuery();

            // Redirects user if result is returned
            if (result.next() == true) {
                response.sendRedirect("admin_dashboard.jsp");
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
