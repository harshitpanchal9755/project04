package in.co.rays.proj4.controller;

import java.io.IOException;
import java.math.BigDecimal;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import in.co.rays.proj4.bean.BaseBean;
import in.co.rays.proj4.bean.VirtualWalletBean;
import in.co.rays.proj4.exception.ApplicationException;
import in.co.rays.proj4.exception.DuplicateException;
import in.co.rays.proj4.model.VirtualWalletModel;
import in.co.rays.proj4.util.DataUtility;
import in.co.rays.proj4.util.DataValidator;
import in.co.rays.proj4.util.PropertyReader;
import in.co.rays.proj4.util.ServletUtility;

@WebServlet(name = "VirtualWalletCtl" , urlPatterns = {"/ctl/VirtualWalletCtl"})
public class VirtualWalletCtl extends BaseCtl {

	@Override
	protected boolean validate(HttpServletRequest request) {
		boolean pass = true;

		if (DataValidator.isNull(request.getParameter("walletCode"))) {
			request.setAttribute("walletCode", PropertyReader.getValue("error.require", "WalletCode"));
			pass = false;

		}

		if (DataValidator.isNull(request.getParameter("userName"))) {
			request.setAttribute("userName", PropertyReader.getValue("error.require", "UserName"));
			pass = false;

		} else if (!DataValidator.isName(request.getParameter("userName"))) {
			request.setAttribute("userName", "Invalid Is UserName");
			pass = false;
		}
		
		if(DataValidator.isNull(request.getParameter("balance"))) {
			request.setAttribute("balance" , PropertyReader.getValue("error.require", "Balance"));
			pass = false;
			
		}
		
		if(DataValidator.isNull(request.getParameter("status"))) {
			request.setAttribute("status" , PropertyReader.getValue("error.require", "status"));
			pass = false;

		}
		
		return pass;
	}

	@Override
	protected BaseBean populateBean(HttpServletRequest request) {
		VirtualWalletBean bean = new VirtualWalletBean();
		bean.setId(DataUtility.getLong(request.getParameter("id")));
		bean.setWalletCode(request.getParameter("walletCode"));
		bean.setUserName(request.getParameter("userName"));
		String balance = request.getParameter("balance");
		
		if(balance !=  null && balance.trim().length() > 0) {
			bean.setBalance(new BigDecimal(balance));
		}
		bean.setStatus(request.getParameter("status"));
		
		populateDTO(bean, request);
		
		return bean;
	}
	
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		long id = DataUtility.getLong(request.getParameter("id"));
		VirtualWalletModel model = new VirtualWalletModel();
		
		if(id > 0) {
			
			try {
				VirtualWalletBean bean = model.findBypk(id);
				ServletUtility.setBean(bean, request);
				
			}catch(ApplicationException e) {
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
		
		VirtualWalletModel model = new VirtualWalletModel();
	

		long id = DataUtility.getLong(request.getParameter("id"));

		if (OP_SAVE.equalsIgnoreCase(op)) {
			VirtualWalletBean bean = (VirtualWalletBean) populateBean(request);

			try {
				long pk = model.add(bean);
				ServletUtility.setBean(bean, request);
				ServletUtility.setSuccessMessage("Data is added Succsessfully", request);

			} catch (DuplicateException e) {
				ServletUtility.setBean(bean, request);
				ServletUtility.setErrorMessage("virtualwallet 1 already Exists", request);
				e.printStackTrace();

			} catch (ApplicationException e) {
				e.printStackTrace();
				ServletUtility.handleException(e, request, response);
				return;
			}

		} else if (OP_UPDATE.equalsIgnoreCase(op)) {

			VirtualWalletBean bean = (VirtualWalletBean) populateBean(request);

			try {
				if (id > 0) {
					model.update(bean);

				}
				
				ServletUtility.setBean(bean, request);
				ServletUtility.setSuccessMessage("Data is Updated Successfully", request);

			} catch (DuplicateException e) {
				ServletUtility.setBean(bean, request);
				ServletUtility.setErrorMessage("virtualwallet already Exists", request);
				e.printStackTrace();
				
			} catch (ApplicationException e) {
				e.printStackTrace();
				ServletUtility.handleException(e, request, response);
				return;
			}
			
		}else if(OP_CANCEL.equalsIgnoreCase(op)) {
			ServletUtility.redirect(ORSView.VIRTUALWALLET_LIST_CTL, request, response);
			return;
			
		}else if(OP_RESET.equalsIgnoreCase(op)) {
			ServletUtility.redirect(ORSView.VIRTUALWALLET_CTL, request, response);
			return;
		}
		
		ServletUtility.forward(getView(), request, response);
		
	}
	@Override
	protected String getView() {
		// TODO Auto-generated method stub
		return ORSView.VIRTUALWALLET_VIEW;
	}

}
