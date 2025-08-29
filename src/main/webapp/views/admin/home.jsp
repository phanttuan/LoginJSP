<%@ page contentType="text/html; charset=UTF-8" %>
<%@ page session="true" %>
<%@ page import="model.User" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Chào mừng</title>
</head>
<body>
    <%
        User user = (User) session.getAttribute("account");
        String username = (user != null) ? user.getUserName() : "Khách";
    %>
    <div class="user-info">
        Xin chào, <%= username %>
        <% if (user != null) { %>
            <!-- Nút đăng xuất -->
            <form action="<%=request.getContextPath()%>/logout" method="get" style="display:inline;">
                <button type="submit" style="color: white; background: #d9534f; padding: 6px 12px; border-radius: 4px; border:none; margin-left: 10px; cursor:pointer;">
                    Đăng xuất
                </button>
            </form>
            <!-- Nút chuyển qua trang Category -->
            <a href="<%=request.getContextPath()%>/admin/category/list">
                <button type="button" style="color: white; background: #5cb85c; padding: 6px 12px; border-radius: 4px; border:none; margin-left: 10px; cursor:pointer;">
                    Quản lý Category
                </button>
            </a>
        <% } %>
    </div>
    <h2>Chào mừng <%= username %>!</h2>
    <p>Chúc bạn một ngày tốt lành.</p>
    
    <!-- Hiển thị thời gian hiện tại (UTC) -->
    <div style="margin-top: 20px;">
        <p>Thời gian hiện tại (UTC): 2025-08-29 03:23:20</p>
        <p>Người dùng hiện tại: phanttuan</p>
    </div>
</body>
</html>