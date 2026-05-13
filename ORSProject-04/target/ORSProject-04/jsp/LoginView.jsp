<%@page import="in.co.rays.proj4.controller.UserRegistrationCtl"%>
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

	<form action="<%=ORSView.LOGIN_CTL%>" method="post">
	<%@ include file="Header.jsp"%>
	
		<jsp:useBean id="bean" class="in.co.rays.proj4.bean.UserBean"
			scope="request"></jsp:useBean>

		<div align="center">
			<h1 style="color: navy">Login</h1>

			<div style="height: 15px; margin-bottom: 12px">
				<h3 align="center">
					<font color="red"><%=ServletUtility.getErrorMessage(request)%></font>
				</h3>

				<h3 align="center">
					<font color="green"><%=ServletUtility.getSuccessMessage(request)%></font>
				</h3>
				
				</div>

				<table>
					<tr>
						<th align="left">Login:<span style="color:red">*</span></th>
						<td><input type="email" name="login"
							placeholder="Enter Email Id"
							value="<%=DataUtility.getStringData(bean.getLogin())%>"></td>
						<td style="position: fixed"><font style="color: red"><%=ServletUtility.getErrorMessage("login", request)%></font></td>
					</tr>
					<tr>
						<th align="left">Password:<span style="color: red">*</span></th>
						<td><input type="password" name="password"
							placeholder="Enter Password"
							value="<%=DataUtility.getStringData(bean.getPassword())%>"></td>
						<td style="position: fixed"><font style="color: red"><%=ServletUtility.getErrorMessage("password", request)%></font></td>
					</tr>

					<tr>
						<th></th>
						<td><input type="submit" name="operation"
							value="<%=LoginCtl.OP_SIGN_IN%>">&nbsp
							<input type="submit" name="operation" value="<%=LoginCtl.OP_SIGN_UP%>">
							</td>
					</tr>
					
					<tr>
					<th></th>
					<td></td>
					</tr>
					<tr>
					<th></th>
					<td><a href="<%=ORSView.FORGET_PASSWORD_CTL%>"><b>Forget password?</b></a>&nbsp;</td>
				</tr>
				</table>
			</div>

	</form>
	<%@ include file="Footer.jsp"%>
</body>
</html>