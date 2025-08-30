<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Đăng Nhập</title>
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css"/>
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.7.0/css/font-awesome.min.css"/>
    <style>
    body {
        background: #f8f9fa;
    }
    .login-container {
        width: 400px;
        margin: 40px auto;
        background: #fff;
        padding: 30px;
        border-radius: 8px;
        box-shadow: 0 2px 8px rgba(0,0,0,.1);
    }
    .btn-primary { width: 100%; }
    </style>
</head>
<body>
<div class="login-container">
    <h2 class="mb-4 text-center">Đăng Nhập Vào Hệ Thống</h2>
    <form action="<c:url value='/login'/>" method="post">
        <c:if test="${alert !=null}">
            <h3 class="alert alert-danger">${alert}</h3>
        </c:if>
        <div class="form-group">
            <div class="input-group">
                <div class="input-group-prepend">
                  <span class="input-group-text"><i class="fa fa-user"></i></span>
                </div>
                <input type="text" class="form-control" placeholder="Tài khoản" name="username">
            </div>
        </div>
        <div class="form-group">
            <div class="input-group">
                <div class="input-group-prepend">
                  <span class="input-group-text"><i class="fa fa-lock"></i></span>
                </div>
                <input type="password" class="form-control" placeholder="Mật khẩu" name="password">
            </div>
        </div>
        <div class="form-group d-flex justify-content-between align-items-center">
            <div>
                <input type="checkbox" name="remember"> Nhớ tôi
            </div>
            <a href="<c:url value='/forgot-password'/>">Quên mật khẩu?</a>
        </div>
        <button type="submit" class="btn btn-primary">Đăng nhập</button>
        <div class="mt-3 text-center">
            Nếu bạn chưa có tài khoản, hãy <a href="<c:url value='/register'/>">Đăng ký</a>
        </div>
    </form>
</div>
</body>
</html>