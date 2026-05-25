package in.co.rays.proj4.controller;

import javax.servlet.http.HttpServletRequest;

import com.mysql.cj.xdevapi.Schema.Validation;

import in.co.rays.proj4.util.DataValidator;

public class UserSessionCtl extends BaseCtl{

	@Override
	protected boolean validate(HttpServletRequest request) {
		
		boolean pass = true;
		
		if(DataValidator.isNull(request.getParameter("userCode"))) {
			
		}
		return false;
				
	}
	@Override
	protected String getView() {
		// TODO Auto-generated method stub
		return ORSView.USERSESSION_VIEW;
	}

}
