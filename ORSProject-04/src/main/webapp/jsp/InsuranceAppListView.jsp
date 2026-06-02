<%@page import="in.co.rays.proj4.controller.InsuranceAppListCtl"%>
<%@page import="java.util.Iterator"%>
<%@page import="in.co.rays.proj4.bean.InsuranceAppBean"%>
<%@page import="java.util.List"%>
<%@page import="in.co.rays.proj4.util.DataUtility"%>
<%@page import="in.co.rays.proj4.util.ServletUtility"%>
<%@page import="in.co.rays.proj4.controller.ORSView"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Insurance List</title>
<link rel="icon" type="image/png"
	href="<%=ORSView.APP_CONTEXT%>/img/logo.png" sizes="16x16" />
</head>
<body>
	<%@include file="Header.jsp"%>

	<jsp:useBean id="bean"
		class="in.co.rays.proj4.bean.InsuranceAppBean" scope="request"></jsp:useBean>

	<div align="center">
		<h1 align="center" style="margin-bottom: -15; color: navy;">BatchProcessing
			List</h1>

		<div style="height: 15px; margin-bottom: 12px">
			<h3>
				<font color="red"><%=ServletUtility.getErrorMessage(request)%></font>
			</h3>
			<h3>
				<font color="green"><%=ServletUtility.getSuccessMessage(request)%></font>
			</h3>
		</div>

		<form action="<%=ORSView.INSURANCEAPP_LIST_CTL%>" method="post">
			<%
			int pageNo = ServletUtility.getPageNo(request);
			int pageSize = ServletUtility.getPageSize(request);
			int index = ((pageNo - 1) * pageSize) + 1;
			int nextListSize = DataUtility.getInt(request.getAttribute("nextListSize").toString());

			List<InsuranceAppBean> insuranceList = (List<InsuranceAppBean>) request.getAttribute("insuranceList");
			List<InsuranceAppBean> list = (List<InsuranceAppBean>) ServletUtility.getList(request);
			Iterator<InsuranceAppBean> it = list.iterator();

			if (list.size() != 0) {
			%>

			<input type="hidden" name="pageNo" value="<%=pageNo%>"> <input
				type="hidden" name="pageSize" value="<%=pageSize%>">

			<table style="width: 100%">
				<tr>
					<td align="center"><label><b>CustomerName : </b></label> <input
						type="type" name="customername" placeholder="Enter CustomerName"
						value="<%=ServletUtility.getParameter("customername", request)%>">

						&nbsp;&nbsp;<label><b>PolicyType :</b></label> <input type="text"
						name="policytype" placeholder="PolicyType"
						value="<%=ServletUtility.getParameter("policytype", request)%>">&emsp;

						<input type="submit" name="operation"
						value="<%=InsuranceAppListCtl.OP_SEARCH%>"> &nbsp; <input
						type="submit" name="operation"
						value="<%=InsuranceAppListCtl.OP_RESET%>"></td>
				</tr>
			</table>
			<br>

			<table border="1" style="width: 100%; border: groove;">
				<tr style="background-color: #e1e6f1e3;">
					<th width="5%"><input type="checkbox" id="selectall" /></th>
					<th width="5%">S.No</th>
					<th width="13%">CustomerName</th>
					<th width="13%">PolicyType</th>
					<th width="23%">PremiumAmount</th>
					<th width="10%">ClaimStatus</th>
					<th width="10%">Edit</th>

				</tr>

				<%
				while (it.hasNext()) {
					bean = (InsuranceAppBean) it.next();
				%>

				<tr>
					<td style="text-align: center;"><input type="checkbox"
						class="case" name="ids" value="<%=bean.getId()%>"></td>
					<td style="text-align: center;"><%=index++%></td>
					<td style="text-align: center; text-transform: capitalize;"><%=bean.getCustomerName()%></td>
					<td style="text-align: center; text-transform: capitalize;"><%=bean.getPolicyType()%></td>
					<td style="text-align: center;"><%=bean.getAmount()%></td>
					<td style="text-align: center;"><%=bean.getClaimStatus()%></td>

					<td style="text-align: center;"><a
						href="InsuranceAppCtl?id=<%=bean.getId()%>">Edit</a></td>
				</tr>

				<%
				}
				%>
			</table>

			<table style="width: 100%">
				<tr>
					<td style="width: 25%"><input type="submit" name="operation"
						value="<%=InsuranceAppListCtl.OP_PREVIOUS%>"
						<%=pageNo > 1 ? "" : "disabled"%>></td>
					<td align="center" style="width: 25%"><input type="submit"
						name="operation" value="<%=InsuranceAppListCtl.OP_NEW%>"></td>
					<td align="center" style="width: 25%"><input type="submit"
						name="operation" value="<%=InsuranceAppListCtl.OP_DELETE%>"></td>
					<td style="width: 25%" align="right"><input type="submit"
						name="operation" value="<%=InsuranceAppListCtl.OP_NEXT%>"
						<%=nextListSize != 0 ? "" : "disabled"%>></td>
				</tr>
			</table>

			<%
			} else {
			%>

			<table>
				<tr>
					<td align="right"><input type="submit" name="operation"
						value="<%=InsuranceAppListCtl.OP_BACK%>"></td>
				</tr>
			</table>

			<%
			}
			%>
		</form>
	</div>
</body>
</html>