	package in.co.rays.proj4.controller;
	
	import java.io.IOException;
	
	import javax.servlet.ServletException;
	import javax.servlet.annotation.WebServlet;
	import javax.servlet.http.HttpServletRequest;
	import javax.servlet.http.HttpServletResponse;
	import javax.xml.crypto.Data;
	
	import in.co.rays.proj4.bean.BaseBean;
	import in.co.rays.proj4.bean.RoleBean;
	import in.co.rays.proj4.bean.UserBean;
	import in.co.rays.proj4.exception.ApplicationException;
	import in.co.rays.proj4.exception.DuplicateException;
	import in.co.rays.proj4.model.UserModel;
	import in.co.rays.proj4.util.DataUtility;
	import in.co.rays.proj4.util.DataValidator;
	import in.co.rays.proj4.util.ServletUtility;
	
	@WebServlet("/UserRegistrationCtl")
	public class UserRegistrationCtl extends BaseCtl{
		
		public static final String OP_SIGN_UP = "Sign Up";
		
		protected boolean validate(HttpServletRequest req) {
			boolean pass = true;
			
			if(DataValidator.isNull(req.getParameter("firstName"))) {
				req.setAttribute("firstName", "firstName is required");
				pass = false;
				
			}else if(!DataValidator.isName(req.getParameter("firstName"))) {
				req.setAttribute("firstName", "firstName is invalid");
				pass = false;
			}
		
			if(DataValidator.isNull(req.getParameter("lastName"))) {
				req.setAttribute("lastName", "lastname is required" );
				pass = false;
				
			}else if(!DataValidator.isName(req.getParameter("lastName"))) {
				req.setAttribute("lastName", "lastname is invalid");
				pass = false;
			}
			
			if(DataValidator.isNull(req.getParameter("login"))) {
				req.setAttribute("login", "login is required");
				pass = false;
				
			}else if(!DataValidator.isEmail(req.getParameter("login"))) {
				req.setAttribute("login", "Login is invalid");
				pass = false;
			}
			
			if(DataValidator.isNull(req.getParameter("password"))) {
				req.setAttribute("password", "password is required");
				pass = false;
				
			}else if(!DataValidator.isPasswordLength(req.getParameter("password"))) {
				req.setAttribute("password", "Password should be 8-12 char");
				pass = false;
			}
			
			else if((!DataValidator.isPassword(req.getParameter("password")))) {
				req.setAttribute("password", "must be contain uppercase and lowercase and the special character");
				pass = false;
				
			}
			
			if(DataValidator.isNull(req.getParameter("confirmPassword"))) {
				req.setAttribute("confirmPassword", "confirmpassword is required");
				pass = false;
				
			}
			
			if(DataValidator.isNull(req.getParameter("gender"))) {
				req.setAttribute("gender", "gender is required");
				pass = false;
			}
			
			if(DataValidator.isNull(req.getParameter("dob"))) {
				req.setAttribute("dob", "dob is required");
				pass = false;
				
			}else if(!DataValidator.isDate(req.getParameter("dob"))) {
				req.setAttribute("dob", "invalide date of birth");
				pass = false;
			}
			
			if(!req.getParameter("password").equals(req.getParameter("confirmPassword"))) {
				req.setAttribute("confirmpassword", "password and confirmpassword is same");
				pass = false;
			}
			
			if(DataValidator.isNull(req.getParameter("mobileNo"))) {
				req.setAttribute("mobileNo", "mobileNo is required");
				pass = false;
				
			}else if(!DataValidator.isPhoneLength(req.getParameter("mobileNo"))) {
				req.setAttribute("mobileNo", "mobileNo is not 10 digit");
				pass = false;
				
			} else if(!DataValidator.isPhoneNo(req.getParameter("mobileNo"))) {
				req.setAttribute("mobileNo", "invalid is mobileNo");
				pass = false;
			}
			
			return pass;
			
		}
		
		protected BaseBean populateBean(HttpServletRequest req) {
			UserBean bean = new UserBean();
			bean.setFirstName(DataUtility.getString(req.getParameter("firstName")));
			bean.setLastName(DataUtility.getString(req.getParameter("lastName")));
			bean.setLogin(DataUtility.getString(req.getParameter("login")));
			bean.setPassword(DataUtility.getString(req.getParameter("password")));
			bean.setConfirmPassword(DataUtility.getString(req.getParameter("confirmPassword")));
			bean.setGender(DataUtility.getString(req.getParameter("gender")));
			bean.setDob(DataUtility.getDate(req.getParameter("dob")));
			bean.setMobileNo(DataUtility.getString(req.getParameter("mobileNo")));
			bean.setRoleid(RoleBean.STUDENT);
			
			populateDTO(bean, req);
			
			return bean;
			
		}
		
		@Override
		protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
			System.out.println("in UserRegistrationCtl doget method");
			ServletUtility.forward(getView(), req, resp);
		}
		
		@Override
		protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
			System.out.println("in UserRegistrationCtl doPost method");
	
			String op = DataUtility.getString(req.getParameter("operation"));
	
			UserModel model = new UserModel();
	
			if (OP_SIGN_UP.equalsIgnoreCase(op)) {
				UserBean bean = (UserBean) populateBean(req);
	
				try {
					long pk = model.add(bean);
					ServletUtility.setBean(bean, req);
					ServletUtility.setSuccessMessage("Registration successful!", req);
				} catch (DuplicateException e) {
					ServletUtility.setBean(bean, req);
					ServletUtility.setErrorMessage("Login id already exists", req);
				} catch (ApplicationException e) {
					e.printStackTrace();
					return;
				}
				ServletUtility.forward(getView(), req, resp);
			} else if (OP_RESET.equalsIgnoreCase(op)) {
				ServletUtility.redirect(ORSView.USER_REGISTRATION_CTL, req, resp);
				return;
			}
	
		}
	
		
	
		@Override
		protected String getView() {
			return ORSView.USER_REGISTRATION_VIEW;
		}
	
	}
