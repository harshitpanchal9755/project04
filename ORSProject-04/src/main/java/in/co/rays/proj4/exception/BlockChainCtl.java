//package in.co.rays.proj4.exception;
//
//import java.io.IOException;
//
//import javax.servlet.ServletException;
//import javax.servlet.annotation.WebServlet;
//import javax.servlet.http.HttpServletRequest;
//import javax.servlet.http.HttpServletResponse;
//
//import in.co.rays.proj4.bean.BaseBean;
//import in.co.rays.proj4.bean.BlockchainBean;
//import in.co.rays.proj4.bean.CourseBean;
//import in.co.rays.proj4.controller.BaseCtl;
//import in.co.rays.proj4.controller.ORSView;
//import in.co.rays.proj4.model.BlockchainModel;
//import in.co.rays.proj4.model.CourseModel;
//import in.co.rays.proj4.util.DataUtility;
//import in.co.rays.proj4.util.DataValidator;
//import in.co.rays.proj4.util.PropertyReader;
//import in.co.rays.proj4.util.ServletUtility;
//@WebServlet(name = "BlockChainCtl", urlPatterns = {"/ctl/BlockChainCtl"})
//public class BlockChainCtl extends BaseCtl {
//
//	@Override
//	protected boolean validate(HttpServletRequest request) {
//		boolean pass = true;
//		
//		if(DataValidator.isNull(request.getParameter("transcactionHash"))) {
//			request.setAttribute("transcactionHash", PropertyReader.getValue("error.require", "TranscactionHash"));
//			pass = false;
//			
//		}
//		
//		if(DataValidator.isNull(request.getParameter("senderAddress"))) {
//			request.setAttribute("senderAddress", PropertyReader.getValue("error.require", "SenderAddress"));
//			pass = false;
//		}
//		
//		if(DataValidator.isNull(request.getParameter("recieverAddress"))) {
//			request.setAttribute("recieverAddress", PropertyReader.getValue("recieverAddress", "Reciever"));
//			pass = false;
//		}
//		
//		if(DataValidator.isNull(request.getParameter("gasFee"))) {
//			request.setAttribute("gasFee", PropertyReader.getValue("error.require", "GasFee"));
//			pass = false;
//		}
//	
//		return pass;
//	}
//	
//	@Override
//	protected BaseBean populateBean(HttpServletRequest request) {
//		BlockchainBean bean = new BlockchainBean();
//		bean.setId(DataUtility.getLong(request.getParameter("id")));
//		bean.setTranscactionHash(DataUtility.getStringData(request.getParameter("transcationHash")));
//		bean.setSenderAddress(DataUtility.getStringData(request.getParameter("senderAddress")));
//		bean.setReceiverAddress(DataUtility.getStringData(request.getParameter("recieverAddress")));
//		
//		populateDTO(bean, request);
//		return bean;
//	}
//	
//	@Override
//	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
//		long id = DataUtility.getLong(request.getParameter("id"));
//		
//		BlockchainModel model = new BlockchainModel();
//		
//		if(id > 0) {
//			try {
//				BlockchainBean bean = model.findBypk(id);
//				ServletUtility.setBean(bean, request);
//			}catch (ApplicationException e) {
//				e.printStackTrace();
//				return;
//			}
//		}
//		ServletUtility.forward(getView(), request, response);
//	}
//	
//	@Override
//	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
//		String op = DataUtility.getStringData(request.getParameter("op"));
//		BlockchainModel model = new BlockchainModel();
//
//		long id = DataUtility.getLong(request.getParameter("id"));
//
//		if (OP_SAVE.equalsIgnoreCase(op)) {
//			BlockchainBean bean = (BlockchainBean) populateBean(request);
//			try {
//				long pk = model.add(bean);
//				ServletUtility.setBean(bean, request);
//				ServletUtility.setSuccessMessage("Course added successfully", request);
//			} catch (DuplicateException e) {
//				ServletUtility.setBean(bean, request);
//				ServletUtility.setErrorMessage("Course already exists", request);
//			} catch (ApplicationException e) {
//				e.printStackTrace();
//				ServletUtility.handleException(e, request, response);
//			return;
//				
//			} catch (Exception e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			}
//		} else if (OP_UPDATE.equalsIgnoreCase(op)) {
//			BlockchainBean bean = (BlockchainBean) populateBean(request);
//			try {
//				if (id > 0) {
//					model.update(bean);
//				}
//				ServletUtility.setBean(bean, request);
//				ServletUtility.setSuccessMessage("Course updated successfully", request);
//			} catch (DuplicateException e) {
//				ServletUtility.setBean(bean, request);
//				ServletUtility.setErrorMessage("Course already exists", request);
//			} catch (ApplicationException e) {
//				e.printStackTrace();
//				ServletUtility.handleException(e, request, response);
//				return;
//			}
//		} else if (OP_CANCEL.equalsIgnoreCase(op)) {
//			ServletUtility.redirect(ORSView.BLOCKCHAIN_LIST_CTL, request, response);
//			return;
//		} else if (OP_RESET.equalsIgnoreCase(op)) {
//			ServletUtility.redirect(ORSView.BLOCKCHAIN_CTL, request, response);
//			return;
//		}
//		ServletUtility.forward(getView(), request, response);
//	}
//		
//	
//	@Override
//	protected String getView() {
//		// TODO Auto-generated method stub
//		return ORSView.BLOCKCHAIN_VIEW;
//	}
//	
//
//}
