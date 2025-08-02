<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<html>
<head>
    <title>My Breach Report</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/breachreport.css"/>
</head>

<body>
    <div class="back-button-container">
        <a href="${pageContext.request.contextPath}/dashboard" class="button-link">Back to Dashboard</a>
    </div>

    <div class="container">
        <div class="print-area">
        <h1>Breach Report</h1>

        <table class="breach-report-table">
            <thead>
                <tr>
                    <th>Breach Source</th>
                    <th>Exposed Data Type</th>
                    <th>Exposure Date</th>
                </tr>
            </thead>
            <tbody>
                <%-- Check if breach results is empty --%>
                <c:if test="${empty breachList}">
                    <tr>
                        <td colspan="3" style="text-align:center; font-style:italic;">
                            No breach results found.
                        </td>
                    </tr>
                </c:if>

                <c:forEach var="b" items="${breachList}">
                    <tr>
                        <%-- Breach Source --%>
                        <td><c:out value="${b.name}"/></td>
                        <%-- Exposed data type --%>
                        <td>
                            <c:forEach var="dc" items="${b.dataClasses}" varStatus="status">
                                <c:out value="${dc}"/>
                                <c:if test="${!status.last}">, </c:if>
                            </c:forEach>
                        </td>
                        <%-- Exposed date --%>
                        <td><c:out value="${b.breachDate}"/></td>
                    </tr>
                </c:forEach>
            </tbody>
        </table>
        </div>

        <div class="save-pdf-button-container">
            <button onclick="window.print()" class="button-link">Save as PDF</button>
        </div>

        <h2>Mitigation Guide</h2>

        <label for="dataTypeSelect"><strong>Select Exposed Data Type:</strong></label>
        <select id="dataTypeSelect" onchange="updateMitigationGuide()">
            <option value="">-Choose a data type-</option>
            <option value="email">Email</option>
            <option value="password">Password</option>
            <option value="phone">Phone Number</option>
            <option value="address">Physical Address</option>
            <option value="ssn">Birthday</option>
        </select>
    </div>
</body>
</html>
