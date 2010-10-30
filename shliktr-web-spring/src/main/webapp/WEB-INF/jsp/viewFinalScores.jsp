<?xml version="1.0" encoding="ISO-8859-1"?>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN"
    "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">

<head>
  <meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1" />
  <link rel="stylesheet" href="http://ajax.googleapis.com/ajax/libs/dojo/1.5/dijit/themes/tundra/tundra.css" />
  <link rel="stylesheet" type="text/css" href="../pool.css" />
  <link rel="stylesheet" href="http://ajax.googleapis.com/ajax/libs/dojo/1.5/dojox/grid/resources/Grid.css" />
  <link rel="stylesheet" href="http://ajax.googleapis.com/ajax/libs/dojo/1.5/dojox/grid/resources/tundraGrid.css" />
  <style>
    .numberCell { text-align: right; font-size: 0.75em; }
  </style>
  <title>NFL Pool - Final Scores</title>
</head>

<body class="tundra">

  <h1>Final Scores</h1>

  <%@ include file="/WEB-INF/jsp/welcome.jsp" %>

  <div style="clear: both;" />

  <div id="grid" style="width: 100%;"></div>

  <h1>Accounts</h1>

  <table cellpadding="3">
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

<script type="text/javascript"
        src="http://ajax.googleapis.com/ajax/libs/dojo/1.5/dojo/dojo.xd.js"
        djConfig="parseOnLoad: true"></script>
<script type="text/javascript">
dojo.require("dojo.data.ItemFileReadStore");
dojo.require("dojox.grid.DataGrid");

dojo.addOnLoad(function(){
  var store = new dojo.data.ItemFileReadStore({
    url: 'finalScores.json'
  });
  var weeks = new Array();
  for( var i = 0; i < 17; i++ ) {
    weeks.push({field: 'week'+i, name: ' ', width: '50px', classes: 'numberCell'});
  }
  console.log(weeks);
  var grid = new dojox.grid.DataGrid({
      structure: [
        {
          noscroll: true,
          cells: [
            {field: 'player', name: 'Player', width: '75px'},
            {field:  'score', name:  'Score', width: '50px', classes: 'numberCell'},
            {field:  'total', name:  'Total', width: '50px', classes: 'numberCell'}
          ]
        },
        weeks
      ],
      store: store,
      clientSort: true
    },
    dojo.byId('grid')
  );
  grid.startup();
});
</script>

</html>
