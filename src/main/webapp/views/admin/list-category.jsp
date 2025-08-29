<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
	<c:forEach items="${cateList}" var="cate" varStatus="STT">
		<tr class="odd gradeX">
			<td>${STT.index+1 }</td>
			<c:url value="/image?fname=${cate.icon }" var="imgUrl"></c:url>
			<td><img height="150" width="200" src="${imgUrl}" /></td>
			<td>${cate.name }</td>
			<td><a
				href="<c:url value='/admin/category/edit?id=${cate.id }'/>"
				class="center">Sửa</a> | <a
				href="<c:url value='/admin/category/delete?id=${cate.id }'/>"
				class="center">Xóa</a></td>
		</tr>
	</c:forEach>
</body>
</html>