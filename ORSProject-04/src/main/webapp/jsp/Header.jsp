<%@page import="in.co.rays.proj4.bean.RoleBean"%>
<%@page import="in.co.rays.proj4.controller.LoginCtl"%>
<%@page import="in.co.rays.proj4.bean.UserBean"%>
<%@page import="in.co.rays.proj4.controller.ORSView"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>

<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Header Page</title>

<!-- JQuery -->
<script src="https://code.jquery.com/jquery-3.6.4.min.js"></script>

<!-- JQuery UI -->
<script src="https://code.jquery.com/ui/1.13.2/jquery-ui.min.js"></script>

<!-- JQuery UI CSS -->
<link rel="stylesheet"
	href="https://code.jquery.com/ui/1.13.2/themes/base/jquery-ui.css">

<script src="/ORSProject-04/js/checkbox.js"></script>
<script src="/ORSProject-04/js/datepicker.js"></script>

</head>

<body>

	<!-- Logo -->
	<img src="<%=request.getContextPath()%>/img/customLogo.jpg"
		align="right" width="100" height="40" border="0">

	<%
	UserBean user = (UserBean) session.getAttribute("user");
	%>

	<%
	if (user != null) {
	%>

	<h3>
		Hi,
		<%=user.getFirstName()%>
		(<%=session.getAttribute("role")%>)
	</h3>

	<%
	if (user.getRoleId() == RoleBean.ADMIN) {
	%>

	<!-- User -->
	<a href="<%=ORSView.USER_CTL%>"><b>Add User</b></a>
	<b>|</b>

	<a href="<%=ORSView.USER_LIST_CTL%>"><b>User List</b></a>
	<b>|</b>

	<!-- Role -->
	<a href="<%=ORSView.ROLE_CTL%>"><b>Add Role</b></a>
	<b>|</b>

	<a href="<%=ORSView.ROLE_LIST_CTL%>"><b>Role List</b></a>
	<b>|</b>

	<!-- College -->
	<a href="<%=ORSView.COLLEGE_CTL%>"><b>Add College</b></a>
	<b>|</b>

	<a href="<%=ORSView.COLLEGE_LIST_CTL%>"><b>College List</b></a>
	<b>|</b>

	<!-- Student -->
	<a href="<%=ORSView.STUDENT_CTL%>"><b>Add Student</b></a>
	<b>|</b>

	<a href="<%=ORSView.STUDENT_LIST_CTL%>"><b>Student List</b></a>
	<b>|</b>

	<!-- Marksheet -->
	<a href="<%=ORSView.MARKSHEET_CTL%>"><b>Add Marksheet</b></a>
	<b>|</b>

	<a href="<%=ORSView.MARKSHEET_LIST_CTL%>"><b>Marksheet List</b></a>
	<b>|</b>

	<a href="<%=ORSView.MARKSHEET_MERIT_LIST_CTL%>"><b>Marksheet
			Merit List</b></a>
	<b>|</b>

	<a href="<%=ORSView.GET_MARKSHEET_CTL%>"><b>Get Marksheet</b></a>
	<b>|</b>

	<!-- Course -->
	<a href="<%=ORSView.COURSE_CTL%>"><b>Add Course</b></a>
	<b>|</b>

	<a href="<%=ORSView.COURSE_LIST_CTL%>"><b>Course List</b></a>
	<b>|</b>

	<!-- Subject -->
	<a href="<%=ORSView.SUBJECT_CTL%>"><b>Add Subject</b></a>
	<b>|</b>

	<a href="<%=ORSView.SUBJECT_LIST_CTL%>"><b>Subject List</b></a>
	<b>|</b>

	<!-- Timetable -->
	<a href="<%=ORSView.TIMETABLE_CTL%>"><b>Add Timetable</b></a>
	<b>|</b>

	<a href="<%=ORSView.TIMETABLE_LIST_CTL%>"><b>Timetable List</b></a>
	<b>|</b>

	<!-- Faculty -->
	<a href="<%=ORSView.FACULTY_CTL%>"><b>Add Faculty</b></a>
	<b>|</b>

	<a href="<%=ORSView.FACULTY_LIST_CTL%>"><b>Faculty List</b></a>
	<b>|</b>

	<!-- Geofence -->
	<a href="<%=ORSView.GEOFENCE_CTL%>"><b>Add Geofence</b></a>
	<b>|</b>

	<!-- BatchProcessing -->
	<a href="<%=ORSView.BATCHPROCESSING_CTL%>"><b>Add
			BatchProcessing</b></a>
	<b>|</b>

	<a href="<%=ORSView.BATCHPROCESSING_LIST_CTL%>"><b>BatchProcessing
			List</b></a>
	<b>|</b>

	<!-- VirtualWallet -->
	<a href="<%=ORSView.VIRTUALWALLET_CTL%>"><b>Add
			VirtualWallet</b></a>
	<b>|</b>

	<a href="<%=ORSView.VIRTUALWALLET_LIST_CTL%>"><b>VirtualWallet
			List</b></a>
	<b>|</b>
	
	<a href = "<%=ORSView.VOICEASSISTANT_CTL %>"><b>Add VoiceAssistant</b></a>
	<b>|</b>
	
	<a href = "<%=ORSView.VOICEASSISTANT_LIST_CTL %>"><b>VoiceAssistant List</b></a>
	<b>|</b>
	

	<!-- Profile -->
	<a href="<%=ORSView.MY_PROFILE_CTL%>"><b>My Profile</b></a>
	<b>|</b>

	<a href="<%=ORSView.CHANGE_PASSWORD_CTL%>"><b>Change
			Password</b></a>
	<b>|</b>

	<!-- Java Doc -->
	<a target="blank" href="<%=ORSView.JAVA_DOC%>"><b>Java
			Doc</b></a>
	<b>|</b>

	<!-- Logout -->
	<a
		href="<%=ORSView.LOGIN_CTL%>?operation=<%=LoginCtl.OP_LOG_OUT%>">
		<b>Logout</b>
	</a>

	<%
	}
	%>
	

	<%
	if (user.getRoleId() == RoleBean.STUDENT) {
	%>

	<a href="<%=ORSView.MARKSHEET_LIST_CTL%>"><b>Marksheet List</b></a>
	<b>|</b>

	<a href="<%=ORSView.GET_MARKSHEET_CTL%>"><b>Get Marksheet</b></a>
	<b>|</b>

	<a href="<%=ORSView.MY_PROFILE_CTL%>"><b>My Profile</b></a>
	<b>|</b>
	
	<a href = "<%=ORSView.VIRTUALWALLET_CTL %>"><b>Add VirtualWallet</b></a>
	<b>|</b>
	
	<a href = "<%=ORSView.VIRTUALWALLET_LIST_CTL %>"><b>VirtualWallet List</b></a>
	<b>|</b>

	<a href="<%=ORSView.CHANGE_PASSWORD_CTL%>"><b>Change
			Password</b></a>
	<b>|</b>

	<a
		href="<%=ORSView.LOGIN_CTL%>?operation=<%=LoginCtl.OP_LOG_OUT%>">
		<b>Logout</b>
	</a>

	<%
	}
	%>

	<%
	} else {
	%>

	<h3>Hi, Guest</h3>

	<a href="<%=ORSView.WELCOME_CTL%>"><b>Welcome</b></a>
	<b>|</b>

	<a href="<%=ORSView.LOGIN_CTL%>"><b>Login</b></a>

	<%
	}
	%>

	<hr>

</body>
</html>