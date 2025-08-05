<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
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
                    <a href="${pageContext.request.contextPath}/activity-log-servlet" class="nav-link">
                        <span class="nav-icon">📋</span>
                        Activity Log
                    </a>
                </li>
                <li class="nav-item">
                    <a href="${pageContext.request.contextPath}/signin.jsp" class="nav-link logout">
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
                        <h3 id="total-users">${totalUsers}</h3>
                        <p>Total Users</p>
                    </div>
                </div>
                <div class="stat-card">
                    <div class="stat-icon">✅</div>
                    <div class="stat-info">
                        <h3 id="active-users">${activeUsers}</h3>
                        <p>Active Users</p>
                    </div>
                </div>
                <div class="stat-card">
                    <div class="stat-icon">⚠️</div>
                    <div class="stat-info">
                        <h3 id="breached-accounts">${breachedUsers}</h3>
                        <p>Breached Accounts</p>
                    </div>
                </div>
                <div class="stat-card">
                    <div class="stat-icon">🔒</div>
                    <div class="stat-info">
                        <h3 id="secure-accounts">${secureAccounts}</h3>
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
                        Add New User
                    </button>
                </div>

                <!-- Users Table -->
                <div class="table-container">
                    <table class="users-table">
                        <thead>
                            <tr>
                                <th>User ID</th>
                                <th>Email</th>
                                <th>Status</th>
                                <th>Privilege</th>
                                <th>Actions</th>
                            </tr>
                        </thead>
                        <tbody id="users-table-body">
                            <c:forEach var="user" items="${userList}">
                                   <tr>
                                       <td><c:out value="${user.userId}"/></td>
                                       <td><c:out value="${user.email}"/></td>
                                       <td><c:out value="${user.status}"/></td>
                                       <td><c:out value="${user.privilege}"/></td>
                                       <td>
                                           <button class="btn btn-secondary" onclick="openEditUserModal('${user.userId}', '${user.email}', '${user.status}', '${user.privilege}')">Edit</button>
                                           <form action="${pageContext.request.contextPath}/admin-delete-account-servlet" method="post" onsubmit="return confirm('Are you sure you want to delete this account?');">
                                               <input type="hidden" name="userId" value="${user.userId}"/>
                                               <button class="btn btn-danger" type="submit">Delete</button>
                                           </form>
                                       </td>
                                   </tr>
                               </c:forEach>
                        </tbody>
                    </table>
                </div>
            </div>
        </div>

<!--ADDING NEW USER EVENT -->

 <div id="addUserModal" class="modal">
        <div class="modal-content">
            <div class="modal-header">
                <h2 class="modal-title">Add New User</h2>
                <span class="close-btn" onclick="closeAddUserModal()">&times;</span>
            </div>
            <div class="modal-body">
                <!-- The form that submits to the servlet -->
                <form action="${pageContext.request.contextPath}/add-account-servlet" method="post">
                    <div class="form-group">
                        <label for="email">Email address</label>
                        <input type="email" id="email" name="email" required>
                    </div>
                    <div class="form-group">
                        <label for="password">Password</label>
                        <input type="password" id="password" name="password" required>
                    </div>
                    <div class="form-group">
                        <label for="status">Status</label>
                        <select id="status" name="status" required>
                            <option value="Active">Active</option>
                            <option value="Inactive">Inactive</option>
                        </select>
                    </div>
                    <div class="form-group">
                        <label for="privilege">Privilege</label>
                        <select id="privilege" name="privilege" required>
                            <option value="user">User</option>
                            <option value="admin">Admin</option>
                        </select>
                    </div>
                    <div class="form-actions">
                        <button type="submit" class="btn btn-primary">Create User</button>
                        <button type="button" class="btn btn-secondary" onclick="closeAddUserModal()">Cancel</button>
                    </div>
                </form>
            </div>
        </div>
    </div>
<!-- EDIT USER MODAL -->
<div id="editUserModal" class="modal">
    <div class="modal-content">
        <div class="modal-header">
            <h2 class="modal-title">Edit User Account</h2>
            <span class="close-btn" onclick="closeEditUserModal()">&times;</span>
        </div>
        <div class="modal-body">
            <form action="${pageContext.request.contextPath}/edit-account-servlet" method="post">
                <input type="hidden" id="edit-userId" name="userId">
                <div class="form-group">
                    <label for="edit-email">Email address</label>
                    <input type="email" id="edit-email" name="email" required>
                </div>
                <div class="form-group">
                    <label for="edit-password">New Password (optional)</label>
                    <input type="password" id="edit-password" name="password">
                </div>
                <div class="form-group">
                    <label for="edit-status">Status</label>
                    <select id="edit-status" name="status" required>
                        <option value="Active">Active</option>
                        <option value="Deactivated">Deactivated</option>
                    </select>
                </div>
                <div class="form-group">
                    <label for="edit-privilege">Privilege</label>
                    <select id="edit-privilege" name="privilege" required>
                        <option value="user">User</option>
                        <option value="admin">Admin</option>
                    </select>
                </div>
                <div class="form-actions">
                    <button type="submit" class="btn btn-primary">Save Changes</button>
                    <button type="button" class="btn btn-secondary" onclick="closeEditUserModal()">Cancel</button>
                </div>
            </form>
        </div>
    </div>
</div>

<script>
    // Javascript for modals

    // Get the modals
    const addUserModal = document.getElementById("addUserModal");
    const editUserModal = document.getElementById("editUserModal");

    // Function to open the Add User modal
    function openAddUserModal() {
        addUserModal.style.display = "block";
    }

    // Function to close the Add User modal
    function closeAddUserModal() {
        addUserModal.style.display = "none";
    }

    // Function to open the Edit User modal and populate it with data
    function openEditUserModal(userId, email, status, privilege) {
        document.getElementById("edit-userId").value = userId;
        document.getElementById("edit-email").value = email;
        document.getElementById("edit-status").value = status;
        document.getElementById("edit-privilege").value = privilege;
        editUserModal.style.display = "block";
    }

    // Function to close the Edit User modal
    function closeEditUserModal() {
        editUserModal.style.display = "none";
    }

    // Close the modals when the user clicks anywhere outside of them
    window.onclick = function(event) {
        if (event.target === addUserModal) {
            closeAddUserModal();
        }
        if (event.target === editUserModal) {
            closeEditUserModal();
        }
    }
</script>


  <!-- Figure out how to implement CRUD with servlets, this is just front end for now so far (ADD IS COMPLETE)  -->
</body>
</html>