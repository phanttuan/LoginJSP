<%@ page contentType="text/html; charset=UTF-8" %>
<%@ page session="true" %>
<%@ page import="model.User" %>
<!DOCTYPE html>
<html>
<head>
    <title>Chào mừng</title>
</head>
<body>
    <%
        User user = (User) session.getAttribute("account");
        String username = (user != null) ? user.getUserName() : "Khách";
    %>
    <h2>Chào mừng <%= username %>!</h2>
    <p>Chúc bạn một ngày tốt lành.</p>
</body>
</html>