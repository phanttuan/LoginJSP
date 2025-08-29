<%@ page contentType="text/html; charset=UTF-8" %>
<!DOCTYPE html>
<html>
<head>
    <title>Quên mật khẩu</title>
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css"/>
</head>
<body>
<div class="container" style="max-width:400px;margin:auto;margin-top:40px;">
    <h2 class="mb-4 text-center">Quên mật khẩu</h2>
    <form action="forgot-password" method="post">
        <div class="form-group">
            <label>Nhập email hoặc tên đăng nhập của bạn:</label>
            <input type="text" class="form-control" name="emailOrUsername" required>
        </div>
        <button type="submit" class="btn btn-primary btn-block">Gửi yêu cầu</button>
    </form>
    <% String alert = (String) request.getAttribute("alert");
       if (alert != null) { %>
        <div class="alert alert-info mt-3" role="alert">
            <%= alert %>
        </div>
    <% } %>
    <div class="mt-3 text-center">
        <a href="login">Quay lại đăng nhập</a>
    </div>
</div>
</body>
</html>