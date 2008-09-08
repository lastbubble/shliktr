<?xml version="1.0" encoding="ISO-8859-1"?>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN"
    "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">

<head>
  <meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1" />
  <link rel="stylesheet" type="text/css" href="../pool.css" />
  <title>NFL Pool</title>
</head>

<body>

  <h1>Welcome to the NFL Pool</h1>

  <%@ include file="/WEB-INF/jsp/welcome.jsp" %>

  <a href="viewResults?weekId=${week.id}">View Week ${week.id} Results</a>

  <%@ include file="/WEB-INF/jsp/week.jsp" %>

</body>

</html>