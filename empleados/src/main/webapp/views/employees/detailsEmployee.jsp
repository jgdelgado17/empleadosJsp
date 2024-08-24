<%@ page contentType="text/html" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-QWTKZyjpPEjISv5WaRU9OFeRpok6YctnYmDr5pNlyT2bRjXh0JMhjY6hW+ALEwIH" crossorigin="anonymous">
        <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.6.0/css/all.min.css" integrity="sha512-Kc323vGBEqzTmouAECnVceyQqyqdsSiqLQISBL29aUW4U/M7pSPA/gEUZQqv1cwx4OnYxTxve5UMg5GT6L4JJg==" crossorigin="anonymous" referrerpolicy="no-referrer" />
        <title>Details employee</title>
    </head>
    <body>
        <div class="container">
            <div class="row">
                <div class="col-12">
                    <h1 class="text-center">Details employee</h1>
                </div>
            </div>
            <div class="row">
                <div class="col-12">
                    <table class="table table-striped">
                        <tr>
                            <td>ID</td>
                            <td><c:out value="${employee.id}"/></td>
                        </tr>
                        <tr>
                            <td>First Name</td>
                            <td><c:out value="${employee.firstName}"/></td>
                        </tr>
                        <tr>
                            <td>Last Name</td>
                            <td><c:out value="${employee.lastName}"/></td>
                        </tr>
                        <tr>
                            <td>Entry Date</td>
                            <td><c:out value="${employee.entryDate}"/></td>
                        </tr>
                        <tr>
                            <td>Salary</td>
                            <td><c:out value="${employee.salary}"/></td>
                        </tr>
                    </table>
                    <div class="text-center">
                        <button type="button" class="btn btn-dark btn-sm mt-2" onclick="history.back();">
                            <i class="fa fa-arrow-left"></i> Back
                        </button>
                    </div>
                </div>
            </div>
        </div>
    </body>
</html>