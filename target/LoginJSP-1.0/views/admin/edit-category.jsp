<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Chỉnh sửa danh mục</title>
<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css">
</head>
<body>
<div class="container mt-4">
	<h2>Chỉnh sửa danh mục</h2>
	<c:url value="/admin/category/edit" var="edit"></c:url>
	<form role="form" action="${edit}" method="post" enctype="multipart/form-data">
		<input name="id" value="${category.cateid}" hidden="">
		<div class="form-group">
			<label>Tên danh mục:</label> 
			<input type="text" class="form-control" value="${category.catename}" name="name" />
		</div>
		<div class="form-group">
			<c:url value="/image?fname=${category.icon}" var="imgUrl"></c:url>
			<img class="img-responsive" width="100px" src="${imgUrl}" alt="">
			<label>Ảnh đại diện</label> 
			<input type="file" name="icon" class="form-control-file" />
		</div>
		<button type="submit" class="btn btn-primary">Lưu</button>
		<button type="reset" class="btn btn-secondary">Đặt lại</button>
	</form>
</div>
</body>
</html>