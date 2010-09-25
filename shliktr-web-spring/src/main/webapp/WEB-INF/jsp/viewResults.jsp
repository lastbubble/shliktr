<?xml version="1.0" encoding="ISO-8859-1"?>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN"
    "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">

<head>
  <meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1" />
  <link rel="stylesheet" href="http://ajax.googleapis.com/ajax/libs/dojo/1.5/dijit/themes/tundra/tundra.css" />
  <link rel="stylesheet" type="text/css" href="../pool.css" />
  <title>NFL Pool - Results for week ${week.id}</title>
  <script src="http://ajax.googleapis.com/ajax/libs/dojo/1.5/dojo/dojo.xd.js" djConfig="parseOnLoad:true"></script>
  <script type="text/javascript">
  dojo.require("dijit.Dialog");
  dojo.require("dijit.form.Button");

  function showPicks(id, name) {
    var dlg = dijit.byId('picksDlg');
    dlg.attr('title', name + "'s Picks");
    dojo.xhrGet({
      url: '../app/getPickResults',
      content: {'weekId': ${week.id}, 'playerId': id},
      handleAs: 'text',
      load: function(data) {
        dojo.byId('pickResults').innerHTML = data;
        dlg.show();
      }
    });
  }
  </script>
</head>

<body class="tundra">

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
    <c:forEach items="${results}" var="result">
    <tr align="right">
      <td>
        <a href="#" onclick="showPicks(${result.player.id}, '${result.player.name}'); return false" title="View ${result.player.name}'s picks" style="text-decoration: none">
          ${result.player.name}
        </a>
      </td>
      <td>${result.points}</td>
      <td>${result.gamesWon}-${result.gamesLost}</td>
      <td>${result.tiebreaker}&nbsp;(${result.tiebreakerDiff})</td>
      <td>${result.pointsLost}</td>
      <td>${result.pointsRemaining}</td>
    </tr>
    </c:forEach>
  </table>

  <div style="display:${isAdmin ? 'block' : 'none'}; font-family: monospace; white-space: pre;">${resultOutput}</div>

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

  <div style="display:${isAdmin ? 'block' : 'none'}; font-family: monospace; white-space: pre;">${bankOutput}</div>

  <h2>Tiebreaker</h2>
  <p>${week.tiebreaker}</p>

  <p>Closest to actual tiebreaker result: ${week.tiebreakerAnswer}</p>
  <ul>
  <c:forEach items="${closest}" var="result">
    <li>${result.player.name}: ${result.tiebreaker}</li>  
  </c:forEach>
  </ul>

  <div dojoType="dijit.Dialog" id="picksDlg" title="Picks" style="display:none">
    <div id="pickResults" />
  </div>

</body>

</html>
