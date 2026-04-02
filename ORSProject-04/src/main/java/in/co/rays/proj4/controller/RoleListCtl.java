package in.co.rays.proj4.controller;

import java.io.IOException;
import java.util.List;

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
import in.co.rays.proj4.util.PropertyReader;
import in.co.rays.proj4.util.ServletUtility;

@WebServlet("/RoleListCtl")
public class RoleListCtl extends BaseCtl {

	@Override
	protected BaseBean populateBean(HttpServletRequest request) {
		RoleBean bean = new RoleBean();

		bean.setName(DataUtility.getString(request.getParameter("name")));
		bean.setId(DataUtility.getLong(request.getParameter("roleid")));
		return bean;

	}

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		int pageNo = 1;
		int pageSize = DataUtility.getInt(PropertyReader.getValue("page.size"));

		RoleBean bean = new RoleBean();
		RoleModel model = new RoleModel();

		try {
			List<RoleBean> list = model.search(bean, pageNo, pageSize);
			List<RoleBean> next = model.search(bean, pageNo + 1, pageSize);

			if (list == null || list.isEmpty()) {
				ServletUtility.setErrorMessage("No record found", request);
			}

			ServletUtility.setList(list, request);
			ServletUtility.setPageNo(pageNo, request);
			ServletUtility.setPageSize(pageSize, request);
			ServletUtility.setBean(bean, request);
			request.setAttribute("nextListSize", next.size());

		} catch (ApplicationException e) {
			e.printStackTrace();
		}

	}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		List list = null;
		List next = null;
		
		int pageNo = DataUtility.getInt(request.getParameter("pageNO"));
		int pageSize = DataUtility.getInt(request.getParameter("pageSize"));
		
		pageNo = (pageNo == 0) ? 1 : pageNo;
		pageSize = (pageSize == 0) ? DataUtility.getInt(PropertyReader.getValue("page.size")) : pageSize;

		RoleBean bean = (RoleBean) populateBean(request);
		RoleModel model = new RoleModel();

		String op = DataUtility.getString(request.getParameter("operation"));
		String[] ids = request.getParameterValues("ids");
		
		try {
			if(OP_SEARCH.equalsIgnoreCase(op) || "Next".equalsIgnoreCase(op) || "Previous".equalsIgnoreCase(op)) {
				
				if(OP_SEARCH.equalsIgnoreCase(op)) {
					pageNo = 1;
					
				}else if("Next".equalsIgnoreCase(op)) {
					pageNo++;
					
				}else if("Previous".equalsIgnoreCase(op) && pageNo > 1) {
					pageNo--;
					
				}
			}else if(OP_NEW.equalsIgnoreCase(op)) {
				ServletUtility.redirect(ORSView.ROLE_CTL, request, response);
				return;
			
			
			
			}else if(OP_DELETE.equalsIgnoreCase(op)) {
				pageNo = 1;
				
				if(ids != null && ids.length == 0) {
					RoleBean deletebean = new RoleBean();
					
				for(String id : ids) {
					deletebean.setId(DataUtility.getInt("id"));
					model.delete(deletebean);
					ServletUtility.setSuccessMessage("Data is delete is sucessfull", request);
					
				}
			
				}else {
					ServletUtility.setErrorMessage("Select al least one record", request);
				}	
			
	}else if(OP_RESET.equalsIgnoreCase(op)) {
		ServletUtility.redirect(ORSView.ROLE_LIST_CTL, request, response);
		return;
	}
			
	list = model.search(bean, pageSize, pageNo)	;
	next = model.search(bean, pageSize + 1, pageNo);
	
	if(list == null || list.size() == 0) {
		ServletUtility.setErrorMessage("No record is found", request);
	}
	
	ServletUtility.setList(list, request);
	ServletUtility.setPageNo(pageNo, request);
	ServletUtility.setPageSize(pageSize, request);
	ServletUtility.setBean(bean, request);
	request.setAttribute("nextListSize", next.size());
	
	ServletUtility.forward(getView(), request, response);
	
		}catch(ApplicationException e) {
			e.printStackTrace();
			return;
		} catch (DuplicateException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	protected String getView() {
		// TODO Auto-generated method stub
		return ORSView.ROLE_LIST_VIEW;
	}

}
