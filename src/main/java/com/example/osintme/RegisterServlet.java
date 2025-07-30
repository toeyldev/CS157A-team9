package com.example.osintme;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;
import java.sql.*;
// import org.mindrot.jbcrypt.BCrypt;

@WebServlet(name = "RegisterServlet", value = "/register-servlet")
public class RegisterServlet extends HttpServlet {
    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            String first_name = request.getParameter("first_name");
            String email = request.getParameter("email");
            String password = request.getParameter("password");

            // Hashing the password?
            // String hashPassword = BCrypt.hashpw(password, BCrypt.gensalt());

            // Connection to MySQL
            Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/osintme", "root", "helloworld");
            
            // Query to check if user_id/email already exists in the db
            String checkEmailSql = "SELECT user_id FROM User WHERE email = ?";
            PreparedStatement prepare = connection.prepareStatement(checkEmailSql);
            prepare.setString(1, email);
            ResultSet result = prepare.executeQuery();

            // Returns [] if so:
            if (result.next()) {
                // []
            }

            // If above didn't return anything, make a new user
            String registerSql = "INSERT INTO User (email, password) VALUES (?, ?)";
            prepare.setString(1, email);
            prepare.setString(2, password);
            int affectedRows = prepare.executeUpdate();

            if (affectedRows > 0) {

            }
            
            // have to make email verifhy first

        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }
}
