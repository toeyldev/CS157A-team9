<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
  <link rel="stylesheet" href="${pageContext.request.contextPath}/css/admindashboard.css">
  <title>Admin Dashboard</title>
</head>

<header class="header">
  <h1 class="header-text">OSINTMe</h1>
</header>

<body>
<div class="back-button-container">
  <a href="${pageContext.request.contextPath}/signin.jsp" class="button-link">Log out</a>
</div>

<h1>Welcome, Admin!</h1>
<p style="text-align:center;">Your User ID: <strong>${userId}</strong></p>

<div class="dashboard-buttons">
  <a href="${pageContext.request.contextPath}/admin_account_management.jsp" class="button-link">User Account Management</a>
</div>
</body>

<footer class="footer">
  <p>&copy; 2025 OSINTMe. All rights reserved.</p>
</footer>

</html>
