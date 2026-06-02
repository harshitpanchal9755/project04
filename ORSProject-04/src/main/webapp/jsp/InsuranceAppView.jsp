<%@page import="in.co.rays.proj4.controller.InsuranceAppCtl"%>
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
<title>Add Insurance</title>
</head>
<body>
<form action="<%=ORSView.INSURANCEAPP_CTL%>" method="post">

		<%@ include file="Header.jsp"%>

		<jsp:useBean id="bean" class="in.co.rays.proj4.bean.InsuranceAppBean"
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
				InsuranceApp
			</h1>

			<div style="height: 15px; margin-bottom: 12px">
				<H3 align="center">
					<font color="red"> <%=ServletUtility.getErrorMessage(request)%>
					</font>
				</H3>

				<H3 align="center">
					<font color="green"> <%=ServletUtility.getSuccessMessage(request)%>
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
					<th align="left">CustomerName<span style="color: red">*</span></th>
					<td><input type="text" name="customername"
						placeholder="Enter CustomerName"
						value="<%=DataUtility.getStringData(bean.getCustomerName())%>"></td>
					<td style="position: fixed;"><font color="red"> <%=ServletUtility.getErrorMessage("customername", request)%></font></td>
				</tr>
				<tr>
					<th align="left">PolicyType<span style="color: red">*</span></th>
					<td><input type="text" name="policytype"
						placeholder="Enter PolicyType"
						value="<%=DataUtility.getStringData(bean.getPolicyType())%>"></td>
					<td style="position: fixed;"><font color="red"> <%=ServletUtility.getErrorMessage("policytype", request)%></font></td>
				</tr>
				<tr>
					<th align="left">Amount<span style="color: red">*</span></th>
					<td><input type="text" name="amount"
						placeholder="Enter Amount"
						value="<%=bean.getAmount() == 0 ? "" : bean.getAmount()%>"></td>
					<td style="position: fixed;"><font color="red"> <%=ServletUtility.getErrorMessage("amount", request)%></font></td>
				</tr>
				<tr>
					<th align="left">ClaimStatus<span style="color: red">*</span></th>
					<td>
					<%
					HashMap<String, String> map = new HashMap<String, String>();
					map.put("Active", "Active");
					map.put("Inactive", "Inactive");
					String htmlList = HTMLUtility.getList("claimstatus", bean.getClaimStatus(), map);
					
					%><%=htmlList %>
					</td>
					<td style="position: fixed;"><font color="red"> <%=ServletUtility.getErrorMessage("claimstatus", request)%></font></td>
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
						name="operation" value="<%=InsuranceAppCtl.OP_UPDATE%>"> <input
						type="submit" name="operation" value="<%=InsuranceAppCtl.OP_CANCEL%>">
						<%
						} else {
						%></td>
					<td align="left" colspan="2"><input type="submit"
						name="operation" value="<%=InsuranceAppCtl.OP_SAVE%>"> <input
						type="submit" name="operation" value="<%=InsuranceAppCtl.OP_RESET%>">
						<%
						}
						%></td>
				</tr>
			</table>
		</div>
	</form>
</body>
</html>