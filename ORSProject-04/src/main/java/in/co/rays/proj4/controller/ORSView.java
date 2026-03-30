package in.co.rays.proj4.controller;

public interface ORSView { /// ors view is loosecoupling provide in class interface
	
	public String APP_CONTEXT = "/ORSProject-04";
	public String PAGE_FOLDER = "/jsp";
	
	public String WELCOME_VIEW = PAGE_FOLDER + "/WelcomeView.jsp";
	public String WELCOME_CTL = APP_CONTEXT + "/WelcomeCtl";
	
	public String LOGIN_VIEW = PAGE_FOLDER + "/Login.jsp";
	public String LOGIN_CTL = APP_CONTEXT + "/LoginCtl"; 
	
	public String GET_MARKSHEET_VIEW = PAGE_FOLDER + "/GetMarksheet.jsp";
	public String GET_MARKSHEET_CTL = APP_CONTEXT + "/GetMarksheetCtl";
	
	public String MARKSHEET_MERIT_LIST_VIEW = PAGE_FOLDER + "/MarksheetMeritList.jsp";
	public String MARKSHEET_MERIT_LIST_CTL = APP_CONTEXT + "/MarsheetMeritListCtl";
	
	public String USER_REGISTRATION_VIEW = PAGE_FOLDER + "/UserRegistration.jsp";
	public String USER_REGISTRATION_CTL = APP_CONTEXT + "/UserRegistrationCtl";
	
	public String USER_LIST_VIEW = PAGE_FOLDER + "/UserList.jsp";
	public String USER_LIST_CTL = APP_CONTEXT + "/UserListCtl";
	
	public String USER_VIEW = PAGE_FOLDER + "/User.jsp";
	public String USER_CTL = APP_CONTEXT + "/UserCtl";
	
	public String ROLE_VIEW = PAGE_FOLDER + "/Role.jsp";
	public String ROLE_CTL = APP_CONTEXT + "RoleCtl";
	
	public String ROLE_LIST_VIEW = PAGE_FOLDER + "/RoleList.jsp";
	public String ROLE_LIST_CTL = APP_CONTEXT + "/RoleListCtl";
	
	public String STUDENT_VIEW = PAGE_FOLDER + "/Student.jsp";
	public String STUDENT_CTL = APP_CONTEXT + "/StudentCtl";
	
	public String STUDENT_LIST_VIEW = PAGE_FOLDER + "/StudentList.jsp";
    public String STUDENT_LIST_CTL = APP_CONTEXT + "/StudentListCtl";
    
    public String MARKSHEET_VIEW = PAGE_FOLDER + "/Marksheet.jsp";
    public String MARKSHEET_CTL = APP_CONTEXT + "/MarksheetCtl";
    
    public String MARKSHEET_LIST_VIEW = PAGE_FOLDER + "/MarksheetList.jsp";
    public String MARKSHEET_LIST_CTL = APP_CONTEXT + "/MarksheetListCtl";
    
    public String COURSE_VIEW = PAGE_FOLDER + "/Course.jsp";
    public String COURSE_CTL = APP_CONTEXT + "/CourseCtl";
    
    public String COURSE_LIST_VIEW = PAGE_FOLDER + "/CourseList.jsp";
    public String COURSE_LIST_CTL = APP_CONTEXT + "/CourseListCtl";
    
    public String SUBJECT_VIEW = PAGE_FOLDER + "/Subject.jsp";
    public String SUBJECT_CTL = APP_CONTEXT + "/SubjectCtl";
    
    public String SUBJECT_LIST_VIEW = PAGE_FOLDER + "/SubjectList.jsp";
    public String SUBJECT_LIST_CTL = APP_CONTEXT + "/SubjectListCtl";
    
    public String TIMETABLE_VIEW = PAGE_FOLDER + "/TimetableView.jsp";
	public String TIMETABLE_CTL = APP_CONTEXT + "/TimetableCtl";
    
    public String TIMETABLE_LIST_VIEW = PAGE_FOLDER + "/TimetableListView.jsp";
	public String TIMETABLE_LIST_CTL = APP_CONTEXT + "/TimetableListCtl";

	public String FACULTY_VIEW = PAGE_FOLDER + "/FacultyView.jsp";
	public String FACULTY_CTL = APP_CONTEXT + "/FacultyCtl";

	public String FACULTY_LIST_VIEW = PAGE_FOLDER + "/FacultyListView.jsp";
	public String FACULTY_LIST_CTL = APP_CONTEXT + "/FacultyListCtl";

	public String FORGET_PASSWORD_VIEW = PAGE_FOLDER + "/ForgetPasswordView.jsp";
	public String FORGET_PASSWORD_CTL = APP_CONTEXT + "/ForgetPasswordCtl";

	public String MY_PROFILE_VIEW = PAGE_FOLDER + "/MyProfileView.jsp";
	public String MY_PROFILE_CTL = APP_CONTEXT + "/MyProfileCtl";

	public String CHANGE_PASSWORD_VIEW = PAGE_FOLDER + "/ChangePasswordView.jsp";
	public String CHANGE_PASSWORD_CTL = APP_CONTEXT + "/ChangePasswordCtl";

	public String ERROR_VIEW = PAGE_FOLDER + "/ErrorView.jsp";
	public String ERROR_CTL = APP_CONTEXT + "/ErrorCtl";
    
    
}
