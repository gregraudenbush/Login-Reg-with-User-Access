<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Admin Page</title>
</head>
<style>
table {
    font-family: arial, sans-serif;
    border-collapse: collapse;
    width: 100%;
}

td, th {
    border: 1px solid #dddddd;
    text-align: left;
    padding: 8px;
}

tr:nth-child(even) {
    background-color: #dddddd;
}
</style>

<body>
    <form method="POST" id="logoutForm" action="/logout">
        <input type="hidden" name="${_csrf.parameterName }" value="${_csrf.token }"/>
        <input type="submit" value="Logout!"/>
    </form>

    <h1>Welcome <c:out value="${currentUser.firstName}!"></c:out></h1>
    <table>
            <tr>
                <th>Name</th>
                <th>Email</th>
                <th>Action</th>
            </tr>
    <c:forEach items="${allusers}" var="user">
        <tr>
        <td><c:out value="${user.firstName} ${user.lastName}"/></td>
        <td><c:out value="${user.username}"/></td>
        <td><c:if test="${roleadmin.toString() ==  user.roles.toString()}">
                admin
        </c:if>
        <c:if test="${roleadmin.toString() !=  user.roles.toString()}">
                <a href="/makeadmin/${ user.id }">Make admin</a>
        <a href="/delete/${ user.id }">Delete</a>
        </c:if>
        </td>
        <td>
        </td>
        </tr>
    </c:forEach>

    </table>

</body>
</html>