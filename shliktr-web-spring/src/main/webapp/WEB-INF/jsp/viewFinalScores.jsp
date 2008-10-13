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
  <title>NFL Pool - Final Scores</title>
</head>

<body>

  <h1>Final Scores</h1>

  <%@ include file="/WEB-INF/jsp/welcome.jsp" %>

  <table border="1" cellpadding="3">
    <tr>
      <th>Player</th>
      <th>Score</th>
      <th colspan="17">Weekly scores (week in parentheses)</th>
    </tr>
    <c:forEach items="${bank.finalScores}" var="finalScore">
    <tr align="right">
      <td>${finalScore.player.name}</td>
      <td>${finalScore.points}</td>
      <c:forEach items="${weekIds}" var="weekId">
        <td>
          <c:if test="${fn:length(finalScore.results) >= weekId}">
            ${finalScore.results[weekId - 1].points}&nbsp(${finalScore.results[weekId - 1].week})
          </c:if>
        </td>
      </c:forEach>
    </tr>
    </c:forEach>
  </table>

  <table border="1" cellpadding="3">
    <tr>
      <th>Player</th>
      <th>Account</th>
    </tr>
    <c:forEach items="${bank.accounts}" var="account">
    <tr align="right">
      <td>${account.player.name}</td>
      <td>${account.points}</td>
    </c:forEach>
    </tr>
  </table><br />
  <div>Reserve: ${bank.reserve}</div>

</body>

</html>
