<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>
<head>
    <title>User Dashboard</title>
</head>
<body>

    <div align="right" style="margin:8px;">
        <a href="settings.jsp">Settings</a> |
        <a href="signin.jsp">Log Out</a>
    </div>

    <h1><%= "Welcome to User Dashboard!" %>
    </h1>
    <br/>
    <h3>Menu</h3>
    <ul>
        <li>Initiate New Scan</li>
        <li>View Scan Reports</li>
        <li>View Mitigation Guide</li>
    </ul>
</body>
</html>