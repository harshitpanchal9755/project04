package in.co.rays.proj4.controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import in.co.rays.proj4.bean.BaseBean;
import in.co.rays.proj4.bean.CollegeBean;
import in.co.rays.proj4.bean.StudentBean;
import in.co.rays.proj4.exception.ApplicationException;
import in.co.rays.proj4.exception.DuplicateException;
import in.co.rays.proj4.model.CollegeModel;
import in.co.rays.proj4.model.StudentModel;
import in.co.rays.proj4.util.DataUtility;
import in.co.rays.proj4.util.DataValidator;
import in.co.rays.proj4.util.PropertyReader;
import in.co.rays.proj4.util.ServletUtility;

@WebServlet(name = "StudentCtl", urlPatterns = { "/ctl/StudentCtl" })
public class StudentCtl extends BaseCtl {

	@Override
	protected void preload(HttpServletRequest request) {
		CollegeModel collegemodel = new CollegeModel();

		try {

			List<CollegeBean> collegeList = collegemodel.list();
			request.setAttribute("collegeList", collegeList);

		} catch (ApplicationException e) {
			e.printStackTrace();
		}

	}

	@Override
	protected boolean validate(HttpServletRequest request) {
		boolean pass = true;

		if (DataValidator.isNull(request.getParameter("firstname"))) {
			request.setAttribute("firstname", PropertyReader.getValue("error.require", "First Name"));
			pass = false;

		} else if (!DataValidator.isName(request.getParameter("firstname"))) {
			request.setAttribute("firstname", "Invalid First Name");
			pass = false;
		}

		if (DataValidator.isNull(request.getParameter("lastname"))) {
			request.setAttribute("lastname", PropertyReader.getValue("error.require", "Last Name"));
			pass = false;

		} else if (!DataValidator.isName(request.getParameter("lastname"))) {
			request.setAttribute("lastname", "Invalid Last Name");
			pass = false;

		}

		if (DataValidator.isNull(request.getParameter("mobileno"))) {
			request.setAttribute("mobileno", PropertyReader.getValue("error.require", "Mobile No"));
			pass = false;

		} else if (!DataValidator.isPhoneLength(request.getParameter("mobileno"))) {
			request.setAttribute("mobileno", "Mobile No must have 10 digits");
			pass = false;
		} else if (!DataValidator.isPhoneNo(request.getParameter("mobileno"))) {
			request.setAttribute("mobileno", "Invalid Mobile No");
			pass = false;
		}

		if (DataValidator.isNull(request.getParameter("gender"))) {
			request.setAttribute("gender", PropertyReader.getValue("error.require", "Gender"));
			pass = false;
		}
		if (DataValidator.isNull(request.getParameter("email"))) {
			request.setAttribute("email", PropertyReader.getValue("error.require", "Email Id"));
			pass = false;
		} else if (!DataValidator.isEmail(request.getParameter("email"))) {
			request.setAttribute("email", PropertyReader.getValue("error.email", "Email Id"));
			pass = false;
		}

		if (DataValidator.isNull(request.getParameter("collegeId"))) {
			request.setAttribute("collegeId", PropertyReader.getValue("error.require", "College Name"));
			pass = false;
		}

		if (DataValidator.isNull(request.getParameter("dob"))) {
			request.setAttribute("dob", PropertyReader.getValue("error.require", "Date of Birth"));
			pass = false;
		} else if (!DataValidator.isDate(request.getParameter("dob"))) {
			request.setAttribute("dob", PropertyReader.getValue("error.date", "Date of Birth"));
			pass = false;
		}

		return pass;

	}

	@Override
	protected BaseBean populateBean(HttpServletRequest request) {

		StudentBean bean = new StudentBean();

		bean.setId(DataUtility.getLong(request.getParameter("id")));
		bean.setFirstName(DataUtility.getString(request.getParameter("firstname")));
		bean.setLastName(DataUtility.getString(request.getParameter("lastname")));
		bean.setDob(DataUtility.getDate(request.getParameter("dob")));
		bean.setGender(DataUtility.getString(request.getParameter("gender")));
		bean.setMobileno(DataUtility.getString(request.getParameter("mobileno")));
		bean.setEmail(DataUtility.getString(request.getParameter("email")));
		bean.setCollegeId(DataUtility.getLong(request.getParameter("collegeId")));
		
//		populateDTO(bean, request);

		return bean;
	}

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		long id = DataUtility.getLong(request.getParameter("id"));

		StudentModel model = new StudentModel();

		if (id > 0) {
			try {
				StudentBean bean = model.findByPk(id);
				ServletUtility.setBean(bean, request);
			} catch (ApplicationException e) {
				e.printStackTrace();
				return;
			}

		}
		ServletUtility.forward(getView(), request, response);
	}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String op = request.getParameter("operation");

		StudentModel model = new StudentModel();

		long id = DataUtility.getLong(request.getParameter("id"));

		if (OP_SAVE.equalsIgnoreCase(op)) {
			StudentBean bean = (StudentBean) populateBean(request);

			try {
				long pk = model.add(bean);
				ServletUtility.setBean(bean, request);
				ServletUtility.setSuccessMessage("Student added Successfull", request);

			} catch (DuplicateException e) {
				ServletUtility.setBean(bean, request);
				ServletUtility.setErrorMessage("Email already Exists", request);

			} catch (ApplicationException e) {
				e.printStackTrace();
				ServletUtility.handleException(e, request, response);
				return;
			}
		} else if (OP_UPDATE.equalsIgnoreCase(op)) {
			
			StudentBean bean = (StudentBean) populateBean(request);

			try {
				if (id > 0) {
					model.update(bean);
				}

				ServletUtility.setBean(bean, request);
				ServletUtility.setSuccessMessage("Student update Successfull", request);

			} catch (DuplicateException e) {
				ServletUtility.setBean(bean, request);
				ServletUtility.setErrorMessage("Email already Exists", request);

			} catch (ApplicationException e) {
				e.printStackTrace();
				ServletUtility.handleException(e, request, response);
				return;
			}

		} else if (OP_CANCEL.equalsIgnoreCase(op)) {
			ServletUtility.redirect(ORSView.STUDENT_LIST_CTL, request, response);
			return;

		} else if (OP_RESET.equalsIgnoreCase(op)) {
			ServletUtility.redirect(ORSView.STUDENT_CTL, request, response);
			return;
		}
		
		preload(request);
		ServletUtility.forward(getView(), request, response);
	}

	@Override
	protected String getView() {
		return ORSView.STUDENT_VIEW;
	}

}
