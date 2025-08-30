<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Đặt lại mật khẩu</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
</head>
<body>
    <div class="container mt-5">
        <div class="row justify-content-center">
            <div class="col-md-6">
                <div class="card">
                    <div class="card-header">
                        <h3 class="text-center">Đặt lại mật khẩu</h3>
                    </div>
                    <div class="card-body">
                        <% if(request.getAttribute("alert") != null) { %>
                            <div class="alert alert-warning"><%= request.getAttribute("alert") %></div>
                        <% } %>
                        <form action="<c:url value='/reset-password'/>" method="post">
                            <!-- Prefer token from request scope (forward), fallback to query param -->
                            <input type="hidden" name="token" value="${not empty requestScope.token ? requestScope.token : param.token}" />
                            <div class="mb-3">
                                <label for="password" class="form-label">Mật khẩu mới:</label>
                                <input type="password" class="form-control" id="password" name="password" required>
                            </div>
                            <div class="mb-3">
                                <label for="confirmPassword" class="form-label">Xác nhận mật khẩu mới:</label>
                                <input type="password" class="form-control" id="confirmPassword" name="confirmPassword" required>
                            </div>
                            <div class="d-grid">
                                <button type="submit" class="btn btn-primary">Đặt lại mật khẩu</button>
                            </div>
                        </form>
                    </div>
                </div>
            </div>
        </div>
    </div>
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>