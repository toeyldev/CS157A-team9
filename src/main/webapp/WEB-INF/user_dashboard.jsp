<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
  <link rel="stylesheet" href="${pageContext.request.contextPath}/css/userdashboard.css">
  <title>User Dashboard</title>
</head>

<header class="header">
  <h1 class="header-text">OSINTMe</h1>
</header>

<body>
<div class="back-button-container">
  <a href="${pageContext.request.contextPath}/signin.jsp" class="button-link">Log out</a>
</div>

<h1>Welcome, ${name}!</h1>

<div class="dashboard-buttons">
  <a href="${pageContext.request.contextPath}/settings" class="button-link">Settings</a>
  <a href="${pageContext.request.contextPath}/reports" class="button-link">Breach Reports</a>
  <a href="${pageContext.request.contextPath}/initiate_scan" class="button-link">Initiate Scan</a>
</div>
</body>

<footer class="footer">
  <p>&copy; 2025 OSINTMe. All rights reserved.</p>
</footer>

</html>
