<%@page import="java.util.Iterator"%>
<%@page import="java.util.List"%>
<%@page import="in.co.rays.proj4.util.DataUtility"%>
<%@page import="in.co.rays.proj4.bean.VoiceAssistantBean"%>
<%@page import="in.co.rays.proj4.util.ServletUtility"%>
<%@page import="in.co.rays.proj4.controller.ORSView"%>

<%@page import="in.co.rays.proj4.controller.VoiceAssistantListCtl"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>VoiceAssistant List</title>
<link rel="icon" type="image/png"
	href="<%=ORSView.APP_CONTEXT%>/img/logo.png" sizes="16x16" />
</head>
<body>
<%@include file="Header.jsp"%>

	<jsp:useBean id="bean"
		class="in.co.rays.proj4.bean.VoiceAssistantBean" scope="request"></jsp:useBean>

	<div align="center">
		<h1 align="center" style="margin-bottom: -15; color: navy;">VoiceAssistant List</h1>

		<div style="height: 15px; margin-bottom: 12px">
			<h3>
				<font color="red"><%=ServletUtility.getErrorMessage(request)%></font>
			</h3>
			<h3>
				<font color="green"><%=ServletUtility.getSuccessMessage(request)%></font>
			</h3>
		</div>

		<form action="<%=ORSView.VOICEASSISTANT_LIST_CTL%>" method="post">
			<%
			int pageNo = ServletUtility.getPageNo(request);
			int pageSize = ServletUtility.getPageSize(request);
			int index = ((pageNo - 1) * pageSize) + 1;
			int nextListSize = DataUtility.getInt(request.getAttribute("nextListSize").toString());

			List<VoiceAssistantBean> voiceList = (List<VoiceAssistantBean>) request.getAttribute("voiceList");
			List<VoiceAssistantBean> list = (List<VoiceAssistantBean>) ServletUtility.getList(request);
			Iterator<VoiceAssistantBean> it = list.iterator();

			if (list.size() != 0) {
			%>

			<input type="hidden" name="pageNo" value="<%=pageNo%>"> <input
				type="hidden" name="pageSize" value="<%=pageSize%>">

			<table style="width: 100%">
				<tr>
					<td align="center"><label><b>UserVoice : </b></label> <input
						type="type" name="userVoice" placeholder="Enter UserVoice"
						value="<%=ServletUtility.getParameter("userVoice", request)%>">

						&nbsp;&nbsp;<label><b>Response :</b></label> <input type="text"
						name="response" placeholder="Enter Response"
						value="<%=ServletUtility.getParameter("response", request)%>">&emsp;

						<input type="submit" name="operation"
						value="<%=VoiceAssistantListCtl.OP_SEARCH%>"> &nbsp; <input
						type="submit" name="operation"
						value="<%=VoiceAssistantListCtl.OP_RESET%>"></td>
				</tr>
			</table>
			<br>

			<table border="1" style="width: 100%; border: groove;">
				<tr style="background-color: #e1e6f1e3;">
					<th width="5%"><input type="checkbox" id="selectall" /></th>
					<th width="5%">S.No</th>
					<th width="13%">UserVoice</th>
					<th width="13%">Response</th>
					<th width="23%">Language</th>
					<th width="10%">Accuracy</th>
					<th width="10%">Edit</th>

				</tr>

				<%
				while (it.hasNext()) {
					bean = (VoiceAssistantBean) it.next();
				%>

				<tr>
					<td style="text-align: center;"><input type="checkbox"
						class="case" name="ids" value="<%=bean.getId()%>"></td>
					<td style="text-align: center;"><%=index++%></td>
					<td style="text-align: center; text-transform: capitalize;"><%=bean.getUserVoice()%></td>
					<td style="text-align: center; text-transform: capitalize;"><%=bean.getResponse()%></td>
					<td style="text-align: center;"><%=bean.getLanguage()%></td>
					<td style="text-align: center;"><%=bean.getAccuracy()%></td>

					<td style="text-align: center;"><a
						href="VoiceAssistantCtl?id=<%=bean.getId()%>">Edit</a></td>
				</tr>

				<%
				}
				%>
			</table>

			<table style="width: 100%">
				<tr>
					<td style="width: 25%"><input type="submit" name="operation"
						value="<%=VoiceAssistantListCtl.OP_PREVIOUS%>"
						<%=pageNo > 1 ? "" : "disabled"%>></td>
					<td align="center" style="width: 25%"><input type="submit"
						name="operation" value="<%=VoiceAssistantListCtl.OP_NEW%>"></td>
					<td align="center" style="width: 25%"><input type="submit"
						name="operation" value="<%=VoiceAssistantListCtl.OP_DELETE%>"></td>
					<td style="width: 25%" align="right"><input type="submit"
						name="operation" value="<%=VoiceAssistantListCtl.OP_NEXT%>"
						<%=nextListSize != 0 ? "" : "disabled"%>></td>
				</tr>
			</table>

			<%
			} else {
			%>

			<table>
				<tr>
					<td align="right"><input type="submit" name="operation"
						value="<%=VoiceAssistantListCtl.OP_BACK%>"></td>
				</tr>
			</table>

			<%
			}
			%>
		</form>
	</div>
</body>
</html>