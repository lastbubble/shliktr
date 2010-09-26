<?xml version="1.0" encoding="ISO-8859-1"?>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN"
    "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">

<head>
  <meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1" />
  <link rel="stylesheet" href="http://ajax.googleapis.com/ajax/libs/dojo/1.5/dijit/themes/tundra/tundra.css" />
  <link rel="stylesheet" type="text/css" href="../pool.css" />
  <title>NFL Pool - Pick statistics for week ${week.id}</title>
  <script src="http://ajax.googleapis.com/ajax/libs/dojo/1.5/dojo/dojo.xd.js" djConfig="parseOnLoad:true"></script>
  <script type="text/javascript">
  dojo.require("dijit.Tooltip");
  function showPlayers(team,elem) {
    var tipId = 'tip-'+elem.id;
    if (dijit.byId(tipId)) { return }
    dojo.xhrGet({
      url: '../app/viewRankings',
      content: {'weekId': ${week.id}, 'team': team},
      handleAs: 'text',
      load: function(data) {
        new dijit.Tooltip({id:tipId, label:data, connectId:[elem.id]}).open(elem);
      }
    });
  }
  </script>
</head>

<body class="tundra">

  <h1>Week ${week.id} Pick Statistics</h1>

  <table>
    <tr>
      <th align="left">Team</th>
      <th># Picks</th>
      <th>Total</th>
      <th>Rankings</th>
    </tr>
    <c:forEach items="${statsList}" var="stats">
    <tr>
      <td><img class="teamlogo" src="../images/${stats.team.abbr}.gif" /> ${stats.team.location}</td>
      <td align="right">
        <a id="team-${stats.team.abbr}"
           href="#"
           onclick="return false"
           onmouseover="showPlayers('${stats.team.abbr}',this); return false">${fn:length(stats.rankings)}</a>
      </td>
      <td align="right">${stats.total}</td>
      <td>${stats.rankings}</td>
    </tr>
    </c:forEach>
  </table>

</body>

</html>
