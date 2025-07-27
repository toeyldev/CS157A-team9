<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/userdashboard.css">
    <title>User Dashboard</title>
</head>
<body>
<div class="top-right-logout">
    <a href="${pageContext.request.contextPath}/signin.jsp">Log out</a>
</div>

<h1>Welcome, ${name}!</h1>

<div class="dashboard-buttons">
    <a href="${pageContext.request.contextPath}/settings.jsp" class="button-link">Settings</a>
    <a href="${pageContext.request.contextPath}/breach_reports.jsp" class="button-link">Breach Reports</a>
    <a href="${pageContext.request.contextPath}/initiate_scan" class="button-link">Initiate Scan</a>
</div>
</body>
</html>
