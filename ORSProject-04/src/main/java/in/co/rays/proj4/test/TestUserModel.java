//package in.co.rays.proj4.test;
//
//import java.sql.Timestamp;
//import java.util.ArrayList;
//import java.util.Date;
//import java.util.Iterator;
//import java.util.List;
//
//import javax.management.modelmbean.ModelMBean;
//
//import in.co.rays.proj4.bean.RoleBean;
//import in.co.rays.proj4.bean.UserBean;
//import in.co.rays.proj4.exception.ApplicationException;
//import in.co.rays.proj4.exception.DuplicateException;
//import in.co.rays.proj4.model.UserModel;
//
//public class TestUserModel {
//	static UserModel model = new UserModel();
//
//	public static void main(String[] args) throws Exception {
//
////	 testadd();
////	 testupdate();
////		testdelete();
////		testfindBypk();
////		testlogin();
////		testauthenticate();
//		testsearch();
//
//	}
//
//	private static void testsearch() throws Exception {
//		try {
//			UserBean bean = new UserBean();
//			bean.setFirstName("sir");
//			List<UserBean> list = new ArrayList();
//
//			list = model.search(bean, 0, 0);
//			if (list.size() == 0) {
//				System.out.println("tell search is fail");
//			}
//
//			Iterator<UserBean> it = list.iterator();
//
//			while (it.hasNext()) {
//				bean = (UserBean) it.next();
//				System.out.println(bean.getId());
//				System.out.println(bean.getFirstName());
//				System.out.println(bean.getLastName());
//
//			}
//		} catch (Exception e) {
//			e.printStackTrace();
//			throw new ApplicationException("Exception : Exception as search user" + e.getMessage());
//
//		}
//
//	}
//
//	private static void testauthenticate() {
//		try {
//			UserBean bean = new UserBean();
//
//			bean.setLogin("harshit@gmail.com");
//			bean.setPassword("harshit123");
//
//			model.authenticate(bean.getLogin(), bean.getPassword());
//
//			if (bean != null) {
//				System.out.println("authenicate is sucessfull");
//
//			} else {
//				System.out.println("invalidate login & password");
//
//			}
//
//		} catch (ApplicationException e) {
//			e.printStackTrace();
//		}
//
//	}
//
//	private static void testlogin() {
//		try {
//			UserBean bean = model.findByLogin("harshit@gamil.com");
//
//			if (bean == null) {
//				System.out.println("tell is about login ");
//			}
//
//			System.out.println(bean.getId());
//			System.out.println(bean.getFirstName());
//			System.out.println(bean.getLastName());
//			System.out.println(bean.getLogin());
//		} catch (ApplicationException e) {
//			e.printStackTrace();
//		}
//
//	}
//
//	private static void testfindBypk() {
//
//		try {
//			UserBean bean = model.findByPk(1L);
//			if (bean == null) {
//				System.out.println("tell is getting fails pk");
//
//			}
//
//			System.out.println(bean.getFirstName());
//			System.out.println(bean.getLastName());
//			System.out.println(bean.getLogin());
//
//		} catch (ApplicationException e) {
//			e.printStackTrace();
//		}
//
//	}
//
//	private static void testdelete() throws Exception {
//		try {
//			UserBean bean = new UserBean();
//			long pk = 2L;
//			bean.setId(pk);
//
//			model.delete(bean);
//			UserBean deletebean = model.findByPk(pk);
//
//			if (deletebean != null) {
//				System.out.println("tell is fails ");
//
//			}
//		} catch (ApplicationException e) {
//			e.printStackTrace();
//		}
//	}
//
//	private static void testupdate() throws Exception {
//		try {
//			UserBean bean = model.findByPk(1L);
//			bean.setFirstName("sir");
//
//			model.update(bean);
//
//		} catch (ApplicationException | DuplicateException e) {
//			e.printStackTrace();
//
//		}
//
//	}
//
//	private static void testadd() throws Exception {
//		UserBean bean = new UserBean();
//
//		bean.setFirstName("vigay");
//		bean.setLastName("panchal");
//		bean.setLogin("vijay@gamil.com");
//		bean.setPassword("harshit1233");
//		bean.setDob(new Timestamp(new Date().getTime()));
//		bean.setMobileNo("98779876876");
//		bean.setRoleid(12);
//		bean.setGender("male");
//		bean.setCreatedBy("root");
//		bean.setModifiedBy("root");
//		bean.setCreatedDateTime(new Timestamp(new Date().getTime()));
//		bean.setModifiedDateTime(new Timestamp(new Date().getTime()));
//
//		long i = model.add(bean);
//		System.out.println("record is add" + i);
//
//	}
//}
