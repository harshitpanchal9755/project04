<%@page import="java.text.SimpleDateFormat"%>
<%@page import="in.co.rays.proj4.model.BatchProcessingModel"%>
<%@page import="in.co.rays.proj4.controller.BatchProcessingListCtl"%>
<%@page import="java.util.Iterator"%>
<%@page import="java.util.List"%>
<%@page import="in.co.rays.proj4.bean.BatchProcessingBean"%>
<%@page import="in.co.rays.proj4.util.DataUtility"%>
<%@page import="in.co.rays.proj4.util.ServletUtility"%>
<%@page import="in.co.rays.proj4.controller.ORSView"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>BatchProcesssing List</title>
<link rel="icon" type="image/png"
	href="<%=ORSView.APP_CONTEXT%>/img/logo.png" sizes="16x16" />
</head>
<body>
	<%@include file="Header.jsp"%>

	<jsp:useBean id="bean"
		class="in.co.rays.proj4.bean.BatchProcessingBean" scope="request"></jsp:useBean>

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

		<form action="<%=ORSView.BATCHPROCESSING_LIST_CTL%>" method="post">
			<%
			int pageNo = ServletUtility.getPageNo(request);
			int pageSize = ServletUtility.getPageSize(request);
			int index = ((pageNo - 1) * pageSize) + 1;
			int nextListSize = DataUtility.getInt(request.getAttribute("nextListSize").toString());

			List<BatchProcessingBean> batchprocessingList = (List<BatchProcessingBean>) request.getAttribute("batchprocessingList");
			List<BatchProcessingBean> list = (List<BatchProcessingBean>) ServletUtility.getList(request);
			Iterator<BatchProcessingBean> it = list.iterator();

			if (list.size() != 0) {
			%>

			<input type="hidden" name="pageNo" value="<%=pageNo%>"> <input
				type="hidden" name="pageSize" value="<%=pageSize%>">

			<table style="width: 100%">
				<tr>
					<td align="center"><label><b>BatchCode : </b></label> <input
						type="type" name="batchCode" placeholder="Enter BatchCode"
						value="<%=ServletUtility.getParameter("batchCode", request)%>">

						&nbsp;&nbsp;<label><b>BatchName :</b></label> <input type="text"
						name="batchName" placeholder="BatchName"
						value="<%=ServletUtility.getParameter("batchName", request)%>">&emsp;

						<input type="submit" name="operation"
						value="<%=BatchProcessingListCtl.OP_SEARCH%>"> &nbsp; <input
						type="submit" name="operation"
						value="<%=BatchProcessingListCtl.OP_RESET%>"></td>
				</tr>
			</table>
			<br>

			<table border="1" style="width: 100%; border: groove;">
				<tr style="background-color: #e1e6f1e3;">
					<th width="5%"><input type="checkbox" id="selectall" /></th>
					<th width="5%">S.No</th>
					<th width="13%">BatchCode</th>
					<th width="13%">BatchName</th>
					<th width="23%">TotalRecords</th>
					<th width="10%">Status</th>
					<th width="10%">Edit</th>

				</tr>

				<%
				while (it.hasNext()) {
					bean = (BatchProcessingBean) it.next();
				%>

				<tr>
					<td style="text-align: center;"><input type="checkbox"
						class="case" name="ids" value="<%=bean.getId()%>"></td>
					<td style="text-align: center;"><%=index++%></td>
					<td style="text-align: center; text-transform: capitalize;"><%=bean.getBatchCode()%></td>
					<td style="text-align: center; text-transform: capitalize;"><%=bean.getBatchName()%></td>
					<td style="text-align: center;"><%=bean.getTotalRecords()%></td>
					<td style="text-align: center;"><%=bean.getStatus()%></td>

					<td style="text-align: center;"><a
						href="BatchProcessingCtl?id=<%=bean.getId()%>">Edit</a></td>
				</tr>

				<%
				}
				%>
			</table>

			<table style="width: 100%">
				<tr>
					<td style="width: 25%"><input type="submit" name="operation"
						value="<%=BatchProcessingListCtl.OP_PREVIOUS%>"
						<%=pageNo > 1 ? "" : "disabled"%>></td>
					<td align="center" style="width: 25%"><input type="submit"
						name="operation" value="<%=BatchProcessingListCtl.OP_NEW%>"></td>
					<td align="center" style="width: 25%"><input type="submit"
						name="operation" value="<%=BatchProcessingListCtl.OP_DELETE%>"></td>
					<td style="width: 25%" align="right"><input type="submit"
						name="operation" value="<%=BatchProcessingListCtl.OP_NEXT%>"
						<%=nextListSize != 0 ? "" : "disabled"%>></td>
				</tr>
			</table>

			<%
			} else {
			%>

			<table>
				<tr>
					<td align="right"><input type="submit" name="operation"
						value="<%=BatchProcessingListCtl.OP_BACK%>"></td>
				</tr>
			</table>

			<%
			}
			%>
		</form>
	</div>
</body>
</html>