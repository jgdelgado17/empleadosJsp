<%@ page contentType="text/html" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-QWTKZyjpPEjISv5WaRU9OFeRpok6YctnYmDr5pNlyT2bRjXh0JMhjY6hW+ALEwIH" crossorigin="anonymous">
        <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.6.0/css/all.min.css" integrity="sha512-Kc323vGBEqzTmouAECnVceyQqyqdsSiqLQISBL29aUW4U/M7pSPA/gEUZQqv1cwx4OnYxTxve5UMg5GT6L4JJg==" crossorigin="anonymous" referrerpolicy="no-referrer" />
        <title>New employee</title>
    </head>
    <body>
        <div class="container">
            <div class="card">
                <div class="card-body">
                    <h4 class="card-title text-center fw-bold mt-3">
                                        <c:choose>
                                            <c:when test="${empty employee.firstName}">
                                                New employee
                                            </c:when>
                                            <c:otherwise>
                                                Edit employee
                                            </c:otherwise>
                                        </c:choose>
                                    </h4>
                    <hr>
                    <form
                        <c:choose>
                            <c:when test="${empty employee.firstName}">
                                action="EmployedController?action=save"
                            </c:when>
                            <c:otherwise>
                                action="EmployedController?action=update&id=${employee.id}"
                            </c:otherwise>
                        </c:choose>
                        method="POST"
                    >
                        <div class="input-group text-center">
                            <div class="mb-3 w-50 mx-auto">
                                <label for="firstName" class="form-label">First Name</label>
                                <input value="${employee.firstName}" type="text" class="form-control" id="firstName" name="firstName" required>
                            </div>
                            <div class="mb-3 w-50 mx-auto">
                                <label for="lastName" class="form-label">Last Name</label>
                                <input value="${employee.lastName}" type="text" class="form-control" id="lastName" name="lastName" required>
                            </div>
                        </div>

                        <div class="input-group text-center">
                            <div class="mb-3 w-50 mx-auto">
                                <label for="entryDate" class="form-label">Entry Date</label>
                                <input value="${employee.entryDate}" type="date" class="form-control" id="entryDate" name="entryDate" required>
                            </div>
                            <div class="mb-3 w-50 mx-auto">
                                <label for="salary" class="form-label">Salary</label>
                                <input value="${employee.salary}" type="number" class="form-control" id="salary" name="salary" required>
                            </div>
                        </div>
                        <div class="text-center">
                            <button type="submit" class="btn btn-primary"><i class="fa fa-save"></i>
                                <c:choose>
                                    <c:when test="${empty employee.firstName}">
                                        Save
                                    </c:when>
                                    <c:otherwise>
                                        Update
                                    </c:otherwise>
                                </c:choose>
                            </button>
                            <a href="EmployedController?action=list" class="btn btn-danger"><i class="fa fa-times"></i> Cancel</a>
                        </div>
                    </form>
                </div>
            </div>
        </div>
    </body>
</html>