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
  <title>NFL Pool - Head-To-Head Results</title>
  <script src="http://ajax.googleapis.com/ajax/libs/dojo/1.5/dojo/dojo.xd.js" djConfig="parseOnLoad:true"></script>
  <script type="text/javascript">
  dojo.require("dijit.Dialog");
  dojo.require("dijit.form.Button");
  dojo.require("dijit.form.CheckBox");
  dojo.require("dijit.form.Form");
  </script>
</head>

<body class="tundra">

  <h1>Head-to-Head Results</h1>

  <%@ include file="/WEB-INF/jsp/welcome.jsp" %>

  <div dojoType="dijit.form.DropDownButton">
    <span>Choose Players</span>
    <div dojoType="dijit.TooltipDialog" style="display:none">
      <form dojoType="dijit.form.Form">
        <c:forEach items="${allPlayers}" var="player">
        <div>
          <input type="checkbox" dojoType="dijit.form.CheckBox" id="select${player.id}" name="playerId" value="${player.id}" <c:if test="${fn:contains(playerNames, player.name)}">checked="checked"</c:if>>
          <label for="select${player.id}">${player.name}</label>
        </div>
        </c:forEach>
        <button dojoType="dijit.form.Button" type="submit">OK</button>
      </form>
    </div>
  </div>

  <c:if test="${results != null}">
    <table>
      <tr>
        <th>Week</th><th>Winner</th><th>Player</th><th>Score</th>
      </tr>
      <c:forEach items="${results}" var="result">
        <c:if test="${fn:length(result) > 0}">
        <tr valign="top">
          <td rowspan="${fn:length(result)}">${result[0].week}</td>
          <td rowspan="${fn:length(result)}">
              <span style="font-weight:bold">${result[0].player.name}</span>
          </td>
          <td>${result[0].player.name}</td>
          <td align="right">${result[0].points}</td>
          <c:forEach items="${result}" var="result2" begin="1">
          </tr>
          <tr>
            <td>${result2.player.name}</td>
            <td align="right">${result2.points}</td>
          </c:forEach>
        </tr>
        </c:if>
      </c:forEach>
    </table>
  </c:if>

</body>

</html>
