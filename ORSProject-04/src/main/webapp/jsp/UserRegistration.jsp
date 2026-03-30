<%@page import="in.co.rays.proj4.controller.ORSView"%>
<%@page import="in.co.rays.proj4.controller.UserRegistrationCtl"%>
<%@page import="in.co.rays.proj4.util.HTMLUtility"%>
<%@page import="java.util.HashMap"%>
<%@page import="in.co.rays.proj4.util.DataUtility"%>
<%@page import="in.co.rays.proj4.util.ServletUtility"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Signup</title>
</head>
<body>
	<%@ include file="Header.jsp"%>

	<form action="<%=ORSView.USER_REGISTRATION_CTL%>" method="post">

		<jsp:useBean id="bean" class="in.co.rays.proj4.bean.UserBean"
			scope="request"></jsp:useBean>

		<div align="center">
			<h1>User Registration</h1>


			<div style="height: 15px; margin-bottom: 12px">
				<h3 align="center" style="color: green">
					<font><%=ServletUtility.getSuccessMessage(request)%></font>
				</h3>

				<h3 align="center" style="color: red">
					<font><%=ServletUtility.getErrorMessage(request)%></font>
				</h3>
			</div>


			<table>

				<tr>
					<th>FirstName:</th>
					<td><input type="text" name="firstName"
						placeholder="enter firstname"
						value="<%=DataUtility.getStringData(bean.getFirstName())%>"></td>
					<td style="position: fixed;"><font color="red"><%=ServletUtility.getErrorMessage("firstName", request)%></font></td>
				</tr>

				<tr>
					<th>LastName:</th>
					<td><input type="text" name="lastName"
						placeholder="enter lastname"
						value="<%=DataUtility.getStringData(bean.getLastName())%>"></td>
					<td style="position: fixed;"><font color="red"><%=ServletUtility.getErrorMessage("lastName", request)%></font></td>
				</tr>

				<tr>
					<th>Login ID:</th>
					<td><input type="email" name="login" placeholder="enter email"
						value="<%=DataUtility.getStringData(bean.getLogin())%>"></td>
					<td style="position: fixed;"><font color="red"><%=ServletUtility.getErrorMessage("login", request)%></font></td>
				</tr>

				<tr>
					<th>Password:</th>
					<td><input type="password" name="password"
						placeholder="enter password"
						value="<%=DataUtility.getStringData(bean.getPassword())%>"></td>
					<td style="position: fixed;"><font color="red"><%=ServletUtility.getErrorMessage("password", request)%></font></td>
				</tr>

				<tr>
					<th>Confirm:</th>
					<td><input type="text" name="confirmPassword"
						placeholder="enter confirmpassword"
						value="<%=DataUtility.getStringData(bean.getConfirmPassword())%>"></td>
					<td style="position: fixed;"><font color="red"><%=ServletUtility.getErrorMessage("confirmPassword", request)%></font></td>
				</tr>

				<tr>
					<th>Dob</th>
					<td><input type="date" name="dob"
						value="<%=DataUtility.getStringData(bean.getDob())%>"
						style="width: 98%"></td>
					<td style="position: fixed;"><font color="red"><%=ServletUtility.getErrorMessage("dob", request)%></font></td>
				</tr>

				<tr>
					<th>gender</th>
					<td>
						<%
						HashMap<String, String> map = new HashMap<String, String>();
						map.put("male", "male");
						map.put("female", "female");
						map.put("other", "other");
						%><%=HTMLUtility.getList("gender", bean.getGender(), map)%>
					</td>
					<td style="position: fixed;"><font color="red"><%=ServletUtility.getErrorMessage("gender", request)%></font></td>
				</tr>

				<tr>
					<th>Mobile:</th>
					<td><input type="text" name="mobileNo" placeholder="enter mobileNo"
						value="<%=DataUtility.getStringData(bean.getMobileNo())%>"></td>
					<td style="position: fixed;"><font color="red"> <%=ServletUtility.getErrorMessage("mobileNo", request)%></font></td>
				</tr>
				<tr>
					<td></td>
					<td><input type="submit" name="operation"
						value="<%=UserRegistrationCtl.OP_SIGN_UP%>"></td>
				</tr>
			</table>

		</div>
	</form>
</body>
</html>