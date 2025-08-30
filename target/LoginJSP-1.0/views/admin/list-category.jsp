<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Danh sách danh mục</title>
<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css">
</head>
<body>
<div class="container mt-4">
    <h2>Danh sách danh mục</h2>
    <a href="<c:url value='/admin/category/add'/>" class="btn btn-primary mb-3">Thêm mới</a>
    <table class="table table-bordered">
        <thead>
            <tr>
                <th>STT</th>
                <th>Ảnh</th>
                <th>Tên danh mục</th>
                <th>Hành động</th>
            </tr>
        </thead>
        <tbody>
            <c:forEach items="${cateList}" var="cate" varStatus="STT">
                <tr class="odd gradeX">
                    <td>${STT.index+1}</td>
                    <c:url value="/image?fname=${cate.icon}" var="imgUrl"></c:url>
                    <td><img height="150" width="200" src="${imgUrl}" /></td>
                    <td>${cate.catename}</td>
                    <td>
                        <a href="<c:url value='/admin/category/edit?id=${cate.cateid}'/>" class="btn btn-warning">Sửa</a> 
                        <a href="<c:url value='/admin/category/delete?id=${cate.cateid}'/>" class="btn btn-danger">Xóa</a>
                    </td>
                </tr>
            </c:forEach>
        </tbody>
    </table>
</div>
</body>
</html>