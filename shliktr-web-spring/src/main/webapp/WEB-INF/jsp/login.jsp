<?xml version="1.0" encoding="ISO-8859-1"?>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN"
    "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
  <meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1" />
  <link rel="stylesheet" type="text/css" href="../pool.css" />
  <title>NFL Pool - Login</title>
</head>
<body>
  <h1>Please log in to the NFL Pool</h1>
  <c:if test="${not empty param.login_error}">
    <p>
      <font color="red">Your login attempt was not successful, try again.</font>
    </p>
  </c:if>

  <form action="<%= request.getContextPath() %>/app/j_acegi_security_check" method="post">
    <table>
      <tr>
        <td>User:</td>
        <td><input type="text" name="j_username" /></td>
      </tr>
      <tr>
        <td>Password:</td>
        <td><input type="password" name="j_password" /></td>
      </tr>
      <tr>
        <td colspan="2"><input name="submit" type="submit" /></td>
      </tr>
    </table>
  </form>
</body>
</html>
