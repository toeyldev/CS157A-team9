<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
  <head>
    <title>Settings</title>
    <link rel="stylesheet" href="css/scan.css" />
  </head>

  <div class="back-button-container">
    <a href="${pageContext.request.contextPath}/dashboard" class="button-link">
      Back to Dashboard
    </a>
  </div>

  <body>
    <div class="container">
      <h2>Your Settings</h2>

      <form
        method="post"
        action="${pageContext.request.contextPath}/update-settings-servlet"
      >
        <div class="pii-grid">
          <div>
            <label for="first_name">First Name</label>
            <input
              type="text"
              id="first_name"
              name="first_name"
              value="${first_name}"
              placeholder="First name"
            />
          </div>

          <div>
            <label for="middle_name">Middle Name</label>
            <input
              type="text"
              id="middle_name"
              name="middle_name"
              value="${middle_name}"
              placeholder="Middle name"
            />
          </div>

          <div>
            <label for="last_name">Last Name</label>
            <input
              type="text"
              id="last_name"
              name="last_name"
              value="${last_name}"
              placeholder="Last name"
            />
          </div>

          <div>
            <label for="address">Address</label>
            <input
              type="text"
              id="address"
              name="address"
              value="${address}"
              placeholder="123 First St"
            />
          </div>

          <div>
            <label for="state">State</label>
            <input
              type="text"
              id="state"
              name="state"
              value="${state}"
              placeholder="CA"
            />
          </div>

          <div>
            <label for="zip_code">Zip Code</label>
            <input
              type="text"
              id="zip_code"
              name="zip_code"
              value="${zip_code}"
              placeholder="12345"
            />
          </div>

          <div>
            <label for="city">City</label>
            <input
              type="text"
              id="city"
              name="city"
              value="${city}"
              placeholder="City"
            />
          </div>

          <div>
            <label for="phone">Phone Number</label>
            <input
              type="text"
              id="phone"
              name="phone"
              value="${phone}"
              placeholder="xxx-xxx-xxxx"
            />
          </div>

          <div>
            <label for="birthday">Birthday</label>
            <input
              type="text"
              id="birthday"
              name="birthday"
              value="${birthday}"
              placeholder="yyyy-mm-dd"
            />
          </div>

          <div>
            <label for="email">Email</label>
            <input
              type="text"
              id="email"
              name="email"
              value="${email}"
              placeholder="name@example.com"
            />
          </div>

          <div>
            <label for="nickname">Nickname</label>
            <input
              type="text"
              id="nickname"
              name="nickname"
              value="${nickname}"
              placeholder="Nickname"
            />
          </div>
        </div>

        <div style="margin-top: 12px">
          <button type="submit">Save Changes</button>
        </div>
      </form>
    </div>
  </body>
</html>
