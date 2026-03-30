package in.co.rays.proj4.test;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.sql.Timestamp;

import in.co.rays.proj4.bean.RoleBean;
import in.co.rays.proj4.exception.ApplicationException;
import in.co.rays.proj4.exception.DataBaseException;
import in.co.rays.proj4.exception.DuplicateException;
import in.co.rays.proj4.model.RoleModel;

public class TestRoleModel {

	static RoleModel model = new RoleModel();

	public static void main(String[] args) throws Exception {
//		testadd();
//		testdelete();
		// testnextPk();
//		testupdate();
		testfindBypk();
//		testfindByName();
//		testsearch();

	}

	private static void testsearch() throws Exception  {
		try {
			RoleBean bean = new RoleBean();
			List<RoleBean> list = new ArrayList<RoleBean>();
			bean.setName("student");
			list = model.search(bean, 0, 0);
			if (list.size() == 0) {
				System.out.println("Test search fail");
			}
			Iterator<RoleBean> it = list.iterator();
			while (it.hasNext()) {
				bean = (RoleBean) it.next();
				System.out.println(bean.getId());
				System.out.println(bean.getName());
				System.out.println(bean.getDescription());
			}

		} catch (Exception e) {
			e.printStackTrace();
			throw new ApplicationException("Exception : Exception in search role" + e.getMessage());
		}
	}

	private static void testfindByName() throws ApplicationException {
		try {
			RoleBean bean = model.findByName("student");
			if (bean != null) {
				System.out.println("test name is fail");
			}

			System.out.println(bean.getId());
			System.out.println(bean.getName());
			System.out.println(bean.getDescription());

		} catch (ApplicationException e) {
			e.printStackTrace();
		}

	}

	private static void testfindBypk() {
		try {
			RoleBean bean = model.findBypk(2L);
			if (bean == null) {
				System.out.println("Test Find By PK fail");
			}
			System.out.println(bean.getId());
			System.out.println(bean.getName());
			System.out.println(bean.getDescription());
		} catch (ApplicationException e) {
			e.printStackTrace();
		}

	}

	private static void testupdate() throws Exception {
		try {
			RoleBean bean = model.findBypk(3L);
			bean.setName("student");
			model.update(bean);

		} catch (ApplicationException e) {
			e.printStackTrace();
		}catch(DuplicateException e) {
			e.printStackTrace();
		}
	}

	private static void testnextPk() throws DataBaseException {

		int i = model.nextpk();

		System.out.println(" Next Pk id is " + i);

	}

	private static void testdelete() throws Exception {

		try {
			RoleBean bean = new RoleBean();
			long pk = 2L;
			bean.setId(pk);
			model.delete(bean);

			RoleBean deletedbean = model.findBypk(pk);
			if (deletedbean != null) {
				System.out.println("Test Delete fail");
			}
		} catch (ApplicationException e) {
			e.printStackTrace();
		}
	}

	private static void testadd() throws Exception {

		RoleBean bean = new RoleBean();

		bean.setName("Admin");
		bean.setDescription("developer");
		bean.setCreatedBy("root");
		bean.setModifiedBy("root");
		bean.setCreatedDateTime(new Timestamp(new Date().getTime()));
		bean.setModifiedDateTime(new Timestamp(new Date().getTime()));

		long i = model.add(bean);
		System.out.println("record is add" + i);

	}

}
