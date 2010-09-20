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
  <title>NFL Pool - Predictions for week ${week.id}</title>
  <script type="text/javascript">
  function mutex(a,name) {
    if(a.checked) {
      var b = document.forms[0].elements[name];
      if(b) { b.checked = false }
    }
  }
  </script>
</head>

<body>

  <h1>Predictions for week ${week.id}</h1>

  <%@ include file="/WEB-INF/jsp/welcome.jsp" %>

  <form>
    <input type="hidden" name="weekId" value="${week.id}" />
    <table>
    <c:forEach items="${week.games}" var="game">
      <tr>
        <td>
          <c:if test="${!game.complete}">
            <input id="away${game.id}" type="checkbox"
                   name="winner" value="${game.awayTeam.abbr}"
                   <c:if test="${game.awayScore > game.homeScore}">checked="checked"</c:if>
                   onChange="mutex(this,'home${game.id}');" />
          </c:if>
        </td>
        <td><img class="teamlogo" src="../images/${game.awayTeam.abbr}.gif" /></td>
        <td class="${game.complete ? (game.awayScore > game.homeScore ? 'winningTeam' : 'losingTeam') : 'incomplete'}">${game.awayTeam.location}</td>
       <td>&nbsp;&nbsp;&nbsp;</td>
       <td>
         <c:if test="${!game.complete}">
           <input id="home${game.id}" type="checkbox"
                  name="winner" value="${game.homeTeam.abbr}"
                   <c:if test="${game.homeScore > game.awayScore}">checked="checked"</c:if>
                  onChange="mutex(this,'away${game.id}');" />
         </c:if>
       </td>
       <td><img class="teamlogo" src="../images/${game.homeTeam.abbr}.gif" /></td>
       <td class="${game.complete ? (game.homeScore > game.awayScore ? 'winningTeam' : 'losingTeam') : 'incomplete'}">${game.homeTeam.location}</td>
      </tr>
    </c:forEach>
    </table>
    <input type="submit" value="Predict" />
  </form>

  <c:if test="${fn:length(predictions) > 1}">
  <table>
    <tr>
      <th>Player</th>
      <th># Winning Outcomes</th>
      <th>Outcomes</th>
      <th>Teams that must win</th>
    </tr>
    <c:forEach items="${predictions}" var="prediction">
    <tr>
      <td>${prediction.player.name}</td>
      <td>${prediction.outcomeCount}</td>
      <td>
        <c:forEach items="${prediction.outcomes}" var="outcome">
          <span>${outcome}</span><br />
        </c:forEach>
      </td>
      <td>${prediction.mustWins}</td>
    </tr>
    </c:forEach>
  </table>
  </c:if>

  <c:if test="${results != null}">
  <table border="1" cellpadding="3">
    <tr>
      <th>Player</th>
      <th>Score</th>
      <th>W-L</th>
      <th>Tiebreaker (Diff.)</th>
      <th>Points lost</th>
      <th>Points remaining</th>
    </tr>
    <c:forEach items="${results}" var="result">
    <tr align="right">
      <td>${result.player.name}</td>
      <td>${result.points}</td>
      <td>${result.gamesWon}-${result.gamesLost}</td>
      <td>${result.tiebreaker}&nbsp;(${result.tiebreakerDiff})</td>
      <td>${result.pointsLost}</td>
      <td>${result.pointsRemaining}</td>
    </tr>
    </c:forEach>
  </table>
  </c:if>

</body>

</html>
