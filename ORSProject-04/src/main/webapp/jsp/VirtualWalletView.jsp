<%@page import="in.co.rays.proj4.controller.VirtualWalletCtl"%>
<%@page import="in.co.rays.proj4.util.HTMLUtility"%>
<%@page import="javax.swing.text.html.HTML"%>
<%@page import="java.util.HashMap"%>
<%@page import="in.co.rays.proj4.util.DataUtility"%>
<%@page import="in.co.rays.proj4.util.ServletUtility"%>
<%@page import="in.co.rays.proj4.bean.VirtualWalletBean"%>
<%@page import="java.util.List"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Add VirtualWallet</title>
</head>
<body>
<form action="<%=ORSView.VIRTUALWALLET_CTL%>" method="post">

		<%@ include file="Header.jsp"%>

		<jsp:useBean id="bean" class="in.co.rays.proj4.bean.VirtualWalletBean"
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
				VirtualWallet
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
					<th align="left">WalletCode<span style="color: red">*</span></th>
					<td><input type="text" name="walletCode"
						placeholder="Enter WalletCode"
						value="<%=DataUtility.getStringData(bean.getWalletCode())%>"></td>
					<td style="position: fixed;"><font color="red"> <%=ServletUtility.getErrorMessage("walletCode", request)%></font></td>
				</tr>
				<tr>
					<th align="left">UserName<span style="color: red">*</span></th>
					<td><input type="text" name="userName"
						placeholder="Enter UserName"
						value="<%=DataUtility.getStringData(bean.getUserName())%>"></td>
					<td style="position: fixed;"><font color="red"> <%=ServletUtility.getErrorMessage("userName", request)%></font></td>
				</tr>
				<tr>
					<th align="left">Balance<span style="color: red">*</span></th>
					<td><input type="text" name="balance"
						placeholder="Enter Balance"
						value="<%=DataUtility.getStringData(bean.getBalance())%>"></td>
					<td style="position: fixed;"><font color="red"> <%=ServletUtility.getErrorMessage("balance", request)%></font></td>
				</tr>
				<tr>
					<th align="left">Status<span style="color: red">*</span></th>
					<td>
					<%
					HashMap<String, String> map = new HashMap<String, String>();
					map.put("Active", "Active");
					map.put("Inactive", "Inactive");
					String htmlList = HTMLUtility.getList("status", bean.getStatus(), map);
					
					%><%=htmlList %>
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
						name="operation" value="<%=VirtualWalletCtl.OP_UPDATE%>"> <input
						type="submit" name="operation" value="<%=VirtualWalletCtl.OP_CANCEL%>">
						<%
						} else {
						%></td>
					<td align="left" colspan="2"><input type="submit"
						name="operation" value="<%=VirtualWalletCtl.OP_SAVE%>"> <input
						type="submit" name="operation" value="<%=VirtualWalletCtl.OP_RESET%>">
						<%
						}
						%></td>
				</tr>
			</table>
		</div>
	</form>
</body>
</html>