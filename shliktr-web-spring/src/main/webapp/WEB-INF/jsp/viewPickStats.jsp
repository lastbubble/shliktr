<?xml version="1.0" encoding="ISO-8859-1"?>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN"
    "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">

<head>
  <meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1" />
  <link rel="stylesheet" type="text/css" href="../pool.css" />
  <title>NFL Pool - Pick statistics for week ${week.id}</title>
</head>

<body>

  <h1>Pick statistics for week ${week.id}</h1>

  <%@ include file="/WEB-INF/jsp/welcome.jsp" %>

  <table>
    <tr>
      <th>Team</th>
      <th>Times Picked</th>
      <th>Total</th>
      <th>Rankings</th>
    </tr>
    <c:forEach items="${statsList}" var="stats">
    <tr>
      <td>${stats.team.location}</td>
      <td>${fn:length(stats.rankings)}</td>
      <td>${stats.total}</td>
      <td>${stats.rankings}</td>
    </tr>
    </c:forEach>
  </table>

</body>

</html>
