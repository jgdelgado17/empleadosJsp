<%@ page contentType="text/html" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<%
      final String ACTION = (String) application.getAttribute("request.param.action");
      final String PAGE = (String) application.getAttribute("request.param.page");
      final String CREATE = (String) application.getAttribute("action.create");
      final String LIST = (String) application.getAttribute("action.list");
      final String DETAILS = (String) application.getAttribute("action.details");
      final String SEARCH = (String) application.getAttribute("action.search");
      final String EDIT = (String) application.getAttribute("action.edit");
      final String DELETE = (String) application.getAttribute("action.delete");
%>

<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-QWTKZyjpPEjISv5WaRU9OFeRpok6YctnYmDr5pNlyT2bRjXh0JMhjY6hW+ALEwIH" crossorigin="anonymous">
        <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.6.0/css/all.min.css" integrity="sha512-Kc323vGBEqzTmouAECnVceyQqyqdsSiqLQISBL29aUW4U/M7pSPA/gEUZQqv1cwx4OnYxTxve5UMg5GT6L4JJg==" crossorigin="anonymous" referrerpolicy="no-referrer" />
        <script src="${pageContext.request.contextPath}/js/scripts.js"></script>
        <script type="text/javascript">
            const ACTION = "<%= ACTION %>";
            const SEARCH = "<%= SEARCH %>";
            const DELETE = "<%= DELETE %>";
        </script>
        <title>Employees</title>
    </head>
    <body>
        <div class="container mt-3">
            <div class="card">
                <div class="card-body">
                    <h4 class="card-title text-center fw-bold mt-3">List of employees</h4>
                    <div class="d-flex align-items-center">
                        <div class="input-group">
                            <input type="hidden" name="action" value="${SEARCH}">
                            <input type="text" id="searchByName" class="form-control-sm w-50 border-primary" placeholder="Type here to search by name or last name"
                                name="searchByName" aria-label="Search by name or last name" aria-describedby="button-addon1" maxlength="50"
                                value="${searchQuery != null ? searchQuery : ''}">
                            <a href="EmployedController?<%= ACTION %>=<%= LIST %>" class="btn btn-outline-secondary"><i class="fa fa-times"></i></a>
                        </div>

                        <div class="d-flex justify-content-end mb-3 ms-5">
                            <a href="EmployedController?<%= ACTION %>=<%= CREATE %>" class="btn btn-info btn-sm"><i class="fa fa-plus-circle "></i> New</a>
                        </div>
                    </div>

                    <jsp:include page="../shared/messages.jsp" />
                    <jsp:include page="../shared/modalConfirmation.jsp" />

                    <table class="table table-striped table-hover table-bordered mt-3" id="employeesTable" >
                        <thead class="table-dark text-center">
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
                                    <td class="text-center">
                                        <a href="EmployedController?<%= ACTION %>=<%= DETAILS %>&id=${employed.id}" class="btn btn-info btn-sm"><i class="fa fa-eye"></i> Details</a>
                                        <a href="EmployedController?<%= ACTION %>=<%= EDIT %>&id=${employed.id}" class="btn btn-success btn-sm"><i class="fa fa-pencil"></i> Edit</a>
                                        <a href="#" class="btn btn-danger btn-sm" data-bs-toggle="modal" data-bs-target="#modalConfirm" data-id="${employed.id}" data-name="${employed.firstName} ${employed.lastName} with id ${employed.id}"><i class="fa fa-trash"></i> Delete</a>
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

                    <!-- Paginación -->
                    <div class="d-flex justify-content-center">
                        <nav aria-label="Page navigation">
                            <ul class="pagination">
                                <c:if test="${currentPage > 1}">
                                    <li class="page-item">
                                        <a class="page-link" href="EmployedController?<%= ACTION %>=<%= LIST %>&<%= PAGE %>=${currentPage - 1}">
                                            <i class="fa fa-backward"></i> Previous
                                        </a>
                                    </li>
                                </c:if>
                                <c:forEach begin="1" end="${totalPages}" var="i">
                                    <li class="page-item ${i == currentPage ? 'active' : ''}">
                                        <a class="page-link" href="EmployedController?<%= ACTION %>=<%= LIST %>&<%= PAGE %>=${i}">${i}</a>
                                    </li>
                                </c:forEach>
                                <c:if test="${currentPage < totalPages}">
                                    <li class="page-item">
                                        <a class="page-link" href="EmployedController?<%= ACTION %>=<%= LIST %>&<%= PAGE %>=${currentPage + 1}">
                                            Next <i class="fa fa-forward"></i>
                                        </a>
                                    </li>
                                </c:if>
                            </ul>
                        </nav>
                    </div>
                </div>
            </div>
        </div>
    </body>
</html>
