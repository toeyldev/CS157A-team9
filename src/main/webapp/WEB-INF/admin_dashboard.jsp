<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
  <link rel="stylesheet" href="${pageContext.request.contextPath}/css/admindashboard.css">
  <title>Admin Dashboard</title>
</head>
<body>
<div class="top-right-logout">
  <a href="${pageContext.request.contextPath}/signin.jsp">Log out</a>
</div>

<h1>Welcome, Admin!</h1>
<p style="text-align:center;">Your User ID: <strong>${userId}</strong></p>

<div class="dashboard-buttons">
  <a href="${pageContext.request.contextPath}/admin_account_management.jsp" class="button-link">User Account Management</a>
  <a href="${pageContext.request.contextPath}/system_metrics.jsp" class="button-link">System Metrics</a>
</div>
</body>
</html>
