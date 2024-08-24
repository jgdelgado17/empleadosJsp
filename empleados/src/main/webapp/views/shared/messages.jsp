<%@ page contentType="text/html" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<c:if test="${sessionScope.error != null}">
    <div id="error-message" class="alert alert-danger mt-3 text-center fw-bold" role="alert">
        ${sessionScope.error}
    </div>
    <c:remove var="error" scope="session"/>
</c:if>

<c:if test="${sessionScope.success != null}">
    <div id="success-message" class="alert alert-success mt-3 text-center fw-bold" role="alert">
        ${sessionScope.success}
    </div>
    <c:remove var="success" scope="session"/>
</c:if>

<script>
    setTimeout(function() {
        var errorMessage = document.getElementById('error-message');
        var successMessage = document.getElementById('success-message');

        if (errorMessage) {
            errorMessage.style.transition = "opacity 0.5s ease-out";
            errorMessage.style.opacity = '0';
            setTimeout(function() {
                errorMessage.style.display = 'none';
                sessionStorage.removeItem('error');
            }, 500);
        }

        if (successMessage) {
            successMessage.style.transition = "opacity 0.5s ease-out";
            successMessage.style.opacity = '0';
            setTimeout(function() {
                successMessage.style.display = 'none';
                sessionStorage.removeItem('success');
            }, 500);
        }
    }, 5000);
</script>