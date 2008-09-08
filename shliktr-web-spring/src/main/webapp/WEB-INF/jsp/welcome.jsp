<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<c:choose>
  <c:when test="${player != null}">
    <div style="float:right">
      Welcome, ${player.name}!
      <br />
      <a href="logout">Logout</a>
    </div>
  </c:when>
  <c:otherwise>
    <div style="float:right"><a href="login">Login</a></div>
  </c:otherwise>
</c:choose>
