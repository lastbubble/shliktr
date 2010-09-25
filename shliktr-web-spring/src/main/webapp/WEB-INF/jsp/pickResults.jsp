<?xml version="1.0" encoding="ISO-8859-1"?>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<table cellspacing="0" cellpadding="3">
  <c:forEach items="${picks.picks}" var="pick">
    <tr class="${pick.game.complete ? (pick.correct ? 'correct' : 'incorrect') : 'incomplete'}">
      <td><img class="teamlogo" src="../images/${pick.game.awayTeam.abbr}.gif" /></td>
      <td class="${(pick.team == pick.game.awayTeam) ? 'picked' : ''}">${pick.game.awayTeam.location}</td>
      <td align="right">${pick.game.awayScore}</td>
      <td>&nbsp;&nbsp;&nbsp;</td>
      <td><img class="teamlogo" src="../images/${pick.game.homeTeam.abbr}.gif" /></td>
      <td class="${(pick.team == pick.game.homeTeam) ? 'picked' : ''}">${pick.game.homeTeam.location}</td>
      <td align="right">${pick.game.homeScore}</td>
      <td>&nbsp;&nbsp;&nbsp;</td>
      <td align="right"><span style="font-weight: bold; font-size: 1.25em;">${pick.ranking}</span></td>
    </tr>
  </c:forEach>
</table>
