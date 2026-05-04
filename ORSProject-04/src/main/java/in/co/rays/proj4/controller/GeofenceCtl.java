package in.co.rays.proj4.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import in.co.rays.proj4.bean.BaseBean;
import in.co.rays.proj4.bean.GeofenceBean;
import in.co.rays.proj4.exception.ApplicationException;
import in.co.rays.proj4.exception.DuplicateException;
import in.co.rays.proj4.model.GeofenceModel;
import in.co.rays.proj4.util.DataUtility;
import in.co.rays.proj4.util.DataValidator;
import in.co.rays.proj4.util.PropertyReader;
import in.co.rays.proj4.util.ServletUtility;

@WebServlet(name = "GeofenceCtl", urlPatterns = {"/ctl/GeofenceCtl"})
public class GeofenceCtl extends BaseCtl {

	@Override
	protected boolean validate(HttpServletRequest request) {
		boolean pass = true;

		if(DataValidator.isNull(request.getParameter("geoFenceCode"))) {
			request.setAttribute("geoFenceCode", PropertyReader.getValue("error.require", "GeofenceCode"));
			pass = false;
			
		}
		
		if (DataValidator.isNull(request.getParameter("locationName"))) {
			request.setAttribute("locationName", PropertyReader.getValue("error.require", "LocationName"));
			pass = false;

		}else if(!DataValidator.isName(request.getParameter("locationName"))) {
			request.setAttribute("locationName", "Invalidate is Name");
			pass = false;
		}

		if (DataValidator.isNull(request.getParameter("radius"))) {
			request.setAttribute("radius", PropertyReader.getValue("error.require", "Radius"));
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
		GeofenceBean bean = new GeofenceBean();
		bean.setId(DataUtility.getLong(request.getParameter("id")));
		bean.setGeoFenceCode(DataUtility.getStringData(request.getParameter("geoFenceCode")));
		bean.setLocationName(DataUtility.getStringData(request.getParameter("locationName")));
		bean.setRadius(DataUtility.getStringData(request.getParameter("radius")));
		bean.setStatus(DataUtility.getStringData(request.getParameter("status")));

		populateDTO(bean, request);

		return bean;

	}

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		long id = DataUtility.getLong(request.getParameter("id"));

		GeofenceModel model = new GeofenceModel();

		if (id > 0) {

			try {
				GeofenceBean bean = model.findBypk(id);
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

		GeofenceModel model = new GeofenceModel();

		long id = DataUtility.getLong(request.getParameter("id"));

		if (OP_SAVE.equalsIgnoreCase(op)) {
			GeofenceBean bean = (GeofenceBean) populateBean(request);

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

			GeofenceBean bean = (GeofenceBean) populateBean(request);

			try {
				if (id > 0) {
					model.update(bean);
				}

				ServletUtility.setBean(bean, request);
				ServletUtility.setSuccessMessage("GeoFenace update Successfull", request);

			} catch (DuplicateException e) {
				ServletUtility.setBean(bean, request);
				ServletUtility.setErrorMessage("Email already Exists", request);

			} catch (ApplicationException e) {
				e.printStackTrace();
				ServletUtility.handleException(e, request, response);
				return;
			}

		} else if (OP_CANCEL.equalsIgnoreCase(op)) {
			ServletUtility.redirect(ORSView.GEOFENCE_LIST_CTL, request, response);
			return;

		} else if (OP_RESET.equalsIgnoreCase(op)) {
			ServletUtility.redirect(ORSView.GEOFENCE_CTL, request, response);
			return;
		}

		preload(request);
		ServletUtility.forward(getView(), request, response);
	}

	@Override
	protected String getView() {
		return ORSView.GEOFENCE_VIEW;
	}

}
