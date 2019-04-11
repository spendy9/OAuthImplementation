<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		<title>Spring MVC</title>
	</head>
	<body>
		<div>
			<h2> Welcome to home page.</h2>
			<form:form method="POST" action="/SpringMVC/addEmployee" modelAttribute="employee">
				<table>
					<tr>
						<td><form:label path="name">Name</form:label></td>
						<td><form:input path="name"/></td>
					</tr>
					<tr>
						<td><form:label path="id">ID</form:label></td>
						<td><form:input path="id"/></td>
					</tr>
					<tr>
						<td><form:label path="contactNumber">Contact Number</form:label></td>
						<td><form:input path="contactNumber"/></td>
					</tr>
					<tr>
						<td><input type="submit" value="Submit"/></td>
					</tr>
				</table>
			</form:form>
		</div>
	
	</body>
</html>