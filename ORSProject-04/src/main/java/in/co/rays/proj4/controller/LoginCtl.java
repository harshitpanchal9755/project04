package in.co.rays.proj4.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import in.co.rays.proj4.bean.BaseBean;
import in.co.rays.proj4.bean.RoleBean;
import in.co.rays.proj4.bean.UserBean;
import in.co.rays.proj4.exception.ApplicationException;
import in.co.rays.proj4.model.RoleModel;
import in.co.rays.proj4.model.UserModel;
import in.co.rays.proj4.util.DataUtility;
import in.co.rays.proj4.util.DataValidator;
import in.co.rays.proj4.util.ServletUtility;
@WebServlet("/LoginCtl")
public class LoginCtl extends BaseCtl{
	public static  final String OP_SIGN_IN = "SignIn";
	public static final String OP_SIGN_UP = "SignUp";
	public static final String OP_LOG_OUT = "LogOut";
	
	protected boolean validate(HttpServletRequest req) {
		
		boolean pass = true;
		String op = req.getParameter("operation");
		
		if(OP_SIGN_IN.equals(op) || OP_LOG_OUT.equals(op)) {
			return pass;
		}
		
	if(DataValidator.isNull(req.getParameter("login"))) {
		req.setAttribute("login", "login is required");
		pass = false;
		
	}
	
	if(DataValidator.isNull(req.getParameter("password"))) {
		req.setAttribute("password", "password is requird");
		pass = false;
		
	}else if(!DataValidator.isPasswordLength(req.getParameter("password"))) {
		req.setAttribute("password", "should  be 8-12 char");
		pass = false;
		
	}else if(!DataValidator.isPassword(req.getParameter("password"))) {
		req.setAttribute("password", "must contain password to be uppercae and lowercase");
		pass = false;
	}
		
		return pass;
		
	}
	
	protected BaseBean populatedBean(HttpServletRequest req) {
		
		UserBean bean = new UserBean();
	
		bean.setLogin(DataUtility.getString(req.getParameter("login")));
		bean.setPassword(DataUtility.getString(req.getParameter("password")));
		return bean;
		
	}
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String op = DataUtility.getString(req.getParameter("operation"));
		
		if(OP_LOG_OUT.equals(op)) {
			HttpSession session = req.getSession();
			session.invalidate();
			session = req.getSession(true);
			session.setAttribute("success", "logout is successfull");
		}
		ServletUtility.forward(getView(), req, resp);
	}
	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		
		String op = DataUtility.getString(req.getParameter("operation"));
		
		UserModel model = new UserModel();
		RoleModel role = new RoleModel();
		HttpSession session = req.getSession();
		
		if(OP_SIGN_IN.equalsIgnoreCase(op)) {
			UserBean bean = (UserBean) populatedBean(req);
			
			try {
				bean = model.authenticate(bean.getLogin(), bean.getPassword());
				
				if(bean != null) {
					session.setAttribute("user", bean);
					RoleBean rolebean = role.findBypk(bean.getRoleid());
					
					if(rolebean != null) {
						session.setAttribute("role", rolebean.getName());
					}
					ServletUtility.setSuccessMessage("login successfull id ", req);
					ServletUtility.redirect(ORSView.WELCOME_CTL, req, resp);
					return;
				}else {
					ServletUtility.setBean(bean, req);
					ServletUtility.setErrorMessage("Invalid LoginId And Password", req);
				}
			}catch (ApplicationException e) {
				e.printStackTrace();
				return;
			}
		} else if (OP_SIGN_UP.equalsIgnoreCase(op)) {
			ServletUtility.redirect(ORSView.USER_REGISTRATION_CTL, req, resp);
			return;
		}
		ServletUtility.forward(getView(), req, resp);
	}
		

	@Override
	protected String getView() {
		// TODO Auto-generated method stub
		return ORSView.LOGIN_VIEW;
	}

}
