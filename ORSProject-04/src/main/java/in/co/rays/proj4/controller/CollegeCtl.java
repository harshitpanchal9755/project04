package in.co.rays.proj4.controller;

import java.io.IOException;

/**
 * CollegeCtl is a controller class that handles CRUD operations
 * for College entities. It extends BaseCtl and provides functionality
 * to validate input, populate beans, and manage college records.
 * 
 * URL pattern: /ctl/CollegeCtl
 * 
 * Author: Harshit Panchal
 * Version: 1.0
 */

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import in.co.rays.proj4.bean.BaseBean;
import in.co.rays.proj4.bean.CollegeBean;
import in.co.rays.proj4.exception.ApplicationException;
import in.co.rays.proj4.exception.DuplicateException;
import in.co.rays.proj4.model.CollegeModel;
import in.co.rays.proj4.util.DataUtility;
import in.co.rays.proj4.util.DataValidator;
import in.co.rays.proj4.util.PropertyReader;
import in.co.rays.proj4.util.ServletUtility;

@WebServlet(name = "CollegeCtl", urlPatterns = { "/ctl/CollegeCtl" })
public class CollegeCtl extends BaseCtl {
	
	private static Logger log = Logger.getLogger(CollegeCtl.class);
	
	  /**
     * Validates the request parameters for College entity.
     * Checks fields like name, address, state, city, and phone number.
     * 
     * @param request HttpServletRequest containing client request data
     * @return true if validation passes, false otherwise
     */
	
	@Override
	protected boolean validate(HttpServletRequest request) {
		log.debug("CollegeCtl Method validate Started");
		
		boolean pass = true;
		
		if(DataValidator.isNull(request.getParameter("name"))) {
			request.setAttribute("name", PropertyReader.getValue("error.require", "Name"));
			pass = false;
			
		}else if(!DataValidator.isName(request.getParameter("name"))) {
			request.setAttribute("name", "Invalide Name");
			pass = false;
		}
		
		if(DataValidator.isNull(request.getParameter("address"))) {
			request.setAttribute("address", PropertyReader.getValue("error.require", "Address"));
			pass = false;
			
		}
		
		if(DataValidator.isNull(request.getParameter("state"))) {
			request.setAttribute("state", PropertyReader.getValue("error.require", "State"));
			pass = false;
		}
		
		if(DataValidator.isNull(request.getParameter("city"))) {
			request.setAttribute("city", PropertyReader.getValue("error.require", "City"));
			pass = false;
		}
		
		if(DataValidator.isNull(request.getParameter("phoneno"))) {
			request.setAttribute("phoneno", PropertyReader.getValue("error.require", "phoneno"));
			pass = false;
			
		}else if(!DataValidator.isPhoneLength(request.getParameter("phoneno"))) {
			request.setAttribute("phoneno", "Phone no. must have 10 digit");
			pass = false;
			
		}else if(!DataValidator.isPhoneNo(request.getParameter("phoneno"))) {
			request.setAttribute("phoneno", "Invalid Phoneno");
			pass = false;
			
		}
		   log.debug("CollegeCtl Method validate ended");
		return pass;
		
		
	}
	
	  /**
     * Populates a CollegeBean object from the request parameters.
     * 
     * @param request HttpServletRequest containing form data
     * @return populated CollegeBean
     */
	@Override
	protected BaseBean populateBean(HttpServletRequest request) {
		CollegeBean bean = new CollegeBean();
		bean.setId(DataUtility.getLong(request.getParameter("id")));
		bean.setName(DataUtility.getString(request.getParameter("name")));
		bean.setAddress(DataUtility.getString(request.getParameter("address")));
		bean.setState(DataUtility.getString(request.getParameter("state")));
		bean.setCity(DataUtility.getString(request.getParameter("city")));
		bean.setPhoneno(DataUtility.getString(request.getParameter("phoneno")));
		
		populateDTO(bean, request);
		log.debug("CollegeCtl Method populatebean ended");
		return bean;
		
	}
	
	   /**
     * Handles GET requests. Retrieves the CollegeBean if id is provided
     * and forwards to the view.
     * 
     * @param request  HttpServletRequest
     * @param response HttpServletResponse
     * @throws ServletException if a servlet error occurs
     * @throws IOException      if an I/O error occurs
     */
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		log.debug("CollegeCtl Method populatebean ended");
		System.out.println("in CollegeCtl doget method");
		long id = DataUtility.getLong(request.getParameter("id"));

		CollegeModel model = new CollegeModel();

		if (id > 0) {
			try {
				CollegeBean bean = model.findBypk(id);
				ServletUtility.setBean(bean, request);
			} catch (ApplicationException e) {
				e.printStackTrace();
				ServletUtility.handleException(e, request, response);
				return;
			}
		}
		ServletUtility.forward(getView(), request, response);
		  log.debug("CollegeCtl Method doEnd ended");
	}
	
	  /**
     * Handles POST requests for adding, updating, canceling, or resetting
     * College records.
     * 
     * @param request  HttpServletRequest
     * @param response HttpServletResponse
     * @throws ServletException if a servlet error occurs
     * @throws IOException      if an I/O error occurs
     */
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		log.debug("CollegeCtl Method doPost Started");
		
		System.out.println("in College dopost method");
		String op = DataUtility.getString(request.getParameter("operation"));

		CollegeModel model = new CollegeModel();

		long id = DataUtility.getLong(request.getParameter("id"));

		if (OP_SAVE.equalsIgnoreCase(op)) {
			CollegeBean bean = (CollegeBean) populateBean(request);
			try {
				long pk = model.add(bean);
				ServletUtility.setBean(bean, request);
				ServletUtility.setSuccessMessage("Data is Successfully saved", request);
			} catch (DuplicateException e) {
				ServletUtility.setBean(bean, request);
				ServletUtility.setErrorMessage("College Name already exists", request);
			} catch (ApplicationException e) {
				e.printStackTrace();
				ServletUtility.handleException(e, request, response);
				return;
			}
		} else if (OP_UPDATE.equalsIgnoreCase(op)) {
			CollegeBean bean = (CollegeBean) populateBean(request);
			try {
				if (id > 0) {
					model.update(bean);
				}
				ServletUtility.setBean(bean, request);
				ServletUtility.setSuccessMessage("Data is Successfully updated", request);
			} catch (DuplicateException e) {
				ServletUtility.setBean(bean, request);
				ServletUtility.setErrorMessage("College Name already exists", request);
			} catch (ApplicationException e) {
				e.printStackTrace();
				ServletUtility.handleException(e, request, response);
				return;
			}
		} else if (OP_CANCEL.equalsIgnoreCase(op)) {
			ServletUtility.redirect(ORSView.COLLEGE_LIST_CTL, request, response);
			return;
		} else if (OP_RESET.equalsIgnoreCase(op)) {
			ServletUtility.redirect(ORSView.COLLEGE_CTL, request, response);
			return;
		}
		ServletUtility.forward(getView(), request, response);
		  log.debug("CollegeCtl Method doPost ended");
	}
	
	   /**
     * Returns the view page for the College controller.
     * 
     * @return view page path as a String
     */

	@Override
	protected String getView() {
		return ORSView.COLLEGE_VIEW;
	} 
	

}
