<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>Activity Log - OSINTMe</title>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <!-- Using a more robust, context-relative path for the CSS files -->
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/activitylog.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/admin_account_management.css">
</head>
<body>
    <main class="main-content">
        <div class="page-header">
            <h1>Activity Log</h1>
            <p>Track user activities and system events</p>
        </div>

        <div class="table-container">
            <table class="activity-table">
                <thead>
                    <tr>
                        <th>Log ID</th>
                        <th>Timestamp</th>
                        <th>User ID</th>
                        <th>Action ID</th>
                    </tr>
                </thead>
                <tbody>
                    <c:forEach var="logEntry" items="${activityLogList}">
                        <tr>
                            <td><c:out value="${logEntry.logId}"/></td>
                            <td><c:out value="${logEntry.timestamp}"/></td>
                            <td><c:out value="${logEntry.userId}"/></td>
                            <td><c:out value="${logEntry.actionId}"/></td>
                        </tr>
                    </c:forEach>
                </tbody>
            </table>
        </div>
    </main>
</body>
</html>