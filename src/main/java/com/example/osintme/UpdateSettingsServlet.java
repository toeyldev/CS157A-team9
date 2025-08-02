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
            String password = request.getParameter("password");

            // Prep for input validation
            request.setAttribute("first_name", first_name);
            request.setAttribute("middle_name", middle_name);
            request.setAttribute("last_name", last_name);
            request.setAttribute("address", address);
            request.setAttribute("state", state);
            request.setAttribute("zip_code", zip_code);
            request.setAttribute("city", city);
            request.setAttribute("phone", phone);
            request.setAttribute("birthday", birthday);
            request.setAttribute("email", email);
            request.setAttribute("nickname", nickname);
            request.setAttribute("password", password);

            // Check if setting inputs are in the correct format
            boolean hasUpper, hasLower, hasDigit, hasSpecial;
            hasUpper = hasLower = hasDigit = hasSpecial = false;

            // first_name, email, and password at least have to be filled
            // Note: mostly up to the user to properly format it if they want an accurate scan + can add more conditions later
            if (first_name == null || first_name.trim().isEmpty() || email == null || email.trim().isEmpty()|| password == null || password.trim().isEmpty()) {
                request.setAttribute("error","First name, email, and password cannot be empty.");
                request.getRequestDispatcher("/settings.jsp").forward(request, response);
                return;
            }

            // first_name requires at least 1 uppercase character
            for (char c : first_name.toCharArray()) {
                if (Character.isUpperCase(c)) {
                    hasUpper = true;
                }
            }
            if (!hasUpper) {
                request.setAttribute("error", "First name must have at least 1 uppercase letter.");
                request.getRequestDispatcher("/settings.jsp").forward(request, response);
                return;
            }
            hasUpper = hasLower = hasDigit = hasSpecial = false;

            // (if not empty) phone requires length 12, digit, and special
            if (phone == null || phone.trim().isEmpty()) {
            } else {
                for (char c : phone.toCharArray()) {
                    if (c == '-') {
                        hasSpecial = true;
                    } else if (Character.isDigit(c)) {
                        hasDigit = true;
                    }
                }
                if (hasDigit && hasSpecial && phone.length() == 12) {
                } else {
                    request.setAttribute("error", "Phone has incorrect format.");
                    request.getRequestDispatcher("/settings.jsp").forward(request, response);
                    return;
                }
            }
            hasUpper = hasLower = hasDigit = hasSpecial = false;

            // (if not empty) birthday requires length 10, digit, and special
            if (birthday == null || birthday.trim().isEmpty()) {
            } else {
                for (char c : birthday.toCharArray()) {
                    if (c == '-') {
                        hasSpecial = true;
                    } else if (Character.isDigit(c)) {
                        hasDigit = true;
                    }
                }
                if (hasDigit && hasSpecial && birthday.length() == 10) {
                } else {
                    request.setAttribute("error", "Birthday has incorrect format.");
                    request.getRequestDispatcher("/settings.jsp").forward(request, response);
                    return;
                }
            }
            hasUpper = hasLower = hasDigit = hasSpecial = false;

            // email requires @ and .
            for (char c : email.toCharArray()) {
                if (c == '@') {
                    hasSpecial = true;
                }
                else if (c == '.') {
                    hasDigit = true;
                }
            }
            if (hasDigit && hasSpecial) {  
            } else {
                request.setAttribute("error", "Email has incorrect format.");
                request.getRequestDispatcher("/settings.jsp").forward(request, response);
                return;
            }
            hasUpper = hasLower = hasDigit = hasSpecial = false;

            // pasword requires 8 characters including a number, uppercase, lowercase, and special character
            for (char c : password.toCharArray()) {
                if (Character.isUpperCase(c)) {
                    hasUpper = true;
                } else if (Character.isLowerCase(c)) {
                    hasLower = true;
                } else if (Character.isDigit(c)) {
                    hasDigit = true;
                } else {
                    hasSpecial = true;
                }
            }
            if (hasUpper && hasLower && hasDigit && hasSpecial && password.length() >= 8) {
            } else {
                request.setAttribute("error","Password should be at least 8 characters including a number, uppercase, lowercase, and special character.");
                request.getRequestDispatcher("/settings.jsp").forward(request, response);
                return;
            }

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
            String passwordSql = "UPDATE osintme.User SET password = ? WHERE user_id = ?";

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
            if (birthday == null || birthday.trim().isEmpty()) {
                prepare.setNull(1, java.sql.Types.DATE);
            } else {
                prepare.setString(1, birthday);
            }
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

            prepare = connection.prepareStatement(passwordSql);
            prepare.setString(1, password);
            prepare.setInt(2, userId);
            affectedRows = prepare.executeUpdate();
            if (affectedRows > 0) {
                request.setAttribute("password", password);
            }

            request.getRequestDispatcher("/settings.jsp").forward(request, response);

            // Close connection
            prepare.close();
            connection.close();
        }
        catch (Exception e) {
            e.printStackTrace();
            request.getRequestDispatcher("/" + e.getMessage()).forward(request, response); // for debugging
        }
    }
}
