package com.example.osintme;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;
import java.sql.*;

import javax.mail.*;
import javax.mail.internet.*;
import java.util.Properties;
import java.util.Random;

@WebServlet(name = "RegisterCodeServlet", value = "/register-code-servlet")
public class RegisterCodeServlet extends HttpServlet {
    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            // Check http session connection
            HttpSession session = request.getSession();
            if (session == null) {
                response.sendRedirect(request.getContextPath() + "/signin.jsp");
                return;
            }

            // Get parameters from register.jsp
            String first_name = request.getParameter("first_name");
            String email = request.getParameter("email").trim();
            String password = request.getParameter("password");

            // Check if password meets the conditions
            boolean hasUpper, hasLower, hasDigit, hasSpecial;
            hasUpper = hasLower = hasDigit = hasSpecial = false;

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
                request.getRequestDispatcher("/register.jsp").forward(request, response);
                return;
            }

            // Random 6-digit code maker
            String code = Integer.toString(new Random().nextInt(900000) + 100000);

            session.setAttribute("first_name", first_name);
            session.setAttribute("email", email);
            session.setAttribute("password", password);
            // session.setAttribute("code", code);
            session.setAttribute("verify_code", code); // this is a temp session attribute that holds verification code

            // Connection to MySQL
            Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/osintme", "root", "helloworld");
            
            // Query to check if user_id/email already exists in the db
            String checkEmailSql = "SELECT user_id FROM osintme.User WHERE email = ?";
            PreparedStatement prepare = connection.prepareStatement(checkEmailSql);
            prepare.setString(1, email);
            ResultSet result = prepare.executeQuery();

            // Also make sure to check uniqueness of entered email to avoid duplicates
            // If email already exists, dispatch them back to register.jsp
            if (result.next()) {
                request.setAttribute("error","Account already exists for this email.");
                request.getRequestDispatcher("/register.jsp").forward(request, response);
                result.close();
                prepare.close();
                connection.close();
                return;
            }

            // Otherwise, send them the verification code
            String serverEmail = "luongpatrick2@gmail.com";
            String serverPassword = "cmqu pdyd vfmh hsbh";

            // Gmail things
            Properties props = new Properties();
            props.put("mail.smtp.host", "smtp.gmail.com");
            props.put("mail.smtp.port", "587");
            props.put("mail.smtp.auth", "true");
            props.put("mail.smtp.starttls.enable", "true");

            // Email session
            Session emailSession = Session.getInstance(props, new Authenticator() {
                protected PasswordAuthentication getPasswordAuthentication() {
                    return new PasswordAuthentication(serverEmail, serverPassword);
                }
            });

            try {
                // Make the message and send it
                Message message = new MimeMessage(emailSession);
                message.setFrom(new InternetAddress(serverEmail));
                message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(email));
                message.setSubject("Your OSINTMe verification code is: " + code);
                message.setText("Hello,\n\nYour OSINTMe verification code is: " + code + "\n\n" + "Best,\nOSINTMe");
                Transport.send(message);
            }
            catch (MessagingException e) {
                e.printStackTrace();
                request.getRequestDispatcher("/" + e.getMessage()).forward(request, response);
            }

            // Have to set the box attributes before forwarding ig
            request.setAttribute("first_name", first_name);
            request.setAttribute("email", email);
            request.setAttribute("password", password);
            request.getRequestDispatcher("/register.jsp").forward(request, response);

            // Close connection
            result.close();
            prepare.close();
            connection.close();
        }
        catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("error", "Error occured: " + e.getMessage());
            request.getRequestDispatcher("/register.jsp").forward(request, response);
            // request.getRequestDispatcher("/" + e.getMessage()).forward(request, response); // for debugging
        }
    }
}
