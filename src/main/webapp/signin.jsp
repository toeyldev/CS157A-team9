<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <link rel="stylesheet" href="css/signin.css">
    <title>Sign In</title>
</head>
<body>
<div>
  <h2>Sign In</h2>
  <form action="${pageContext.request.contextPath}/login-servlet" method="post">
  <div>
      <label for="email">UserID or Email:</label><br/>
      <input type="text" id="email" name="email" required>
    </div>

    <div style="margin-top:8px;">
      <label for="password">Password:</label><br/>
      <input type="password" id="password" name="password" required>
    </div>

    <div style="margin-top:12px;">
      <button type="submit">Sign In</button>
    </div>
  </form>

  <p style="margin-top:16px;">
    <a href="register">Register</a> |
    <a href="forgotPassword">Forgot Password?</a>
  </p>
</body>
</div>
</html>
