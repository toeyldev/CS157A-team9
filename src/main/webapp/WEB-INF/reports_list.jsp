<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

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
          <th>ScanID</th>
          <th>Date</th>
          <th>Status</th>
          <th>View</th>
        </tr>
        </thead>
        <tbody>
        <c:forEach var="s" items="${scans}">
          <tr>
            <td>${s.scanId}</td>
            <td><fmt:formatDate value="${s.scanTime}" pattern="yyyy-MM-dd HH:mm:ss" /></td>
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

    <div class="scan-button-container">
      <a href="${pageContext.request.contextPath}/initiate_scan" class="button-link">Initiate New Scan</a>
    </div>
  </div>

</body>

</html>
