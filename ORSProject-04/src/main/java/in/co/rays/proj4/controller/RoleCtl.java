package in.co.rays.proj4.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import in.co.rays.proj4.bean.BaseBean;
import in.co.rays.proj4.bean.RoleBean;
import in.co.rays.proj4.exception.ApplicationException;
import in.co.rays.proj4.exception.DuplicateException;
import in.co.rays.proj4.model.RoleModel;
import in.co.rays.proj4.util.DataUtility;
import in.co.rays.proj4.util.DataValidator;
import in.co.rays.proj4.util.ServletUtility;

@WebServlet(name = "RoleCtl", urlPatterns = {"/ctl/RoleCtl"})
public class RoleCtl extends BaseCtl {

	@Override
	protected boolean validate(HttpServletRequest request) {

		boolean pass = true;

		if (DataValidator.isNull(request.getParameter("name"))) {
			request.setAttribute("name", "Name is required");
			pass = false;

		} else if (!DataValidator.isName(request.getParameter("name"))) {
			request.setAttribute("name", "Invalid Name");
			pass = false;

		}

		if (DataValidator.isNull(request.getParameter("description"))) {
			request.setAttribute("description", "description is required");
			pass = false;

		}
		return pass;

	 }

	@Override
	protected BaseBean populateBean(HttpServletRequest request) {
		RoleBean bean = new RoleBean();

		bean.setId(DataUtility.getLong(request.getParameter("id")));
		bean.setName(DataUtility.getString(request.getParameter("name")));
		bean.setDescription(DataUtility.getString(request.getParameter("description")));

		populateDTO(bean, request);
		return bean;
	}

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException {
		long id = DataUtility.getLong(request.getParameter("id"));

		RoleModel model = new RoleModel();

		if (id > 0) {

			try {
				RoleBean bean = model.findBypk(id);
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

		String op = DataUtility.getString(request.getParameter("operation"));

		RoleModel model = new RoleModel();

		long id = DataUtility.getLong(request.getParameter("id"));

		if (OP_SAVE.equalsIgnoreCase(op)) {
			RoleBean bean = (RoleBean) populateBean(request);

			try {
				long pk = model.add(bean);
				ServletUtility.setBean(bean, request);
				ServletUtility.setSuccessMessage("Data is successfully saved", request);

			} catch (DuplicateException e) {
				ServletUtility.setBean(bean, request);
				ServletUtility.setErrorMessage("Role already exist", request);

			} catch (ApplicationException e) {
				e.printStackTrace();
				return;

			}

		} else if (OP_UPDATE.equalsIgnoreCase(op)) {
			RoleBean bean = (RoleBean) populateBean(request);

			try {
				if (id > 0) {
					model.update(bean);

				}
				ServletUtility.setBean(bean, request);
				ServletUtility.setSuccessMessage("Data is updated Successfully", request);

			} catch (DuplicateException e) {
				ServletUtility.setBean(bean, request);
				ServletUtility.setErrorMessage("Role already exist", request);
				e.printStackTrace();

			} catch (ApplicationException e) {
				e.printStackTrace();
				return;
			}

		} else if (OP_CANCEL.equalsIgnoreCase(op)) {
			ServletUtility.redirect(ORSView.ROLE_LIST_CTL, request, response);
			return;

		} else if (OP_RESET.equalsIgnoreCase(op)) {
			ServletUtility.redirect(ORSView.ROLE_CTL, request, response);
			return;
		}
		ServletUtility.forward(getView(), request, response);
	}

	@Override
	protected String getView() {
		return ORSView.ROLE_VIEW;
	}

}
