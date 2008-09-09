<?xml version="1.0" encoding="ISO-8859-1"?>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN"
    "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">

<head>
  <meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1" />
  <link rel="stylesheet" type="text/css" href="../pool.css" />
  <title>NFL Pool - Results for week ${week.id}</title>
</head>

<body>

  <h1>Results for week ${week.id}</h1>

  <%@ include file="/WEB-INF/jsp/welcome.jsp" %>

  <table border="1" cellpadding="3">
    <tr>
      <th>Player</th>
      <th>Score</th>
      <th>W-L</th>
      <th>Tiebreaker (Diff.)</th>
      <th>Points lost</th>
      <th>Points remaining</th>
    </tr>
    <c:forEach items="${scores}" var="score">
    <tr align="right">
      <td>${score.player.name}</td>
      <td>${score.score}</td>
      <td>${score.record}</td>
      <td>${score.tiebreaker}&nbsp;(${score.tiebreakerDiff})</td>
      <td>${score.lost}</td>
      <td>${score.remaining}</td>
    </tr>
    </c:forEach>
  </table>

  <h2>Tiebreaker</h2>
  <p>${week.tiebreaker}</p>

  <p>Closest to actual tiebreaker result: ${week.tiebreakerAnswer}</p>
  <ul>
  <c:forEach items="${closest}" var="score">
    <li>${score.player.name}: ${score.tiebreaker}</li>  
  </c:forEach>
  </ul>

</body>

</html>
