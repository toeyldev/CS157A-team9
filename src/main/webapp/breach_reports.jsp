<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Breach Reports</title>
    <link rel="stylesheet" href="css/breachreport.css"/>
</head>

<div class="back-button-container">
    <a href="${pageContext.request.contextPath}/dashboard" class="button-link"
    >Back to Dashboard</a>
</div>

<div class="container">
    <h1>My Breach Reports</h1>

    <table class="breach-report-table">
        <thead>
            <tr>
                <th>Breach Source</th>
                <th>Exposed Data Type</th>
                <th>Exposure Date</th>
            </tr>
        </thead>
        <tbody>
        </tbody>
    </table>

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
</div>
</html>
