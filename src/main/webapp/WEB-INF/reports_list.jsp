<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<html>

<head>
  <title>My Breach Report History</title>
  <link rel="stylesheet" href="${pageContext.request.contextPath}/css/breachreport.css"/>
</head>

<body>

  <div class="back-button-container">
    <a href="${pageContext.request.contextPath}/dashboard" class="button-link">Back to Dashboard</a>
  </div>

  <div class="container">
    <h1>My Breach Report History</h1>

    <c:if test="${empty scans}">
      <p>No past scans found.</p>
    </c:if>

    <c:if test="${not empty scans}">
      <table class="breach-report-table">
        <thead>
        <tr>
          <th>Scan #</th>
          <th>Date</th>
          <th>Status</th>
          <th>View</th>
        </tr>
        </thead>
        <tbody>
        <c:forEach var="s" items="${scans}">
          <tr>
            <td>${s.scanId}</td>
            <td>${s.scanTime}</td>
            <td>${s.status}</td>
            <td>
              <a href="${pageContext.request.contextPath}/report?scanId=${s.scanId}">
                View Report
              </a>
            </td>
          </tr>
        </c:forEach>
        </tbody>
      </table>
    </c:if>
  </div>

    <p><a href="${pageContext.request.contextPath}/initiate_scan">Run new scan</a></p>
</body>

</html>
