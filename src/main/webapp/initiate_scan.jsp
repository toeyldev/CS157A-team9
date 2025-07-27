<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
  <head>
    <title>Initiate Scan</title>
    <link rel="stylesheet" href="css/scan.css" />
  </head>

  <div class="back-button-container">
    <a href="${pageContext.request.contextPath}/dashboard" class="button-link"
      >Back to Dashboard</a
    >
  </div>

  <body>
    <div class="container">
      <h2>Initiate Scan</h2>

      <form
        method="post"
        action="${pageContext.request.contextPath}/scan-servlet"
      >
        <div class="pii-grid">
          <div>
            <label for="first_name">First Name</label>
            <input
              type="text"
              id="first_name"
              name="first_name"
              value="${first_name}"
            />
          </div>

          <div>
            <label for="middle_name">Middle Name</label>
            <input
              type="text"
              id="middle_name"
              name="middle_name"
              value="${middle_name}"
            />
          </div>

          <div>
            <label for="last_name">Last Name</label>
            <input
              type="text"
              id="last_name"
              name="last_name"
              value="${last_name}"
            />
          </div>

          <div>
            <label for="address">Address</label>
            <input type="text" id="address" name="address" value="${address}" />
          </div>

          <div>
            <label for="state">State</label>
            <input type="text" id="state" name="state" value="${state}" />
          </div>

          <div>
            <label for="zip_code">Zip Code</label>
            <input
              type="text"
              id="zip_code"
              name="zip_code"
              value="${zip_code}"
            />
          </div>

          <div>
            <label for="city">City</label>
            <input type="text" id="city" name="city" value="${city}" />
          </div>

          <div>
            <label for="phone">Phone Number</label>
            <input type="text" id="phone" name="phone" value="${phone}" />
          </div>

          <div>
            <label for="birthday">Birthday</label>
            <input
              type="text"
              id="birthday"
              name="birthday"
              value="${birthday}"
            />
          </div>

          <div>
            <label for="email">Email</label>
            <input type="text" id="email" name="email" value="${email}" />
          </div>

          <div>
            <label for="nickname">Nickname</label>
            <input
              type="text"
              id="nickname"
              name="nickname"
              value="${nickname}"
            />
          </div>
        </div>

        <div style="margin-top: 12px">
          <button type="submit">Initiate Scan</button>
        </div>
      </form>
    </div>
  </body>
</html>
