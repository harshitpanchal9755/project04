package in.co.rays.proj4.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.mysql.cj.xdevapi.Schema.Validation;

import in.co.rays.proj4.bean.BaseBean;
import in.co.rays.proj4.bean.DataImportLogBean;
import in.co.rays.proj4.util.DataUtility;
import in.co.rays.proj4.util.DataValidator;
import in.co.rays.proj4.util.PropertyReader;

public class DataImportLogCtl extends BaseCtl {
	public boolean Validation(HttpServletRequest request, HttpServletResponse response) {
		boolean pass = true;
		
		if(DataValidator.isNull(request.getParameter("filename"))) {
			request.setAttribute("filename", PropertyReader.getValue("error.requird", "FileName"));
			pass = false;
			
		}else if(DataValidator.isName(request.getParameter("filename"))) {
			request.setAttribute("filename", "Invalide FileName");
			pass = false;
			
		}
		return false;

	}
	
	@Override
	protected BaseBean populateBean(HttpServletRequest request) {
		DataImportLogBean bean = new DataImportLogBean();
		
		bean.setId(DataUtility.getLong(request.getParameter("id")));
		bean.setImportlogcode(DataUtility.getString(request.getParameter("importlogcode")));
		
		return super.populateBean(request);
	}

	@Override
	protected String getView() {
		// TODO Auto-generated method stub
		return ORSView.DATAIMPORTLOG_VIEW;
	}

}
