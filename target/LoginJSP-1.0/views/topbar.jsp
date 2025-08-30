<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="jakarta.tags.core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Thanh điều hướng</title>
</head>
<body>
	<c:choose>
		<c:when test="${sessionScope.account == null}">
			<div class="col-sm-6">
				<ul class="list-inline right-topbar pull-right">
					<li><a href="<%=request.getContextPath()%>/login">Đăng
							nhập</a> | <a href="<%=request.getContextPath()%>/register">Đăng ký</a></li>
					<li><i class="search fa fa-search search-button"></i></li>
				</ul>
			</div>
		</c:when>
		<c:otherwise>
			<div class="col-sm-6">
				<ul class="list-inline right-topbar pull-right">
					<li><a
						href="<%=request.getContextPath()%>/member/myaccount">${sessionScope.account.fullName}</a>
						| <a href="<%=request.getContextPath()%>/logout">Đăng Xuất</a></li>
					<li><i class="search fa fa-search search-button"></i></li>
				</ul>
			</div>
		</c:otherwise>
	</c:choose>
</body>
</html>