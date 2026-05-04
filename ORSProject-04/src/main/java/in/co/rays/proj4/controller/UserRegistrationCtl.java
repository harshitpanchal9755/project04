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
import in.co.rays.proj4.util.PropertyReader;
import in.co.rays.proj4.util.ServletUtility;

@WebServlet("/UserRegistrationCtl")
public class UserRegistrationCtl extends BaseCtl {

	public static final String OP_SIGN_UP = "Sign Up";

	@Override
	protected boolean validate(HttpServletRequest request) {
		boolean pass = true;

		if (DataValidator.isNull(request.getParameter("firstName"))) {
			request.setAttribute("firstName", PropertyReader.getValue("error.require", "First Name"));
			pass = false;

		} else if (!DataValidator.isName(request.getParameter("firstName"))) {
			request.setAttribute("firstName",  "Invalid First Name");
			pass = false;
		}

		if (DataValidator.isNull(request.getParameter("lastName"))) {
			request.setAttribute("lastName", PropertyReader.getValue("error.require", "Last Name"));
			pass = false;

		} else if (!DataValidator.isName(request.getParameter("lastName"))) {
			request.setAttribute("lastName",  "Invalid Last Name");
			pass = false;
		}

		if (DataValidator.isNull(request.getParameter("login"))) {
			request.setAttribute("login",  PropertyReader.getValue("error.require", "Login Id"));
			pass = false;

		} else if (!DataValidator.isEmail(request.getParameter("login"))) {
			request.setAttribute("login",  "Invalid Login");
			pass = false;
		}

		if (DataValidator.isNull(request.getParameter("password"))) {
			request.setAttribute("password",  PropertyReader.getValue("error.require",  "Password"));
			pass = false;

		} else if (!DataValidator.isPasswordLength(request.getParameter("password"))) {
			request.setAttribute("password", "Password should be 8-12 char");
			pass = false;
		}

		else if ((!DataValidator.isPassword(request.getParameter("password")))) {
			request.setAttribute("password", "must be contain uppercase and lowercase and the special character");
			pass = false;

		}

		if (DataValidator.isNull(request.getParameter("confirmPassword"))) {
			request.setAttribute("confirmPassword",  PropertyReader.getValue("error.require",  "Confirm Password"));
			pass = false;

		}

		if (DataValidator.isNull(request.getParameter("gender"))) {
			request.setAttribute("gender", PropertyReader.getValue("error.require",  "Gender"));
			pass = false;
		}

		if (DataValidator.isNull(request.getParameter("dob"))) {
			request.setAttribute("dob",  PropertyReader.getValue("error.require", "Date Of Birth"));
			pass = false;

		} else if (!DataValidator.isDate(request.getParameter("dob"))) {
			request.setAttribute("dob",  "Invalid Date Of Birth");
			pass = false;
		}

		if (!request.getParameter("password").equals(request.getParameter("confirmPassword"))) {
			request.setAttribute("confirmpassword", "password and confirm password is same");
			pass = false;
		}

		if (DataValidator.isNull(request.getParameter("mobileNo"))) {
			request.setAttribute("mobileNo",  PropertyReader.getValue("error.require",  "Mobile No"));
			pass = false;

		} else if (!DataValidator.isPhoneLength(request.getParameter("mobileNo"))) {
			request.setAttribute("mobileNo", "mobileNo is not 10 digit");
			pass = false;

		} else if (!DataValidator.isPhoneNo(request.getParameter("mobileNo"))) {
			request.setAttribute("mobileNo",  "Invalid Mobile No");
			pass = false;
		}

		return pass;

	}

	@Override
	protected BaseBean populateBean(HttpServletRequest request) {
		UserBean bean = new UserBean();
		bean.setFirstName(DataUtility.getString(request.getParameter("firstName")));
		bean.setLastName(DataUtility.getString(request.getParameter("lastName")));
		bean.setLogin(DataUtility.getString(request.getParameter("login")));
		bean.setPassword(DataUtility.getString(request.getParameter("password")));
		bean.setConfirmPassword(DataUtility.getString(request.getParameter("confirmPassword")));
		bean.setGender(DataUtility.getString(request.getParameter("gender")));
		bean.setDob(DataUtility.getDate(request.getParameter("dob")));
		bean.setMobileNo(DataUtility.getString(request.getParameter("mobileNo")));
		bean.setRoleId(RoleBean.STUDENT);

		populateDTO(bean, request);

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
				ServletUtility.setSuccessMessage("Registration successfull", req);
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
