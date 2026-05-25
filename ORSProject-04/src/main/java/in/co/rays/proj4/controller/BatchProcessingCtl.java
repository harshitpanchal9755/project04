package in.co.rays.proj4.controller;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import in.co.rays.proj4.bean.BaseBean;
import in.co.rays.proj4.bean.BatchProcessingBean;
import in.co.rays.proj4.exception.ApplicationException;
import in.co.rays.proj4.exception.DuplicateException;
import in.co.rays.proj4.model.BatchProcessingModel;
import in.co.rays.proj4.util.DataUtility;
import in.co.rays.proj4.util.DataValidator;
import in.co.rays.proj4.util.PropertyReader;
import in.co.rays.proj4.util.ServletUtility;

@WebServlet(name = "BatchProcessingCtl", urlPatterns = {"/ctl/BatchProcessingCtl"})
public class BatchProcessingCtl extends BaseCtl {

	@Override
	protected boolean validate(HttpServletRequest request) {
		boolean pass = true;

		if (DataValidator.isNull(request.getParameter("batchCode"))) {
			request.setAttribute("batchCode", PropertyReader.getValue("error.require", "BatchCode"));
			pass = false;
		}

		if (DataValidator.isNull(request.getParameter("batchName"))) {
			request.setAttribute("batchName", PropertyReader.getValue("error.require", "BatchName"));
			pass = false;

		} else if (!DataValidator.isName(request.getParameter("batchName"))) {
			request.setAttribute("batchName", "Invalidate is Name");
			pass = false;
		}

		if (DataValidator.isNull(request.getParameter("totalRecords"))) {
			request.setAttribute("totalRecords", PropertyReader.getValue("error.require", "TotalRecords"));
			pass = false;
		}

		if (DataValidator.isNull(request.getParameter("status"))) {
			request.setAttribute("status", PropertyReader.getValue("error.require", "Status"));
			pass = false;

		}
		return pass;
	}

	@Override
	protected BaseBean populateBean(HttpServletRequest request) {
		BatchProcessingBean bean = new BatchProcessingBean();
		bean.setId(DataUtility.getLong(request.getParameter("id")));
		bean.setBatchCode(DataUtility.getStringData(request.getParameter("batchCode")));
		bean.setBatchName(DataUtility.getStringData(request.getParameter("batchName")));
		bean.setTotalRecords(DataUtility.getInt(request.getParameter("totalRecords")));
		bean.setStatus(DataUtility.getStringData(request.getParameter("status")));

		populateDTO(bean, request);

		return bean;
	}

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		long id = DataUtility.getLong(request.getParameter("id"));

		BatchProcessingModel model = new BatchProcessingModel();

		if (id > 0) {

			try {
				BatchProcessingBean bean = model.findBypk(id);
				ServletUtility.setBean(bean, request);

			} catch (Exception e) {
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

		BatchProcessingModel model = new BatchProcessingModel();

		long id = DataUtility.getLong(request.getParameter("id"));

		if (OP_SAVE.equalsIgnoreCase(op)) {
			BatchProcessingBean bean = (BatchProcessingBean) populateBean(request);

			try {
				long pk = model.add(bean);
				ServletUtility.setBean(bean, request);
				ServletUtility.setSuccessMessage("BatchProcessing added Sucessfully", request);

			} catch (DuplicateException e) {
				ServletUtility.setBean(bean, request);
				ServletUtility.getErrorMessage("Email is already exist", request);

			} catch (ApplicationException e) {
				e.printStackTrace();
				ServletUtility.handleException(e, request, response);
				return;
			}
		}else if (OP_UPDATE.equalsIgnoreCase(op)) {

			BatchProcessingBean bean = (BatchProcessingBean) populateBean(request);

			try {
				if (id > 0) {
					model.update(bean);
				}

				ServletUtility.setBean(bean, request);
				ServletUtility.setSuccessMessage("BatchProcessing update Successfull", request);

			} catch (DuplicateException e) {
				ServletUtility.setBean(bean, request);
				ServletUtility.setErrorMessage("batchprocessing already Exists", request);

			} catch (ApplicationException e) {
				e.printStackTrace();
				ServletUtility.handleException(e, request, response);
				return;
			}

		} else if (OP_CANCEL.equalsIgnoreCase(op)) {
			ServletUtility.redirect(ORSView.BATCHPROCESSING_LIST_CTL, request, response);
			return;

		} else if (OP_RESET.equalsIgnoreCase(op)) {
			ServletUtility.redirect(ORSView.BATCHPROCESSING_CTL, request, response);
			return;
		}

		preload(request);
		ServletUtility.forward(getView(), request, response);
	}
	
	

	@Override
	protected String getView() {
		
		return ORSView.BATCHPROCESSING_VIEW;
	}

}
