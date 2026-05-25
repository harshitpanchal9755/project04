package in.co.rays.proj4.controller;
import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import in.co.rays.proj4.bean.BaseBean;
import in.co.rays.proj4.bean.VoiceAssistantBean;
import in.co.rays.proj4.controller.BaseCtl;
import in.co.rays.proj4.controller.ORSView;
import in.co.rays.proj4.exception.ApplicationException;
import in.co.rays.proj4.exception.DuplicateException;
import in.co.rays.proj4.model.VoiceAssistantModel;
import in.co.rays.proj4.util.DataUtility;
import in.co.rays.proj4.util.PropertyReader;
import in.co.rays.proj4.util.ServletUtility;

@WebServlet(name = "VoiceAssistantListCtl", urlPatterns = {"/ctl/VoiceAssistantListCtl"})
public class VoiceAssistantListCtl extends BaseCtl {
	
	@Override
	protected BaseBean populateBean(HttpServletRequest request) {
		VoiceAssistantBean bean = new VoiceAssistantBean();
		bean.setUserVoice(DataUtility.getStringData(request.getParameter("userVoice")));
		bean.setResponse(DataUtility.getStringData(request.getParameter("response")));
		bean.setLanguage(DataUtility.getStringData(request.getParameter("language")));
		String accuracy = request.getParameter("accuracy");
		if(accuracy != null && !accuracy.trim().equals("")) {
			bean.setAccuracy(Double.parseDouble(accuracy));
			
		} else {
			bean.setAccuracy(0);
		}
		return bean;
	}

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		int pageNo = 1;
		int pageSize = DataUtility.getInt(PropertyReader.getValue("page.size"));
		
		
		VoiceAssistantBean bean = (VoiceAssistantBean) populateBean(request);
		VoiceAssistantModel model = new VoiceAssistantModel();
		
		try {
			List<VoiceAssistantBean> list = model.search(bean, pageNo, pageSize);
			List<VoiceAssistantBean> next = model.search(bean, pageNo + 1, pageSize);
			
			if(list == null || list.isEmpty()) {
				ServletUtility.setErrorMessage("No Record Found", request);
			}
			
			ServletUtility.setList(list, request);
			ServletUtility.setPageNo(pageNo, request);
			ServletUtility.setPageSize(pageSize, request);
			ServletUtility.setBean(bean, request);
			request.setAttribute("nextListSize", next.size());
			
			ServletUtility.forward(getView(), request, response);
			
		
		}catch (ApplicationException e) {
			e.printStackTrace();
			return;
		}
		
		ServletUtility.forward(getView(), request, response);
	}
	
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		List list = null;
		List next = null;
		
		int pageNo = DataUtility.getInt(request.getParameter("pageNo"));
		int pageSize = DataUtility.getInt(request.getParameter("pageSize"));

		pageNo = (pageNo == 0) ? 1 : pageNo;
		pageSize = (pageSize == 0) ? DataUtility.getInt(PropertyReader.getValue("page.size")) : pageSize;

		VoiceAssistantBean bean = (VoiceAssistantBean) populateBean(request);
		VoiceAssistantModel model = new VoiceAssistantModel();

		String op = DataUtility.getString(request.getParameter("operation"));
		String[] ids = request.getParameterValues("ids");

		try {
			if (OP_SEARCH.equalsIgnoreCase(op) || "Next".equalsIgnoreCase(op) || "Previous".equalsIgnoreCase(op)) {

				if (OP_SEARCH.equalsIgnoreCase(op)) {
					pageNo = 1;

				} else if (OP_NEXT.equalsIgnoreCase(op)) {
					pageNo++;

				} else if (OP_PREVIOUS.equalsIgnoreCase(op) && pageNo > 1) {
					pageNo--;

				}
			} else if (OP_NEW.equalsIgnoreCase(op)) {
				ServletUtility.redirect(ORSView.VOICEASSISTANT_CTL, request, response);
				return;

			} else if (OP_DELETE.equalsIgnoreCase(op)) {
				pageNo = 1;

				if (ids != null && ids.length > 0) {
					VoiceAssistantBean deletebean = new VoiceAssistantBean();

					for (String id : ids) {
						deletebean.setId(DataUtility.getInt(id));
						model.delete(deletebean);
						ServletUtility.setSuccessMessage("Data is delete sucessfully", request);

					}

				} else {
					ServletUtility.setErrorMessage("Select at least one record", request);
				}

			} else if (OP_RESET.equalsIgnoreCase(op)) {
				ServletUtility.redirect(ORSView.VOICEASSISTANT_LIST_CTL, request, response);
				return;
			}

			list = model.search(bean, pageNo, pageSize);
			next = model.search(bean, pageNo+1, pageSize);
			

			if (list == null || list.size() == 0) {
				ServletUtility.setErrorMessage("No record is found", request);
			}

			ServletUtility.setList(list, request);
			ServletUtility.setPageNo(pageNo, request);
			ServletUtility.setPageSize(pageSize, request);
			ServletUtility.setBean(bean, request);
			request.setAttribute("nextListSize", next.size());

			ServletUtility.forward(getView(), request, response);

		} catch (ApplicationException e) {
			e.printStackTrace();
			return;
		} catch (DuplicateException e) {
			e.printStackTrace();
			return;
		}
		
		
	}
	@Override
	protected String getView() {
		// TODO Auto-generated method stub
		return ORSView.VOICEASSISTANT_LIST_VIEW;
	}

}
