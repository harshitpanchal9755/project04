<%@page import="in.co.rays.proj4.bean.UserBean"%>
<%@page import="in.co.rays.proj4.bean.RoleBean"%>
<%@page import="in.co.rays.proj4.controller.ORSView"%>
<%@page import="in.co.rays.proj4.util.HTMLUtility"%>
<%@page import="in.co.rays.proj4.model.RoleModel"%>
<%@page import="java.text.SimpleDateFormat"%>
<%@page import="in.co.rays.proj4.util.DataUtility"%>
<%@page import="in.co.rays.proj4.controller.UserListCtl"%>
<%@page import="in.co.rays.proj4.util.ServletUtility"%>
<%@page import="java.util.List"%>
<%@page import="java.util.Iterator"%>

<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>User List</title>

<link rel="icon" type="image/png"
	href="<%=ORSView.APP_CONTEXT%>/img/logo.png" sizes="16x16" />

</head>

<body>

	<%@include file="Header.jsp"%>

	<jsp:useBean id="bean" class="in.co.rays.proj4.bean.UserBean"
		scope="request">
	</jsp:useBean>

	<div align="center">

		<h1 align="center" style="color: navy;">User List</h1>

		<h3>
			<font color="red"> <%=ServletUtility.getErrorMessage(request)%>
			</font>
		</h3>

		<h3>
			<font color="green"> <%=ServletUtility.getSuccessMessage(request)%>
			</font>
		</h3>

		<form action="<%=ORSView.USER_LIST_CTL%>" method="post">

			<%
			int pageNo = ServletUtility.getPageNo(request);

			int pageSize = ServletUtility.getPageSize(request);

			int index = ((pageNo - 1) * pageSize) + 1;

			int nextListSize = DataUtility.getInt(request.getAttribute("nextListSize").toString());

			List<RoleBean> roleList = (List<RoleBean>) request.getAttribute("roleList");

			List<UserBean> list = (List<UserBean>) ServletUtility.getList(request);

			Iterator<UserBean> it = list.iterator();
			%>

			<input type="hidden" name="pageNo" value="<%=pageNo%>"> <input
				type="hidden" name="pageSize" value="<%=pageSize%>">

			<table width="100%">

				<tr>

					<td align="center"><label><b>First Name :</b></label> <input
						type="text" name="firstName" placeholder="Enter First Name"
						value="<%=ServletUtility.getParameter("firstName", request)%>">

						&nbsp;&nbsp; <label><b>Login Id :</b></label> <input type="text"
						name="login" placeholder="Enter Login Id"
						value="<%=ServletUtility.getParameter("login", request)%>">

						&nbsp;&nbsp; <label><b>Role :</b></label> <%=HTMLUtility.getList("roleId", String.valueOf(bean.getRoleId()), roleList)%>
						&nbsp;&nbsp; <input type="submit" name="operation"
						value="<%=UserListCtl.OP_SEARCH%>"> <input type="submit"
						name="operation" value="<%=UserListCtl.OP_RESET%>"></td>

				</tr>

			</table>

			<br>

			<%
			if (list != null && list.size() > 0) {
			%>

			<table border="1" width="100%" style="border-collapse: collapse;">

				<tr style="background-color: #e1e6f1e3;">

					<th><input type="checkbox" id="selectall"></th>

					<th>S.No</th>

					<th>First Name</th>

					<th>Last Name</th>

					<th>Login Id</th>

					<th>Mobile No</th>

					<th>Gender</th>

					<th>Date Of Birth</th>

					<th>Role</th>

					<th>Edit</th>

				</tr>

				<%
				while (it.hasNext()) {

					bean = (UserBean) it.next();

					RoleModel model = new RoleModel();

					RoleBean roleBean = model.findBypk(bean.getRoleId());

					SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");

					String date = "";

					if (bean.getDob() != null) {
						date = sdf.format(bean.getDob());
					}
				%>

				<tr>

					<td align="center"><input type="checkbox" class="case"
						name="ids" value="<%=bean.getId()%>"
						<%=(user.getId() == bean.getId() || bean.getRoleId() == RoleBean.ADMIN) ? "disabled" : ""%>></td>

					<td align="center"><%=index++%></td>

					<td align="center" style="text-transform: capitalize;"><%=bean.getFirstName()%>

					</td>

					<td align="center" style="text-transform: capitalize;"><%=bean.getLastName()%>

					</td>

					<td align="center"><%=bean.getLogin()%></td>

					<td align="center"><%=bean.getMobileNo()%></td>

					<td align="center"><%=bean.getGender()%></td>

					<td align="center"><%=date%></td>

					<td align="center" style="text-transform: capitalize;"><%=roleBean != null ? roleBean.getName() : "N/A"%></td>

					<td align="center"><a
						href="<%=ORSView.USER_CTL%>?id=<%=bean.getId()%>"
						<%=(user.getId() == bean.getId() || bean.getRoleId() == RoleBean.ADMIN) ? "onclick='return false;'" : ""%>>Edit</a></td>

				</tr>

				<%
				}
				%>

			</table>

			<br>

			<table width="100%">

				<tr>

					<td align="left"><input type="submit" name="operation"
						value="<%=UserListCtl.OP_PREVIOUS%>"
						<%=pageNo > 1 ? "" : "disabled"%>></td>

					<td align="center"><input type="submit" name="operation"
						value="<%=UserListCtl.OP_NEW%>"></td>

					<td align="center"><input type="submit" name="operation"
						value="<%=UserListCtl.OP_DELETE%>"></td>

					<td align="right"><input type="submit" name="operation"
						value="<%=UserListCtl.OP_NEXT%>"
						<%=nextListSize != 0 ? "" : "disabled"%>></td>

				</tr>

			</table>

			<%
			} else {
			%>

			<h3>
				<font color="red"> No Record Found </font>
			</h3>

			<input type="submit" name="operation"
				value="<%=UserListCtl.OP_BACK%>">

			<%
			}
			%>

		</form>

	</div>

</body>
</html>