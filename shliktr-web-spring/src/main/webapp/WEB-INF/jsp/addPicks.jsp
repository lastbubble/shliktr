<?xml version="1.0" encoding="ISO-8859-1"?>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="spring-form" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN"
    "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">

<head>
  <meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1" />
  <link rel="stylesheet" type="text/css" href="../pool.css" />
  <title>NFL Pool - Add picks for ${week.id}</title>
</head>

<body>

  <h1>Adding picks for week ${week.id}</h1>

  <c:if test="${errorMsg != null}">
    <div>
      <span class="error">${errorMsg}</span>
    </div>
  </c:if>

  <spring-form:form commandName="newPicks" method="post">
  <spring-form:select path="playerId">
    <c:forEach items="${players}" var="player">
      <spring-form:option value="${player.id}">
        ${player.name}
      </spring-form:option>
    </c:forEach>
  </spring-form:select>

  <table>
  <c:forEach items="${newPicks.picks}" var="pick" varStatus="i">
  <tr>
	<td><img src="../images/${pick.game.awayTeam.abbr}.gif" /></td>
	<td>
      <spring-form:radiobutton path="picks[${i.index}].winner" value="away" />
	  ${pick.game.awayTeam.location}
	</td>
	<td><img src="../images/${pick.game.homeTeam.abbr}.gif" /></td>
	<td>
      <spring-form:radiobutton path="picks[${i.index}].winner" value="home" />
	  ${pick.game.homeTeam.location}</td>
	<td>
	  <spring-form:input path="picks[${i.index}].ranking" />
	</td>
  </tr>
  </c:forEach>
  </table>

  <h2>Tiebreaker</h2>
  <p>${week.tiebreaker}
    <spring-form:textarea path="tiebreaker" />
  </p>
  <input type="submit" value="Save" />

  </spring-form:form>

</body>

</html>
