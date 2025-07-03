<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Sign In</title>
</head>
<body>
  <h2>Sign In</h2>
  <form action="login" method="post">
    <div>
      <label for="username">UserID or Email:</label><br/>
      <input type="text" id="username" name="username" required>
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
</html>
