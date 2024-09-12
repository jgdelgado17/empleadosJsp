<%@ page contentType="text/html" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<%
     final String SAVE = (String) application.getAttribute("action.save");
     final String UPDATE = (String) application.getAttribute("action.update");
     final String ACTION = (String) application.getAttribute("request.param.action");
     final String PAGE = (String) application.getAttribute("request.param.page");
%>

<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-QWTKZyjpPEjISv5WaRU9OFeRpok6YctnYmDr5pNlyT2bRjXh0JMhjY6hW+ALEwIH" crossorigin="anonymous">
        <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.6.0/css/all.min.css" integrity="sha512-Kc323vGBEqzTmouAECnVceyQqyqdsSiqLQISBL29aUW4U/M7pSPA/gEUZQqv1cwx4OnYxTxve5UMg5GT6L4JJg==" crossorigin="anonymous" referrerpolicy="no-referrer" />
        <title>New employee</title>
    </head>
    <body>
        <jsp:include page="../shared/messages.jsp" />
        <div class="container">
            <div class="card">
                <div class="card-body">
                    <h4 class="card-title text-center fw-bold mt-3">
                        ${employee.id == null ? 'New' : 'Edit'} employee
                    </h4>
                    <hr>
                    <form
                        <c:choose>
                            <c:when test="${employee.id == null}">
                                action="EmployedController?<%= ACTION %>=<%= SAVE %>"
                            </c:when>
                            <c:otherwise>
                                action="EmployedController?<%= ACTION %>=<%= UPDATE %>&id=${employee.id}"
                            </c:otherwise>
                        </c:choose>
                        method="POST"
                    >
                        <div class="input-group text-center">
                            <div class="mb-3 w-50 mx-auto">
                                <label for="firstName" class="form-label fw-bold">First Name</label>
                                <div class="input-group">
                                    <input value="${employee.firstName}" type="text" class="form-control border-primary" id="firstName" name="firstName" required>
                                    <button type="button" class="btn btn-outline-secondary" onclick="document.getElementById('firstName').value='';" title="Clear first name">
                                        <i class="fa fa-times"></i>
                                    </button>
                                </div>
                            </div>
                            <div class="mb-3 w-50 mx-auto">
                                <label for="lastName" class="form-label fw-bold">Last Name</label>
                                <div class="input-group">
                                    <input value="${employee.lastName}" type="text" class="form-control border-primary" id="lastName" name="lastName" required>
                                    <button type="button" class="btn btn-outline-secondary" onclick="document.getElementById('lastName').value='';" title="Clear last name">
                                        <i class="fa fa-times"></i>
                                    </button>
                                </div>
                            </div>
                        </div>

                        <div class="input-group text-center">
                            <div class="mb-3 w-50 mx-auto">
                                <label for="entryDate" class="form-label fw-bold">Entry Date</label>
                                <div class="input-group">
                                    <input value="${employee.entryDate}" type="date" class="form-control border-primary" id="entryDate" name="entryDate" required>
                                    <button type="button" class="btn btn-outline-secondary" onclick="document.getElementById('entryDate').value='';" title="Clear entry date">
                                        <i class="fa fa-times"></i>
                                    </button>
                                </div>
                            </div>
                            <div class="mb-3 w-50 mx-auto">
                                <label for="salary" class="form-label fw-bold">Salary</label>
                                <div class="input-group">
                                    <input value="${employee.salary}" type="number" class="form-control border-primary" id="salary" name="salary" required>
                                    <button type="button" class="btn btn-outline-secondary" onclick="document.getElementById('salary').value='';" title="Clear salary">
                                        <i class="fa fa-times"></i>
                                    </button>
                                </div>
                            </div>
                        </div>

                        <div class="text-center">
                            <button type="submit" class="btn btn-primary" title="${employee.id == null ? 'Save employee' : 'Update employee'}"><i class="fa fa-save"></i>
                                ${employee.id == null ? 'Save' : 'Update'}
                            </button>
                            <button type="button" class="btn btn-danger" onclick="history.back();" title="${employee.id == null ? 'Cancel new employee' : 'Cancel edit employee'}"><i class="fa fa-times"></i> Cancel</button>
                        </div>
                    </form>
                </div>
            </div>
        </div>
    </body>
</html>