package in.co.rays.proj4.controller;

import java.io.IOException;
import java.math.BigDecimal;
import java.text.DecimalFormat;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.crypto.Data;

import in.co.rays.proj4.bean.BaseBean;
import in.co.rays.proj4.bean.InsuranceAppBean;
import in.co.rays.proj4.bean.VirtualWalletBean;
import in.co.rays.proj4.exception.ApplicationException;
import in.co.rays.proj4.exception.DuplicateException;
import in.co.rays.proj4.model.InsuranceAppModel;
import in.co.rays.proj4.model.VirtualWalletModel;
import in.co.rays.proj4.util.DataUtility;
import in.co.rays.proj4.util.DataValidator;
import in.co.rays.proj4.util.PropertyReader;
import in.co.rays.proj4.util.ServletUtility;

@WebServlet(name = "InsuranceAppCtl", urlPatterns = {"/ctl/InsuranceAppCtl"})
public class InsuranceAppCtl extends BaseCtl {

	@Override
	protected boolean validate(HttpServletRequest request) {
		
		boolean pass = true;
		
		if(DataValidator.isNull(request.getParameter("customername"))) {
			request.setAttribute("customername", PropertyReader.getValue("error.require", "CustomerName"));
			pass = false;
			
		}
		
		if(DataValidator.isNull(request.getParameter("policytype"))) {
			request.setAttribute("policytype", PropertyReader.getValue("error.require", "PolicyType"));
			pass = false;
		}
		
		if(DataValidator.isNull(request.getParameter("amount"))) {
			request.setAttribute("amount", PropertyReader.getValue("error.require", "Amount"));
			pass = false;
		}
		
		if(DataValidator.isNull(request.getParameter("claimstatus"))) {
			request.setAttribute("claimstatus", PropertyReader.getValue("error.require", "ClaimStatus"));
			pass = false;
		}
		
		return pass;
	}
	
	@Override
	protected BaseBean populateBean(HttpServletRequest request) {
		InsuranceAppBean bean = new InsuranceAppBean();
		bean.setId(DataUtility.getLong(request.getParameter("id")));
		bean.setCustomerName(DataUtility.getStringData(request.getParameter("customername")));
		bean.setPolicyType(DataUtility.getStringData(request.getParameter("policytype")));
		String amount = request.getParameter("amount");
		if(amount != null && !amount.trim().equals("")) {
			bean.setAmount(Double.parseDouble(amount));
		}else {
			bean.setAmount(0);
		}
		bean.setClaimStatus(DataUtility.getStringData(request.getParameter("claimstatus")));
		
		return populateDTO(bean, request);
	}
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		long id = DataUtility.getLong(request.getParameter("id"));
		
		InsuranceAppModel model = new InsuranceAppModel();
		
		if(id > 0) {
			try {
				InsuranceAppBean bean = model.findBypk(id);
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
		System.out.println("operation ===>" + op);
		
	InsuranceAppModel model = new InsuranceAppModel();
	

		long id = DataUtility.getLong(request.getParameter("id"));

		if (OP_SAVE.equalsIgnoreCase(op)) {
			InsuranceAppBean bean = (InsuranceAppBean) populateBean(request);

			try {
				long pk = model.add(bean);
				ServletUtility.setBean(bean, request);
				ServletUtility.setSuccessMessage("Data is added Succsessfully", request);

			} catch (DuplicateException e) {
				ServletUtility.setBean(bean, request);
				ServletUtility.setErrorMessage("InsuranceApp already Exists", request);
				e.printStackTrace();

			} catch (ApplicationException e) {
				e.printStackTrace();
				ServletUtility.handleException(e, request, response);
				return;
			}

		} else if (OP_UPDATE.equalsIgnoreCase(op)) {

			InsuranceAppBean bean = (InsuranceAppBean) populateBean(request);

			try {
				if (id > 0) {
					model.update(bean);

				}
				
				ServletUtility.setBean(bean, request);
				ServletUtility.setSuccessMessage("Data is Updated Successfully", request);

			} catch (DuplicateException e) {
				ServletUtility.setBean(bean, request);
				ServletUtility.setErrorMessage("insurance already Exists", request);
				e.printStackTrace();
				
			} catch (ApplicationException e) {
				e.printStackTrace();
				ServletUtility.handleException(e, request, response);
				return;
			}
			
		}else if(OP_CANCEL.equalsIgnoreCase(op)) {
			ServletUtility.redirect(ORSView.INSURANCEAPP_LIST_CTL, request, response);
			return;
			
		}else if(OP_RESET.equalsIgnoreCase(op)) {
			ServletUtility.redirect(ORSView.INSURANCEAPP_CTL, request, response);
			return;
		}
		
		ServletUtility.forward(getView(), request, response);
		
	}
		
	@Override
	protected String getView() {
		return ORSView.INSURANCEAPP_VIEW;
	}
	

}
