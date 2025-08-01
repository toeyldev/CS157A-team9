<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
  <head>
    <title>Forgot Password</title>
    <link rel="stylesheet" href="css/register.css" />
  </head>

  <body>
    <div>
      <h2>Forgot Your Password</h2>

      <!-- Send code section of the page -->
      <form
        action="${pageContext.request.contextPath}/forgot-code-servlet"
        method="post"
      >
        <div>
          <label for="email">Email:</label><br />
          <input
            type="text"
            id="email"
            name="email"
            value="${email}"
            placeholder="name@example.com"
            required
          />
        </div>

        <div style="margin-top: 12px">
          <button type="submit">Send Code</button>
        </div>
      </form>

      <!-- Email verify section of the page -->
      <form
        action="${pageContext.request.contextPath}/forgot-servlet"
        method="post"
      >
        <div>
          <label>6-Digit Code:</label>
          <input type="text" name="code" placeholder="6-digit code" required />
        </div>

        <div style="margin-top: 12px">
          <button type="submit">Verify Code ></button>
        </div>
      </form>

      <p style="margin-top: 16px">
        <a href="${pageContext.request.contextPath}/signin.jsp">
          Already have an account?
        </a>
        |
        <a href="${pageContext.request.contextPath}/register.jsp">Register</a>
      </p>
    </div>
  </body>
</html>
