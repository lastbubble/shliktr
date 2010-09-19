<?xml version="1.0" encoding="ISO-8859-1"?>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN"
    "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">

<head>
  <meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1" />
  <link rel="stylesheet" type="text/css" href="../pool.css" />
  <title>NFL Pool - ${picks.player.name}'s picks for ${picks.week.id}</title>
</head>

<body>

  <h1>${picks.player.name}'s picks for week ${picks.week.id}</h1>

  <%@ include file="/WEB-INF/jsp/welcome.jsp" %>

  <div style="float: left">
    <a href="editPicks?weekId=${picks.week.id}&playerId=${picks.player.id}">Edit picks</a>
	<br />
    <a href="viewWeek?weekId=${picks.week.id}">View week</a>
  </div>

  <div>
    <table>
    <c:forEach items="${picks.picks}" var="pick">
      <tr class="${pick.game.complete ? (pick.correct ? 'correct' : 'incorrect') : 'incomplete'}">
        <td><img class="teamlogo" src="../images/${pick.game.awayTeam.abbr}.gif" /></td>
        <td>${pick.game.awayTeam.location}</td>
        <td>&nbsp;&nbsp;&nbsp;</td>
        <td><img class="teamlogo" src="../images/${pick.game.homeTeam.abbr}.gif" /></td>
        <td>${pick.game.homeTeam.location}</td>
        <td>${pick.team.location}</td>
        <td>${pick.ranking}</td>
      </tr>
    </c:forEach>
    </table>
  </div>

  <div>
    <h2>Tiebreaker</h2>
    <span>${picks.week.tiebreaker}&nbsp;${picks.tiebreaker}</span>
  </div>

</body>

</html>
