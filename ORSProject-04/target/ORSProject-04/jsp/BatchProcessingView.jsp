<%@page import="in.co.rays.proj4.controller.BatchProcessingCtl"%>
<%@page import="in.co.rays.proj4.util.HTMLUtility"%>
<%@page import="java.util.HashMap"%>
<%@page import="in.co.rays.proj4.util.DataUtility"%>
<%@page import="in.co.rays.proj4.util.ServletUtility"%>
<%@page import="in.co.rays.proj4.controller.ORSView"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Add BatchProcessing</title>
<link rel="icon" type="image/png"
	href="<%=ORSView.APP_CONTEXT%>/img/logo.png" sizes="16x16" />
</head>
<body>
<form action="<%=ORSView.BATCHPROCESSING_CTL%>" method="post">
		<%@ include file="Header.jsp"%>

		<jsp:useBean id="bean" class="in.co.rays.proj4.bean.BatchProcessingBean"
			scope="request"></jsp:useBean>

		<div align="center">
			<h1 align="center" style="margin-bottom: -15; color: navy">
				<%
				if (bean != null && bean.getId() > 0) {
				%>Update<%
				} else {
				%>Add<%
				}
				%>
				BatchProcessing
			</h1>

			<div style="height: 15px; margin-bottom: 12px">
				<H3 align="center">
					<font color="green"> <%=ServletUtility.getSuccessMessage(request)%>
					</font>
				</H3>
				<H3 align="center">
					<font color="red"> <%=ServletUtility.getErrorMessage(request)%>
					</font>
				</H3>
			</div>
			<input type="hidden" name="id" value="<%=bean.getId()%>"> <input
				type="hidden" name="createdBy" value="<%=bean.getCreatedBy()%>">
			<input type="hidden" name="modifiedBy"
				value="<%=bean.getModifiedBy()%>"> <input type="hidden"
				name="createdDatetime"
				value="<%=DataUtility.getTimestamp(bean.getCreatedDateTime())%>">
			<input type="hidden" name="modifiedDatetime"
				value="<%=DataUtility.getTimestamp(bean.getModifiedDateTime())%>">

			<table>
				<tr>
					<th align="left">BatchCode<span style="color: red">*</span></th>
					<td><input type="text" name="batchCode"
						placeholder="Enter BatchCode"
						value="<%=DataUtility.getStringData(bean.getBatchCode())%>"></td>
					<td style="position: fixed;"><font color="red"> <%=ServletUtility.getErrorMessage("batchCode", request)%></font></td>
				</tr>
				<tr>
					<th align="left">BatchName<span style="color: red">*</span></th>
					<td><input type="text" name="batchName"
						placeholder="Enter BatchName"
						value="<%=DataUtility.getStringData(bean.getBatchName())%>"></td>
					<td style="position: fixed;"><font color="red"> <%=ServletUtility.getErrorMessage("batchName", request)%></font></td>
				</tr>
				<tr>
					<th align="left">TotalRecords<span style="color: red">*</span></th>
					<td><input type="text" name="totalRecords" placeholder="Enter Number"
						value="<%=(bean.getTotalRecords() > 0) ? bean.getTotalRecords() : "" %>"></td>
					<td style="position: fixed;"><font color="red"> <%=ServletUtility.getErrorMessage("totalRecords", request)%></font></td>
				</tr>
				
				<tr>
					<th align="left">Status<span style="color: red">*</span></th>
					<td>
						<%
						HashMap<String, String> map = new HashMap<String, String>();
						map.put("Completed", "Completed");
						map.put("Processing", "Processing");
						map.put("Pending", "Pending");

						String htmlList = HTMLUtility.getList("status", bean.getStatus(), map);
						%> <%=htmlList%>
					</td>
					<td style="position: fixed;"><font color="red"> <%=ServletUtility.getErrorMessage("status", request)%></font></td>

				</tr>
				
				<tr>
					<th></th>
					<td></td>
				</tr>
				<tr>
					<th></th>
					<%
					if (bean != null && bean.getId() > 0) {
					%>
					<td align="left" colspan="2"><input type="submit"
						name="operation" value="<%=BatchProcessingCtl.OP_UPDATE%>"> <input
						type="submit" name="operation" value="<%=BatchProcessingCtl.OP_CANCEL%>">
						<%
						} else {
						%>
					<td align="left" colspan="2"><input type="submit"
						name="operation" value="<%=BatchProcessingCtl.OP_SAVE%>"> <input
						type="submit" name="operation" value="<%=BatchProcessingCtl.OP_RESET%>">
						<%
						}
						%>
				</tr>
			</table>
		</div>
	</form>
</body>
</html>