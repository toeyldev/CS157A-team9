package com.example.osintme;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;
import java.sql.*;

@WebServlet(name = "UpdateSettingsServlet", value = "/update-settings-servlet")
public class UpdateSettingsServlet extends HttpServlet {
    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            // Check http session connection
            HttpSession session = request.getSession(false);
            if (session == null || session.getAttribute("userId") == null) {
                response.sendRedirect(request.getContextPath() + "/dashboard.jsp");
                return;
            }

            // Gets user ID from the session
            int userId = (Integer) session.getAttribute("userId");

            // Get parameters from settings.jsp
            String first_name = request.getParameter("first_name");
            String middle_name = request.getParameter("middle_name");
            String last_name = request.getParameter("last_name");
            String address = request.getParameter("address");
            String state = request.getParameter("state");
            String zip_code = request.getParameter("zip_code");
            String city = request.getParameter("city");
            String phone = request.getParameter("phone");
            String birthday = request.getParameter("birthday");
            String email = request.getParameter("email");
            String nickname = request.getParameter("nickname");

            // Sql statements to update query
            String firstNameSql = "UPDATE osintme.Personal_Information SET first_name = ? WHERE pii_id IN (SELECT * FROM (SELECT pii_id FROM osintme.Personal_Information WHERE user_id = ?) AS temp)";
            String middleNameSql = "UPDATE osintme.Personal_Information SET middle_name = ? WHERE pii_id IN (SELECT * FROM (SELECT pii_id FROM osintme.Personal_Information WHERE user_id = ?) AS temp)";
            String lastNameSql = "UPDATE osintme.Personal_Information SET last_name = ? WHERE pii_id IN (SELECT * FROM (SELECT pii_id FROM osintme.Personal_Information WHERE user_id = ?) AS temp)";
            String addressSql = "UPDATE osintme.Location SET address = ? WHERE address_id IN (SELECT address_id FROM osintme.Personal_Information WHERE user_id = ?)";
            String stateSql = "UPDATE osintme.Location SET state = ? WHERE address_id IN (SELECT address_id FROM osintme.Personal_Information WHERE user_id = ?)";
            String zipCodeSql = "UPDATE osintme.Location SET zip_code = ? WHERE address_id IN (SELECT address_id FROM osintme.Personal_Information WHERE user_id = ?)";
            String citySql = "UPDATE osintme.Location SET city = ? WHERE address_id IN (SELECT address_id FROM osintme.Personal_Information WHERE user_id = ?)";
            String phoneSql = "UPDATE osintme.Personal_Information SET phone = ? WHERE pii_id IN (SELECT * FROM (SELECT pii_id FROM osintme.Personal_Information WHERE user_id = ?) AS temp)";
            String birthdaySql = "UPDATE osintme.Personal_Information SET birthday = ? WHERE pii_id IN (SELECT * FROM (SELECT pii_id FROM osintme.Personal_Information WHERE user_id = ?) AS temp)";
            String emailSql = "UPDATE osintme.User SET email = ? WHERE user_id = ?";
            String nicknameSql = "UPDATE osintme.Personal_Information SET nickname = ? WHERE pii_id IN (SELECT * FROM (SELECT pii_id FROM osintme.Personal_Information WHERE user_id = ?) AS temp)";

            // Connection to MySQL
            Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/osintme", "root", "helloworld");
            
            // Update first_name in Personal_Information table
            PreparedStatement prepare = connection.prepareStatement(firstNameSql);
            prepare.setString(1, first_name);
            prepare.setInt(2, userId);
            int affectedRows = prepare.executeUpdate();

            // If first_name is found, updates db + sets the {first_name} in settings.jsp to it
            if (affectedRows > 0) {
                request.setAttribute("first_name", first_name);
            }

            prepare = connection.prepareStatement(middleNameSql);
            prepare.setString(1, middle_name);
            prepare.setInt(2, userId);
            affectedRows = prepare.executeUpdate();
            if (affectedRows > 0) {
                request.setAttribute("middle_name", middle_name);
            }

            prepare = connection.prepareStatement(lastNameSql);
            prepare.setString(1, last_name);
            prepare.setInt(2, userId);
            affectedRows = prepare.executeUpdate();
            if (affectedRows > 0) {
                request.setAttribute("last_name", last_name);
            }

            prepare = connection.prepareStatement(addressSql);
            prepare.setString(1, address);
            prepare.setInt(2, userId);
            affectedRows = prepare.executeUpdate();
            if (affectedRows > 0) {
                request.setAttribute("address", address);
            }

            prepare = connection.prepareStatement(stateSql);
            prepare.setString(1, state);
            prepare.setInt(2, userId);
            affectedRows = prepare.executeUpdate();
            if (affectedRows > 0) {
                request.setAttribute("state", state);
            }

            prepare = connection.prepareStatement(zipCodeSql);
            prepare.setString(1, zip_code);
            prepare.setInt(2, userId);
            affectedRows = prepare.executeUpdate();
            if (affectedRows > 0) {
                request.setAttribute("zip_code", zip_code);
            }

            prepare = connection.prepareStatement(citySql);
            prepare.setString(1, city);
            prepare.setInt(2, userId);
            affectedRows = prepare.executeUpdate();
            if (affectedRows > 0) {
                request.setAttribute("city", city);
            }

            prepare = connection.prepareStatement(phoneSql);
            prepare.setString(1, phone);
            prepare.setInt(2, userId);
            affectedRows = prepare.executeUpdate();
            if (affectedRows > 0) {
                request.setAttribute("phone", phone);
            }

            prepare = connection.prepareStatement(birthdaySql);
            prepare.setString(1, birthday);
            prepare.setInt(2, userId);
            affectedRows = prepare.executeUpdate();
            if (affectedRows > 0) {
                request.setAttribute("birthday", birthday);
            }

            prepare = connection.prepareStatement(emailSql);
            prepare.setString(1, email);
            prepare.setInt(2, userId);
            affectedRows = prepare.executeUpdate();
            if (affectedRows > 0) {
                request.setAttribute("email", email);
            }

            prepare = connection.prepareStatement(nicknameSql);
            prepare.setString(1, nickname);
            prepare.setInt(2, userId);
            affectedRows = prepare.executeUpdate();
            if (affectedRows > 0) {
                request.setAttribute("nickname", nickname);
            }

            // Close connection
            prepare.close();
            connection.close();
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        response.sendRedirect(request.getContextPath() + "/settings");
    }
}
