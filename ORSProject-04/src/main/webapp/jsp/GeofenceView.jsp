<%@page import="in.co.rays.proj4.util.HTMLUtility"%>
<%@page import="java.util.HashMap"%>
<%@page import="in.co.rays.proj4.controller.GeofenceCtl"%>
<%@page import="in.co.rays.proj4.util.DataUtility"%>
<%@page import="in.co.rays.proj4.util.ServletUtility"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Add Geofence</title>
<link rel="icon" type="image/png"
	href="<%=ORSView.APP_CONTEXT%>/img/logo.png" sizes="16x16" />
</head>
<body>
<form action="<%=ORSView.GEOFENCE_CTL%>" method="post">
		<%@ include file="Header.jsp"%>

		<jsp:useBean id="bean" class="in.co.rays.proj4.bean.GeofenceBean"
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
				GeoFence
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
					<th align="left">GeofenceCode<span style="color: red">*</span></th>
					<td><input type="text" name="geoFenceCode"
						placeholder="Enter GeoFenceCode"
						value="<%=DataUtility.getStringData(bean.getGeoFenceCode())%>"></td>
					<td style="position: fixed;"><font color="red"> <%=ServletUtility.getErrorMessage("geoFenceCode", request)%></font></td>
				</tr>
				<tr>
					<th align="left">LocationName<span style="color: red">*</span></th>
					<td><input type="text" name="locationName"
						placeholder="Enter Location Name"
						value="<%=DataUtility.getStringData(bean.getLocationName())%>"></td>
					<td style="position: fixed;"><font color="red"> <%=ServletUtility.getErrorMessage("locationName", request)%></font></td>
				</tr>
				<tr>
					<th align="left">Radius<span style="color: red">*</span></th>
					<td><input type="text" name="radius"
						placeholder="Enter Radius"
						value="<%=DataUtility.getStringData(bean.getRadius())%>"></td>
					<td style="position: fixed;"><font color="red"> <%=ServletUtility.getErrorMessage("radius", request)%></font></td>
				</tr>
				<tr>
					<th align="left">Status<span style="color: red">*</span></th>
					<td>
						<%
						HashMap<String, String> map = new HashMap<String, String>();
						map.put("Active", "Active");
						map.put("InActive", "InActive");

						String htmlList = HTMLUtility.getList("status", bean.getStatus(), map);
						%> <%=htmlList%>
					</td>
					<td style="position: fixed;"><font color="red"> <%=ServletUtility.getErrorMessage("status", request)%></font></td>
				</tr>
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
						name="operation" value="<%=GeofenceCtl.OP_UPDATE%>"> <input
						type="submit" name="operation" value="<%=GeofenceCtl.OP_CANCEL%>">
						<%
						} else {
						%>
					<td align="left" colspan="2"><input type="submit"
						name="operation" value="<%=GeofenceCtl.OP_SAVE%>"> <input
						type="submit" name="operation" value="<%=GeofenceCtl.OP_RESET%>">
						<%
						}
						%>
				</tr>
			</table>
		</div>
	</form>
</body>


</body>
</html>