<html>
<body>
<h1> Book Server</h1>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<c:forEach items="${books}" var="item">
    Book Name:${item.name}<br>
    ISBN:${item.isbn}<br>
    Author:${item.author}<br>
    Price:${item.price}<br>
    <br>
</c:forEach>
</body>
</html>