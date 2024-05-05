<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
  <title>Login Form</title>
  <script src="validation.js" type="text/javascript" ></script>
</head>
<body style="text-align: center">

<h2 style="text-align: center">Login Page</h2><br>

<div class="login-wrap" >
  <div class="login-html" >
    <br><br>
    <div class="login-form">
      <form name="SignIn" action="login" onsubmit="return validateSignInForm()" method="post">
        <div class="sign-in-htm">
          <div class="group">
            <label for="user" class="label">Username</label>
            <input id="user" type="text" name="user" class="input">
          </div>
          <br>
          <div class="group">
            <label for="logpass" class="label">Password</label>
            <input id="logpass" type="password" name="logpass" class="input" data-type="password">
          </div>
          <br>
          <div class="group">
            <input type="submit" class="button" value="Sign In">
          </div>
        </div>
      </form>
    </div>
  </div>
</div>
</body>
</html>