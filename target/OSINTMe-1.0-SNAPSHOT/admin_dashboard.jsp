<%@ page import="java.sql.*"%>
<html>
<head>
  <title>Admin Dashboard</title>
</head>
<body>
<h1>Welcome to Admin's Dashboard</h1>

<table border="1">
  <tr>
    <td>userID</td>
    <td>email</td>
    <td>name</td>
    <td>password</td>
  </tr>
    <%
     String db = "osintme";
        String user; // assumes database name is the same as username
          user = "root";
        String password = "helloworld";
        try {
            java.sql.Connection con;
            Class.forName("com.mysql.jdbc.Driver");
            con = DriverManager.getConnection("jdbc:mysql://localhost:3306/osintme?autoReconnect=true&useSSL=false",user, password);
            out.println(db + " database successfully opened.<br/><br/>");

            out.println("All user data in table \"Users\": <br/>");
            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM Users");
            while (rs.next()) {
         out.println("<tr>" + "<td>" +  rs.getInt(1) + "</td>"+ "<td>" +    rs.getString(2) + "</td>"+   "<td>" + rs.getString(3) + "</td>"  + "<td>" +  rs.getString(4) + "</td>" + "</tr>");
            }
            rs.close();
            stmt.close();
            con.close();
        } catch(SQLException e) {
            out.println("SQLException caught: " + e.getMessage());
        }
    %>
</body>
</html>
