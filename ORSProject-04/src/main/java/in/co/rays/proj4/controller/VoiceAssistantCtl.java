package in.co.rays.proj4.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import in.co.rays.proj4.bean.BaseBean;
import in.co.rays.proj4.bean.UserBean;
import in.co.rays.proj4.bean.VoiceAssistantBean;
import in.co.rays.proj4.exception.ApplicationException;
import in.co.rays.proj4.exception.DuplicateException;
import in.co.rays.proj4.model.UserModel;
import in.co.rays.proj4.model.VoiceAssistantModel;
import in.co.rays.proj4.util.DataUtility;
import in.co.rays.proj4.util.DataValidator;
import in.co.rays.proj4.util.PropertyReader;
import in.co.rays.proj4.util.ServletUtility;
@WebServlet(name = "VoiceAssistantCtl", urlPatterns = {"/ctl/VoiceAssistantCtl"})
public class VoiceAssistantCtl extends BaseCtl {

	@Override
	protected boolean validate(HttpServletRequest request) {
		boolean pass = true;
		
		if(DataValidator.isNull(request.getParameter("userVoice"))) {
			request.setAttribute("userVoice", PropertyReader.getValue("error.require", "UserVoice"));
			pass = false;
		}
		
		if(DataValidator.isNull(request.getParameter("response"))) {
			request.setAttribute("response", PropertyReader.getValue("error.require", "Response"));
			pass = false;
		}
		
		if(DataValidator.isNull(request.getParameter("language"))) {
			request.setAttribute("language", PropertyReader.getValue("error.require", "Language"));
			pass = false;
			
		}
		
		if(DataValidator.isNull(request.getParameter("accuracy"))) {
			request.setAttribute("accuracy", PropertyReader.getValue("error.require", "Accuracy"));
			pass = false;
		}
		return pass;
		
		
	}
	
	@Override
	protected BaseBean populateBean(HttpServletRequest request) {
		VoiceAssistantBean bean = new VoiceAssistantBean();
		bean.setId(DataUtility.getLong(request.getParameter("id")));
		bean.setUserVoice(DataUtility.getStringData(request.getParameter("userVoice")));
		bean.setResponse(DataUtility.getStringData(request.getParameter("response")));
		bean.setLanguage(DataUtility.getStringData(request.getParameter("language")));
		String accuracy = request.getParameter("accuracy");
		if(accuracy != null && !accuracy.trim().equals("")) {
			bean.setAccuracy(Double.parseDouble(accuracy));
			
		}else {
			bean.setAccuracy(0);
		}
		
		populateDTO(bean, request);
		return bean;
	}
	
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		long id = DataUtility.getLong(request.getParameter("id"));
		
		VoiceAssistantModel model = new VoiceAssistantModel();
		
		if(id > 0) {
			
			try {
				VoiceAssistantBean bean = model.findBypk(id);
				ServletUtility.setBean(bean, request);
			}catch (ApplicationException e) {
				e.printStackTrace();
				return;
				
			}
		}
		
		ServletUtility.forward(getView(), request, response);
	}
	
@Override
protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	String op = DataUtility.getString(request.getParameter("operation"));
	System.out.println("operation =" + op);

	VoiceAssistantModel model = new VoiceAssistantModel();

	long id = DataUtility.getLong(request.getParameter("id"));

	if (OP_SAVE.equalsIgnoreCase(op)) {
		VoiceAssistantBean bean = (VoiceAssistantBean) populateBean(request);

		try {
			long pk = model.add(bean);
			ServletUtility.setBean(bean, request);
			ServletUtility.setSuccessMessage("Data is added Succsessfully", request);

		} catch (DuplicateException e) {
			ServletUtility.setBean(bean, request);
			ServletUtility.setErrorMessage("VoiceAssistant already Exists", request);
			e.printStackTrace();

		} catch (ApplicationException e) {
			e.printStackTrace();
			return;
		}

	} else if (OP_UPDATE.equalsIgnoreCase(op)) {

		VoiceAssistantBean bean = (VoiceAssistantBean) populateBean(request);

		try {
			if (id > 0) {
				model.update(bean);

			}
			
			ServletUtility.setBean(bean, request);
			ServletUtility.setSuccessMessage("Data is Updated Successfully", request);

		} catch (DuplicateException e) {
			ServletUtility.setBean(bean, request);
			ServletUtility.setErrorMessage("VoiceAssistant already Exists", request);
			e.printStackTrace();
		} catch (ApplicationException e) {
			e.printStackTrace();
			return;
		}
		
	}else if(OP_CANCEL.equalsIgnoreCase(op)) {
		ServletUtility.redirect(ORSView.VOICEASSISTANT_LIST_CTL, request, response);
		return;
		
	}else if(OP_RESET.equalsIgnoreCase(op)) {
		ServletUtility.redirect(ORSView.VOICEASSISTANT_CTL, request, response);
		return;
	}
	
	ServletUtility.forward(getView(), request, response);

}

	
	@Override
	protected String getView() {
		// TODO Auto-generated method stub
		return ORSView.VOICEASSISTANT_VIEW;
	}

}
