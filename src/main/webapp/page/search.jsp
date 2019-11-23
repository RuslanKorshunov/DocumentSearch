<%@ page contentType="text/html;charset=UTF-8" isELIgnored="false" %>
<%@ taglib prefix="ctg" uri="CustomTags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>Search Page</title>
</head>
<body>
<form action="DocumentSearch" method="get">
    <input type="text" name="question">
    <input type="submit" name="button" value="Search">
    <c:if test="${documents!=null}">
        <ctg:docTag documents="${documents}"></ctg:docTag>
    </c:if>
</form>
</body>
</html>