<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Account Management - OSINTMe</title>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <link rel="stylesheet" href="css/activitylog.css">
</head>
<body>
    <main class="main-content">
        <div id="activity" class="tab-content">
            <div class="page-header">
                <h1>Activity Log</h1>
                <p>Track user activities and system events</p>
            </div>

            <div class="activity-log">
                <div class="log-filters">
                    <select id="log-type-filter">
                        <option value="">All Activities</option>
                        <option value="login">Login</option>
                        <option value="user-created">User Created</option>
                        <option value="user-deleted">User Deleted</option>
                        <option value="breach-detected">Breach Detected</option>
                    </select>
                    <input type="date" id="log-date-filter">
                </div>

                <div class="log-entries" id="activity-log-entries">
                    </div>
            </div>
        </div>
    </main>
</body>
</html>