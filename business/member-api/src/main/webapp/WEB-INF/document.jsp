<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%> 
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>API 更新文档</title>
</head>
<body>
	<table cellpadding="1" cellspacing="1" border="1" style="width:100%;text-align:center;line-height:30px;">
		<thead>
			<tr>
				<th>审核日期</th>
				<th>审核人</th>
				<th>接口名称</th>
				<th>接口方法名称</th>
				<th>路径</th>
				<th>请求方法</th>
				<th>接口方法介绍</th>
			</tr>
		</thead>
		<tbody>
			<c:forEach items="${voList}" var="vo">
				<tr>
					<td><fmt:formatDate value="${vo.date}" pattern="yyyy-MM-dd"/></td>
					<td>${vo.reviewer}</td>
					<td>${vo.apiName}</td>
					<td>${vo.name}</td>
					<td>${vo.path}</td>
					<td>${vo.httpMethod}</td>
					<td>${vo.notes}</td>
				</tr>
			</c:forEach>
		</tbody>
	</table>
</body>
</html>