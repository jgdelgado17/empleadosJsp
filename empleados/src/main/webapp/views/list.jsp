<%@ page contentType="text/html" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Employees</title>
    </head>
    <body>
        <h1>List of employees</h1>
        <table>
            <thead>
                <tr>
                    <th>Id</th>
                    <th>First Name</th>
                    <th>Last Name</th>
                    <th>Entry Date</th>
                    <th>Salary</th>
                    <th>Actions</th>
                </tr>
            </thead>
            <tbody>
                <c:forEach items="${employees}" var="employed">
                    <tr>
                        <td>${employed.id}</td>
                        <td>${employed.firstName}</td>
                        <td>${employed.lastName}</td>
                        <td>${employed.entryDate}</td>
                        <td>${employed.salary}</td>
                        <td>
                            <a href="EmployedController?action=edit&id=${employed.id}">Edit</a>
                            <a href="EmployedController?action=delete&id=${employed.id}">Delete</a>
                        </td>
                    </tr>
                </c:forEach>
            </tbody>
        </table>
        <a href="EmployedController?action=new">Add</a>
        <a href="index.jsp">Home</a>
    </body>
</html>
