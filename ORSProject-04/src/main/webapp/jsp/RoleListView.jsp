<%@page import="in.co.rays.proj4.bean.RoleBean"%>
<%@page import="java.util.List"%>
<%@page import="java.util.Iterator"%>
<%@page import="in.co.rays.proj4.util.DataUtility"%>
<%@page import="in.co.rays.proj4.util.ServletUtility"%>
<%@page import="in.co.rays.proj4.controller.ORSView"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Role List</title>
<link rel="icon" type="image/png"
	href="<%=ORSView.APP_CONTEXT%>/img/logo.png" sizes="16x16" />
</head>
<body>

	<%@ include file="Header.jsp"%>
	<jsp:useBean id="bean" class="in.co.rays.proj4.bean.RoleBean"
		scope="request"></jsp:useBean>

	<div align="center">

		<h1 style="margin-bottom: -15; color: navy">RoleList</h1>

		<div style="height: 15px margin-bottom: 12px">

			<h3>
				<font color="red"><%=ServletUtility.getErrorMessage(request) %></font>
			</h3>

			<h3>
				<font color="green"><%=ServletUtility.getSuccessMessage(request) %></font>
			</h3>
		</div>

		<form action="<%=ORSView.ROLE_LIST_CTL%>" method=post>

			<%
		int pageNo = ServletUtility.getPageNo(request);
		int pageSize = ServletUtility.getPageSize(request);
		int index = ((pageNo - 1) * pageSize) + 1;
		int nextListSize = DataUtility.getInt(request.getAttribute("nextLIstSize").toString());
		
		List<RoleBean> rolelist = (List<RoleBean>)request.getAttribute("rolelist");
		
		List<RoleBean> list = (List<RoleBean>) ServletUtility.getList(request);
		
		Iterator<RoleBean> it = list.iterator();
		
		if(list.size() != 0) {
		%>

			<input type="hidden" name="pageNo" value="<%=pageNo%>"> <input
				type="hidden" name="pageSize" value="<%=pageSize%>">

			<table>
				<tr>
				<tr>

					<td align="center"><label><b>Role : </b></label> <input
						type="text" name="name" placeholder="Enter Role"
						value="<%=DataUtility.getString(bean.getName())%>"> &nbsp;

						<input type="submit" name="operation"
						value="<%=RolelListCtl.OP_SERACH%>"> <input type="submit"
						name="operation" value="<%=RoleListCtl.OP_RESET%>"></td>
				</tr>
			</table>

			<table border="1" style="width: 100%; border: groove;">

				<tr style="background-color: #e1e6f1e3;">
					<th width="5%"><input type="checkbox" id="selectall"></th>
					<th width="5%">S.No</th>
					<th width="25%">Role</th>
					<th width="60%">Description</th>
					<th width="5%">Edit</th>
					
					
			</table>





		</form>



	</div>




</body>
</html>