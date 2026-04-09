package in.co.rays.proj4.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import in.co.rays.proj4.bean.BaseBean;
import in.co.rays.proj4.bean.UserBean;
import in.co.rays.proj4.util.DataUtility;
import in.co.rays.proj4.util.DataValidator;
import in.co.rays.proj4.util.ServletUtility;

public abstract class BaseCtl extends HttpServlet {

	public static final String OP_SAVE = "Save";
	public static final String OP_UPDATE = "Update";
	public static final String OP_CANCEL = "Cancel";
	public static final String OP_DELETE = "Delete";
	public static final String OP_LIST = "List";
	public static final String OP_SEARCH = "Search";
	public static final String OP_VIEW = "View";
	public static final String OP_NEXT = "Next";
	public static final String OP_PREVIOUS = "Previous";
	public static final String OP_NEW = "new";
	public static final String OP_GO = "Go";
	public static final String OP_BACK = "Back";
	public static final String OP_RESET = "Reset";
	public static final String OP_LOG_OUT = "Logout";

	public static final String MSG_SUCCESS = "success";
	public static final String MSG_ERROR = "error";

	protected boolean validate(HttpServletRequest request) {
		return true;

	}

	protected void preload(HttpServletRequest request) {

	}

	protected BaseBean populateBean(HttpServletRequest request) {
		return null;

	}

	protected BaseBean populateDTO(BaseBean dto, HttpServletRequest request) {

		String createdBy = request.getParameter("createdBy");
		String modifiedBy = null;

		UserBean userBean = (UserBean) request.getSession().getAttribute("user");

		if (userBean == null) {
			createdBy = "root";
			modifiedBy = "root";

		} else {
			modifiedBy = userBean.getLogin();

			if ("null".equalsIgnoreCase(createdBy) || DataValidator.isNull(createdBy)) {

				createdBy = modifiedBy;
			}
		}

		dto.setCreatedBy(createdBy);
		dto.setModifiedBy(modifiedBy);

		long cdt = DataUtility.getLong(request.getParameter("createdDatetime"));

		if (cdt > 0) {
			dto.setCreatedDateTime(DataUtility.getTimestamp(cdt));
		} else {
			dto.setCreatedDateTime(DataUtility.getCurrentTimestamp());
		}

		dto.setModifiedDateTime(DataUtility.getCurrentTimestamp());

		return dto;
	}

	@Override
	protected void service(HttpServletRequest request, HttpServletResponse response) // service method call hoti ha
			throws ServletException, IOException {

		preload(request);

		String op = DataUtility.getString(request.getParameter("operation"));

		if (DataValidator.isNotNull(op) && !op.equalsIgnoreCase(OP_CANCEL) && !op.equalsIgnoreCase(OP_RESET)
				&& !op.equalsIgnoreCase(OP_NEW) && !op.equalsIgnoreCase(OP_DELETE)) {

			if (validate(request) == false) {
				BaseBean bean = (BaseBean) populateBean(request);
				ServletUtility.setBean(bean, request);
				ServletUtility.forward(getView(), request, response);
				return;
			}
		}

		super.service(request, response);
	}

	protected abstract String getView();

}
