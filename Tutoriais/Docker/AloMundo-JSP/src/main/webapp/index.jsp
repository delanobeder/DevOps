<%@page contentType="text/html"%>
<%@page pageEncoding="UTF-8"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page isELIgnored="false"%>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>JSP Page</title>
</head>

<body>

    <h4>Alô Mundo</h4>
    <jsp:useBean id="now" class="java.util.Date" />
    <h4>
        <fmt:formatDate value="${now}" dateStyle="full"/> &#149;
        <fmt:formatDate value="${now}" type="time"/>
    </h4>
</body>

</html>