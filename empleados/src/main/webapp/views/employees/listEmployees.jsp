<%@ page contentType="text/html" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-QWTKZyjpPEjISv5WaRU9OFeRpok6YctnYmDr5pNlyT2bRjXh0JMhjY6hW+ALEwIH" crossorigin="anonymous">
        <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.6.0/css/all.min.css" integrity="sha512-Kc323vGBEqzTmouAECnVceyQqyqdsSiqLQISBL29aUW4U/M7pSPA/gEUZQqv1cwx4OnYxTxve5UMg5GT6L4JJg==" crossorigin="anonymous" referrerpolicy="no-referrer" />
        <title>Employees</title>
    </head>
    <body>
        <div class="container mt-3">
            <div class="card">
                <div class="card-body">
                    <h4 class="card-title text-center fw-bold mt-3">List of employees</h4>
                    <hr>
                        <a href="EmployedController?action=create" class="btn btn-primary"><i class="fa fa-plus"></i> New employee</a>
                    <hr>
                    <table class="table table-striped table-hover table-bordered mt-3" >
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
                                        <a href="EmployedController?action=details&id=${employed.id}" class="btn btn-info"><i class="fa fa-eye"></i> Details</a>
                                        <a href="EmployedController?action=edit&id=${employed.id}" class="btn btn-success"><i class="fa fa-pencil"></i> Edit</a>
                                        <a href="EmployedController?action=delete&id=${employed.id}" class="btn btn-danger" onclick="return confirm('Are you sure you want to delete this employee?')"><i class="fa fa-trash"></i> Delete</a>
                                    </td>
                                </tr>
                            </c:forEach>
                            <c:if test="${employees.isEmpty()}">
                                <tr>
                                    <td class="text-center text-danger fw-bold" colspan="6">No employees</td>
                                </tr>
                            </c:if>
                        </tbody>
                    </table>
                </div>
            </div>
        </div>
    </body>
</html>
