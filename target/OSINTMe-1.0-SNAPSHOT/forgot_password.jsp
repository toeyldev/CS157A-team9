<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
  <head>
    <link rel="stylesheet" href="css/register.css" />
    <title>Register</title>
  </head>

  <body>
    <div>
      <h2>Forgot temp</h2>

      <form
        action="${pageContext.request.contextPath}/forgot-servlet"
        method="post"
      >
        <div>
          <label for="email">Email:</label><br />
          <input
            type="text"
            id="email"
            name="email"
            placeholder="name@example.com"
            required
          />
        </div>

        <div>
          <label for="password">Password:</label><br />
          <input
            type="password"
            id="password"
            name="password"
            placeholder="Password"
            required
          />
        </div>

        <div style="margin-top: 12px">
          <button type="submit">Sign In</button>
        </div>
      </form>

      <p style="margin-top: 16px">
        <a href="${pageContext.request.contextPath}/signin.jsp">Sign In</a> |
        <a href="${pageContext.request.contextPath}/register.jsp">Register</a>
      </p>
    </div>
  </body>
</html>
