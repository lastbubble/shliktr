<?xml version="1.0" encoding="ISO-8859-1"?>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="spring-form" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN"
    "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">

<head>
  <meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1" />
  <link rel="stylesheet" type="text/css" href="../pool.css" />
  <title>NFL Pool - Editing week ${week.id}</title>
</head>

<body>

  <h1>Editing week ${week.id}</h1>

  <%@ include file="/WEB-INF/jsp/welcome.jsp" %>

  <spring-form:form commandName="week" method="post">
  <input type="hidden" name="weekId" value="${week.id}" />

  <div>
    <table>
    <c:forEach items="${week.games}" var="game" varStatus="i">
    <tr>
      <td><img class="teamlogo" src="../images/${game.awayTeam.abbr}.gif" /></td>
      <td>${game.awayTeam.location}</td>
      <td>
        <spring-form:input path="games[${i.index}].awayScore" />
      </td>
      <td><img class="teamlogo" src="../images/${game.homeTeam.abbr}.gif" /></td>
      <td>${game.homeTeam.location}</td>
      <td>
        <spring-form:input path="games[${i.index}].homeScore" />
      </td>
    </tr>
    </c:forEach>
    </table>
  </div>

  <div>
    <h2>Tiebreaker</h2>
    <span>
      <spring-form:textarea path="tiebreaker" rows="5" />
      &nbsp;
      <spring-form:input path="tiebreakerAnswer" />
    </span>
  </div>

  <input type="submit" value="Save" />
  </spring-form:form>

</body>

</html>
