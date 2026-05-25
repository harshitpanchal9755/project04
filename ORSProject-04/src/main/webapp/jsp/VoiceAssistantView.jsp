<%@page import="in.co.rays.proj4.controller.VoiceAssistantCtl"%>
<%@page import="in.co.rays.proj4.util.DataUtility"%>
<%@page import="in.co.rays.proj4.util.ServletUtility"%>
<%@page import="in.co.rays.proj4.bean.VoiceAssistantBean"%>
<%@page import="java.util.List"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Add VoiceAssistant</title>
</head>
<body>
<form action="<%=ORSView.VOICEASSISTANT_CTL%>" method="post">

		<%@ include file="Header.jsp"%>

		<jsp:useBean id="bean" class="in.co.rays.proj4.bean.VoiceAssistantBean"
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
				VoiceAssistant
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
					<th align="left">UserVoice<span style="color: red">*</span></th>
					<td><input type="text" name="userVoice"
						placeholder="Enter UserVoice"
						value="<%=DataUtility.getStringData(bean.getUserVoice())%>"></td>
					<td style="position: fixed;"><font color="red"> <%=ServletUtility.getErrorMessage("userVoice", request)%></font></td>
				</tr>
				<tr>
					<th align="left">Response<span style="color: red">*</span></th>
					<td><input type="text" name="response"
						placeholder="Enter Response"
						value="<%=DataUtility.getStringData(bean.getResponse())%>"></td>
					<td style="position: fixed;"><font color="red"> <%=ServletUtility.getErrorMessage("response", request)%></font></td>
				</tr>
				<tr>
					<th align="left">Language<span style="color: red">*</span></th>
					<td><input type="text" name="language"
						placeholder="Enter Language"
						value="<%=DataUtility.getStringData(bean.getLanguage())%>"></td>
					<td style="position: fixed;"><font color="red"> <%=ServletUtility.getErrorMessage("language", request)%></font></td>
				</tr>
				<tr>
					<th align="left">Accuracy<span style="color: red">*</span></th>
					<td><input type="text" name="accuracy"
						placeholder="Enter Accuracy"
						value="<%=bean.getAccuracy() == 0 ? "" : bean.getAccuracy()%>"></td>
					<td style="position: fixed;"><font color="red"> <%=ServletUtility.getErrorMessage("accuracy", request)%></font></td>
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
						name="operation" value="<%=VoiceAssistantCtl.OP_UPDATE%>"> <input
						type="submit" name="operation" value="<%=VoiceAssistantCtl.OP_CANCEL%>">
						<%
						} else {
						%></td>
					<td align="left" colspan="2"><input type="submit"
						name="operation" value="<%=VoiceAssistantCtl.OP_SAVE%>"> <input
						type="submit" name="operation" value="<%=VoiceAssistantCtl.OP_RESET%>">
						<%
						}
						%></td>
				</tr>
			</table>
		</div>
	</form>

</body>

</body>
</html>