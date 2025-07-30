<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
  <head>
    <link rel="stylesheet" href="css/register.css" />
    <title>Register</title>
  </head>

  <body>
    <div>
      <h2>Register</h2>

      <form
        action="${pageContext.request.contextPath}/register-servlet"
        method="post"
      >
        <div>
          <label for="first_name">First Name:</label><br />
          <input
            type="text"
            id="first_name"
            name="first_name"
            placeholder="First name"
            required
          />
        </div>

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
          <label for="password"
            >Password:<br /><br />(Password should be at least 8 characters
            including a number, uppercase, lowercase, and special character.)
          </label>
          <input
            type="password"
            id="password"
            name="password"
            placeholder="Password"
            required
          />
        </div>

        <div style="margin-top: 12px">
          <button type="submit">Create Account ></button>
        </div>
      </form>

      <p style="margin-top: 16px">
        <a href="${pageContext.request.contextPath}/signin.jsp">Sign In</a> |
        <a href="${pageContext.request.contextPath}/forgot_password.jsp">
          Forgot Password?
        </a>
      </p>
    </div>
  </body>
</html>
