<div>
  <table><c:forEach items="${week.games}" var="game">
    <tr>
      <td><img class="teamlogo" src="../images/${game.awayTeam.abbr}.gif" /></td>
      <td class="${game.awayScore > game.homeScore ? 'winningTeam' : 'losingTeam'}">${game.awayTeam.location}</td>
      <td>${game.awayScore}</td>
      <td>&nbsp;&nbsp;&nbsp;</td>
      <td><img class="teamlogo" src="../images/${game.homeTeam.abbr}.gif" /></td>
      <td class="${game.homeScore > game.awayScore ? 'winningTeam' : 'losingTeam'}">${game.homeTeam.location}</td>
      <td>${game.homeScore}</td>
    </tr>
  </c:forEach></table>
</div>

<div style="clear: both">
  <h2>Tiebreaker</h2>
  <span>${week.tiebreaker}&nbsp;${week.tiebreakerAnswer}</span>
</div>
