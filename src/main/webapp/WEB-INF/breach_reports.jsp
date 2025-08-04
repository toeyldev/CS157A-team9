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

        <p><strong>Scan ID:</strong> ${scanId}</p>

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

        <br>

        <%-- Mitigation Guide --%>
        <c:if test="${not empty mitigationMap}">
            <h2>Mitigation Guide</h2>
            <c:forEach var="entry" items="${mitigationMap}">
                <div class="mitigation-box">
                    <h3><c:out value="${entry.key}"/></h3>
                    <ul>
                        <c:forEach var="step" items="${entry.value}">
                            <li><c:out value="${step}"/></li>
                        </c:forEach>
                    </ul>
                </div>
            </c:forEach>
        </c:if>
    </div>
</body>
</html>
