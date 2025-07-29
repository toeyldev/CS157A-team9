package com.example.osintme;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;
import java.sql.*;

@WebServlet(name = "loadScanServlet", urlPatterns = {"/initiate_scan"})
public class LoadScanServlet extends HttpServlet {
    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            // Check http session connection
            HttpSession session = request.getSession(false);
            if (session == null || session.getAttribute("userId") == null) {
                response.sendRedirect(request.getContextPath() + "/dashboard.jsp");
                return;
            }

            // Gets user ID from the session
            int userId = (Integer) session.getAttribute("userId");

            String first_name, middle_name, last_name, address, state, zip_code, city, phone, birthday, email, nickname;
            first_name = middle_name = last_name = address = state = zip_code = city = phone = birthday = email = nickname = "";

            String firstNameSql = "SELECT first_name FROM osintme.Personal_Information WHERE user_id = ?";
            String middleNameSql = "SELECT middle_name FROM osintme.Personal_Information WHERE user_id = ?";
            String lastNameSql = "SELECT last_name FROM osintme.Personal_Information WHERE user_id = ?";
            String addressSql = "SELECT address FROM osintme.Location WHERE address_id IN (SELECT address_id FROM osintme.Personal_Information WHERE user_id = ?)";
            String stateSql = "SELECT state FROM osintme.Location WHERE address_id IN (SELECT address_id FROM osintme.Personal_Information WHERE user_id = ?)";
            String zipCodeSql = "SELECT zip_code FROM osintme.Location WHERE address_id IN (SELECT address_id FROM osintme.Personal_Information WHERE user_id = ?)";
            String citySql = "SELECT city FROM osintme.Location WHERE address_id IN (SELECT address_id FROM osintme.Personal_Information WHERE user_id = ?)";
            String phoneSql = "SELECT phone FROM osintme.Personal_Information WHERE user_id = ?";
            String birthdaySql = "SELECT birthday FROM osintme.Personal_Information WHERE user_id = ?";
            String emailSql = "SELECT email FROM osintme.User WHERE user_id = ?";
            String nicknameSql = "SELECT nickname FROM osintme.Personal_Information WHERE user_id = ?";

            // Connection to MySql
            Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/osintme", "root", "helloworld");

            // Query to check if first_name present in User table
            PreparedStatement prepare = connection.prepareStatement(firstNameSql);
            prepare.setInt(1, userId);
            ResultSet result = prepare.executeQuery();

            // If first_name is found, sets the {first_name} in initiate_scan.jsp to it
            if (result.next()) {
                first_name = result.getString("first_name");
                request.setAttribute("first_name", first_name);
            }

            prepare = connection.prepareStatement(middleNameSql);
            prepare.setInt(1, userId);
            result = prepare.executeQuery();
            if (result.next()) {
                middle_name = result.getString("middle_name");
                request.setAttribute("middle_name", middle_name);
            }

            prepare = connection.prepareStatement(lastNameSql);
            prepare.setInt(1, userId);
            result = prepare.executeQuery();
            if (result.next()) {
                last_name = result.getString("last_name");
                request.setAttribute("last_name", last_name);
            }

            prepare = connection.prepareStatement(addressSql);
            prepare.setInt(1, userId);
            result = prepare.executeQuery();
            if (result.next()) {
                address = result.getString("address");
                request.setAttribute("address", address);
            }

            prepare = connection.prepareStatement(stateSql);
            prepare.setInt(1, userId);
            result = prepare.executeQuery();
            if (result.next()) {
                state = result.getString("state");
                request.setAttribute("state", state);
            }

            prepare = connection.prepareStatement(zipCodeSql);
            prepare.setInt(1, userId);
            result = prepare.executeQuery();
            if (result.next()) {
                zip_code = result.getString("zip_code");
                request.setAttribute("zip_code", zip_code);
            }

            prepare = connection.prepareStatement(citySql);
            prepare.setInt(1, userId);
            result = prepare.executeQuery();
            if (result.next()) {
                city = result.getString("city");
                request.setAttribute("city", city);
            }

            prepare = connection.prepareStatement(phoneSql);
            prepare.setInt(1, userId);
            result = prepare.executeQuery();
            if (result.next()) {
                phone = result.getString("phone");
                request.setAttribute("phone", phone);
            }

            prepare = connection.prepareStatement(birthdaySql);
            prepare.setInt(1, userId);
            result = prepare.executeQuery();
            if (result.next()) {
                birthday = result.getString("birthday");
                request.setAttribute("birthday", birthday);
            }

            prepare = connection.prepareStatement(emailSql);
            prepare.setInt(1, userId);
            result = prepare.executeQuery();
            if (result.next()) {
                email = result.getString("email");
                request.setAttribute("email", email);
            }

            prepare = connection.prepareStatement(nicknameSql);
            prepare.setInt(1, userId);
            result = prepare.executeQuery();
            if (result.next()) {
                nickname = result.getString("nickname");
                request.setAttribute("nickname", nickname);
            }

            // Close connection
            result.close();
            prepare.close();
            connection.close();
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        request.getRequestDispatcher("/initiate_scan.jsp").forward(request, response);
    }
}