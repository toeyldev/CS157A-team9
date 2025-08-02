<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Account Management - OSINTMe</title>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <link rel="stylesheet" href="css/admin_account_management.css">
</head>
<body>
    <!-- Navigation Bar -->

    <nav class="navbar">
        <div class="nav-container">
            <div class="nav-brand">
                <div class="brand-icon">🛡️</div>
                <span class="brand-text">OSINTMe</span>
            </div>
            <ul class="nav-menu">
                <li class="nav-item">
                    <a href="#dashboard" class="nav-link active" data-tab="dashboard">
                        <span class="nav-icon">📊</span>
                        Dashboard
                    </a>
                </li>

                <li class="nav-item">
                    <a href="#activity" class="nav-link" data-tab="activity">
                        <span class="nav-icon">📋</span>
                        Activity Log
                    </a>
                </li>
                <li class="nav-item">
                    <a href="login" class="nav-link logout">
                        <span class="nav-icon">🚪</span>
                        Logout
                    </a>
                </li>
            </ul>
            <div class="hamburger">
                <span></span>
                <span></span>
                <span></span>
            </div>
        </div>
    </nav>

    <!-- Main Content -->
    <!-- Instead of having a separate system metrics page, we will use stat cards to display metrics on the dashboard -->


    <main class="main-content">
        <!-- Dashboard Tab -->
        <div id="dashboard" class="tab-content active">
            <div class="page-header">
                <h1>User Account Management</h1>
                <p>Manage user accounts and monitor system security</p>
            </div>

            <!-- Stats Cards -->
            <div class="stats-grid">
                <div class="stat-card">
                    <div class="stat-icon">👥</div>
                    <div class="stat-info">
                        <h3 id="total-users">0</h3>
                        <p>Total Users</p>
                    </div>
                </div>
                <div class="stat-card">
                    <div class="stat-icon">✅</div>
                    <div class="stat-info">
                        <h3 id="active-users">0</h3>
                        <p>Active Users</p>
                    </div>
                </div>
                <div class="stat-card">
                    <div class="stat-icon">⚠️</div>
                    <div class="stat-info">
                        <h3 id="breached-accounts">0</h3>
                        <p>Breached Accounts</p>
                    </div>
                </div>
                <div class="stat-card">
                    <div class="stat-icon">🔒</div>
                    <div class="stat-info">
                        <h3 id="secure-accounts">0</h3>
                        <p>Secure Accounts</p>
                    </div>
                </div>
            </div>

            <!-- User Management Section -->
            <div class="management-section">
                <div class="section-header">
                    <h2>User Management</h2>
                    <button class="btn btn-primary" onclick="openAddUserModal()">
                        <span class="btn-icon">➕</span>
                        Add New Admin
                    </button>
                </div>

                <!-- Search and Filter -->
                <div class="search-filter">
                    <div class="search-box">
                        <input type="text" id="user-search" placeholder="Search users..." onkeyup="filterUsers()">
                        <span class="search-icon">🔍</span>
                    </div>
                    <select id="status-filter" onchange="filterUsers()">
                        <option value="">All Status</option>
                        <option value="active">Active</option>
                        <option value="inactive">Inactive</option>
                        <option value="breached">Breached</option>
                    </select>
                </div>

                <!-- Users Table -->
                <div class="table-container">
                    <table class="users-table">
                        <thead>
                            <tr>
                                <th>User ID</th>
                                <th>Email</th>
                                <th>Name</th>
                                <th>Status</th>
                                <th>Actions</th>
                            </tr>
                        </thead>
                        <tbody id="users-table-body">
                            <! -- figure out how to pull from workbench to populate table with users here -->
                        </tbody>
                    </table>
                </div>
            </div>
        </div>

        <!-- Activity Log Tab -->
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
                    <!-- Activity entries will be populated here -->
                </div>
            </div>
        </div>
    </main>

  <!-- Figure out how to implement CRUD with servlets, this is just front end for now -->
</body>
</html>