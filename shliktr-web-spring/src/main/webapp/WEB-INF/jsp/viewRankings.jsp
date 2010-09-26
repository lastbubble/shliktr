<?xml version="1.0" encoding="ISO-8859-1"?>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<table>
  <tr><th><img class="teamlogo" src="../images/${team}.gif" /></th><th align="left">Player</th><th>Ranking</th></tr>
  <c:forEach items="${rankings}" var="ranking">
  <tr><td /><td>${ranking.key.name}</td><td align="right">${ranking.value}</td></tr>
  </c:forEach>
</table>
