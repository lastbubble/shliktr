<?xml version="1.0" encoding="ISO-8859-1"?>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN"
    "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">

<head>
  <meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1" />
  <link rel="stylesheet" type="text/css" href="../pool.css" />
  <title>NFL Pool - Week ${week.id}</title>
</head>

<body>

  <h1>Games for week ${week.id}</h1>

  <%@ include file="/WEB-INF/jsp/welcome.jsp" %>

  <div style="float:left">
    <c:choose>
      <c:when test="${isAdmin}">
        <span>
          <a href="editWeek?weekId=${week.id}">Edit week</a>
        </span>
        <br />
        <span>
          <a href="addPicks?weekId=${week.id}">Add picks</a>
        </span>
        <form action="viewPicks" method="get">
          <input type="hidden" name="weekId" value="${week.id}" />
          <select name="playerId">
            <c:forEach items="${players}" var="player">
              <option value="${player.id}">${player.name}'s picks for week ${week.id}</option>
            </c:forEach>
          </select>
          <input type="submit" value="View Picks" />
        </form>
      </c:when>
      <c:otherwise>
        <span><a href="editPicks?weekId=${week.id}">
          <c:choose>
            <c:when test="${hasPicks}">Edit picks</c:when>
            <c:otherwise>Make picks</c:otherwise>
          </c:choose></a>
        </span>
      </c:otherwise>
    </c:choose>
  </div>

  <div style="float: right">
    View another week:
    <form action="" method="get">
      <select name="weekId">
        <c:forEach items="${weekIds}" var="weekId">
          <option value="${weekId}">Week ${weekId}</option>
        </c:forEach>
      </select>
      <input type="submit" value="View" />
    </form>
    <div>
      <a href="viewResults?weekId=${week.id}">View Week ${week.id} Results</a>
      <c:if test="${player != null}">
        <br />
        <a href="predictResults?weekId=${week.id}">Predict Results</a>
      </c:if>
    </div>
  </div>

  <%@ include file="/WEB-INF/jsp/week.jsp" %>

</body>

</html>
