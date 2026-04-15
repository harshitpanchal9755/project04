package in.co.rays.proj4.controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import in.co.rays.proj4.bean.BaseBean;
import in.co.rays.proj4.bean.CourseBean;
import in.co.rays.proj4.bean.SubjectBean;
import in.co.rays.proj4.exception.ApplicationException;
import in.co.rays.proj4.exception.DuplicateException;
import in.co.rays.proj4.model.CourseModel;
import in.co.rays.proj4.model.SubjectModel;
import in.co.rays.proj4.util.DataUtility;
import in.co.rays.proj4.util.DataValidator;
import in.co.rays.proj4.util.PropertyReader;
import in.co.rays.proj4.util.ServletUtility;

@WebServlet(name = "SubjectCtl", urlPatterns = "/ctl/SubjectCtl")
public class SubjectCtl extends BaseCtl{
	
	@Override
	protected void preload(HttpServletRequest request) {
		CourseModel coursemodel = new CourseModel();
		
		try {
			List<CourseBean> courseList = coursemodel.list();
			request.setAttribute("courseList", courseList);
		}catch(ApplicationException e) {
			e.printStackTrace();
		}
	}
	
	@Override
	protected boolean validate(HttpServletRequest request) {
		boolean pass = true;
		
		if(DataValidator.isNull(request.getParameter("name"))) {
			request.setAttribute("name", PropertyReader.getValue("error.require", "Name"));
		pass = false;
		}
	
		if(DataValidator.isNull(request.getParameter("courseId"))) {
			request.setAttribute("courseId", PropertyReader.getValue("error.require", "CourseName"));
			pass = false;
		}
		
		if(DataValidator.isNull(request.getParameter("description"))) {
			request.setAttribute("description", PropertyReader.getValue("error.require", "Description"));
			pass = false;
		}
		
		return true;
		
	}
	
	
	
	@Override
	protected BaseBean populateBean(HttpServletRequest request) {
		
		SubjectBean bean = new SubjectBean();
		bean.setId(DataUtility.getLong(request.getParameter("id")));
		bean.setName(DataUtility.getString(request.getParameter("name")));
		bean.setCoursId(DataUtility.getLong(request.getParameter("courseId")));
		bean.setDescription(DataUtility.getString(request.getParameter("description")));
		
		populateDTO(bean, request);
		
		return bean;
	}
	
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		long id = DataUtility.getLong(request.getParameter("id"));
		
		SubjectModel model = new SubjectModel();
		
		if(id > 0) {
			try {
				SubjectBean bean = model.findByPk(id);
				ServletUtility.setBean(bean, request);
				
			}catch(ApplicationException e) {
				e.printStackTrace();
				ServletUtility.handleException(e, request, response);
				return;
			}
		}
		ServletUtility.forward(getView(), request, response);
	}
	
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String op = DataUtility.getString(request.getParameter("operation"));
		
		SubjectModel model = new SubjectModel();
		
		long id = DataUtility.getLong(request.getParameter("id"));
		
		if(OP_SAVE.equalsIgnoreCase(op)) {
			SubjectBean bean = (SubjectBean) populateBean(request);
			
			try {
				long pk = model.add(bean);
				ServletUtility.setBean(bean, request);
				ServletUtility.setSuccessMessage("Subject added Successfully", request);
				
			}catch(DuplicateException e) {
				e.printStackTrace();
				ServletUtility.setErrorMessage("Subject already Exist", request);
				
			}catch (ApplicationException e) {
				e.printStackTrace();
				ServletUtility.handleException(e, request, response);
				return;
			}
			
		} else if (OP_UPDATE.equalsIgnoreCase(op)) {
			SubjectBean bean = (SubjectBean) populateBean(request);
			try {
				if (id > 0) {
					model.update(bean);
				}
				ServletUtility.setBean(bean, request);
				ServletUtility.setSuccessMessage("Subject updated successfully", request);
			} catch (DuplicateException e) {
				ServletUtility.setBean(bean, request);
				ServletUtility.setErrorMessage("Subject Name already exists", request);
			} catch (ApplicationException e) {
				e.printStackTrace();
				ServletUtility.handleException(e, request, response);
				return;
			}
			
		}else if(OP_BACK.equalsIgnoreCase(op)) {
			SubjectBean bean = (SubjectBean) populateBean(request);
			
			try {
				model.update(bean);
				ServletUtility.setBean(bean, request);
				ServletUtility.setSuccessMessage("Subject update Successfully", request);
				
			}catch(DuplicateException e) {
				e.printStackTrace();
				ServletUtility.setErrorMessage("Subject already Exists", request);
				
			}catch(ApplicationException e) {
				e.printStackTrace();
				ServletUtility.handleException(e, request, response);
				return;
			}
		}else if(OP_CANCEL.equalsIgnoreCase(op)) {
			ServletUtility.redirect(ORSView.SUBJECT_LIST_CTL, request, response);
			return;
			
		}else if(OP_RESET.equalsIgnoreCase(op)) {
			ServletUtility.redirect(ORSView.SUBJECT_CTL, request, response);
			return;
		}
		ServletUtility.forward(getView(), request, response);
	}
	
	@Override
	protected String getView() {
		// TODO Auto-generated method stub
		return ORSView.SUBJECT_VIEW;
	}

}
