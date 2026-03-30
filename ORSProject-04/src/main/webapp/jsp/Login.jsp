<%@page import="in.co.rays.proj4.controller.LoginCtl"%>
<%@page import="in.co.rays.proj4.util.DataUtility"%>
<%@page import="in.co.rays.proj4.util.ServletUtility"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>login page</title>
</head>
<body>
	<%@ include file="Header.jsp"%>

	<form action="<%=ORSView.LOGIN_CTL%>" method="post">
		<jsp:useBean id="bean" class="in.co.rays.proj4.bean.UserBean"
			scope="request"></jsp:useBean>

		<div align="center">
			<h1>Login</h1>

			<div style="height: 15px; margin-bottom: 12px">
				<h3 align="center">
					<font color="green"><%=ServletUtility.getSuccessMessage(request)%></font>
				</h3>
			</div>

			<div style="height: 15px; margin-bottom: 12px">
				<h3 align="center">
					<font color="red"><%=ServletUtility.getErrorMessage(request)%></font>
				</h3>

				<table>
					<tr>
						<th>Login:</th>
						<td><input type="email" name="login"
							placeholder="enter email"
							value="<%=DataUtility.getStringData(bean.getLogin())%>"></td>
						<td><font style="color: red"><%=ServletUtility.getErrorMessage("login", request)%></font></td>
					</tr>
					<tr>
						<th>Password:</th>
						<td><input type="password" name="password"
							placeholder="enter password"
							value="<%=DataUtility.getStringData(bean.getPassword())%>"></td>
						<td><font style="color: red"><%=ServletUtility.getErrorMessage("password", request)%></font></td>
					</tr>

					<tr>
						<th></th>
						<td><input type="submit" name="operation"
							value="<%=LoginCtl.OP_SIGN_IN%>"></td>
					</tr>

				</table>
			</div>

		</div>

	</form>
</body>
</html>