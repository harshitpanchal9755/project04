package in.co.rays.proj4.test;

import java.sql.Timestamp;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import in.co.rays.proj4.bean.CollegeBean;
import in.co.rays.proj4.exception.ApplicationException;
import in.co.rays.proj4.exception.DataBaseException;
import in.co.rays.proj4.exception.DuplicateException;
import in.co.rays.proj4.model.CollegeModel;

public class TestCollegeModel {

	static CollegeModel model = new CollegeModel();

	public static void main(String[] args) throws Exception {
//		testadd();
//		testupdate();
//		testdelete();
//		testfindBypk();
//		testfindByName();
//		testsearch();
		testnextpk();
	}

	private static void testnextpk() throws DataBaseException {
		int i =model.nextpk();
		System.out.println("nextpk is print");
		
	}

	private static void testsearch() {
		try {
			CollegeBean bean = new CollegeBean();
			List<CollegeBean> l = model.search(bean, 0, 0);
			
			Iterator it = l.iterator();
			
			while(it.hasNext()) {
				bean = (CollegeBean) it.next();
			System.out.println(bean.getId());
			System.out.println(bean.getName());
			System.out.println(bean.getAddress());
			System.out.println(bean.getState());
			System.out.println(bean.getCity());
			System.out.println(bean.getPhoneno());
			System.out.println(bean.getCreatedBy());
			System.out.println(bean.getModifiedBy());
			System.out.println(bean.getCreatedDateTime());
			System.out.println(bean.getModifiedDateTime());
			
			}
			
			}catch(Exception e) {
				e.printStackTrace();
		
		}
		
	}

	private static void testfindByName() {
		
		try {
		CollegeBean bean = model.findByName("harsh");
		
		if(bean == null) {
			System.out.println("tell about name is fail");
			
		}
		
		System.out.println(bean.getId());
		System.out.println(bean.getAddress());
		System.out.println(bean.getState());
		
		}catch(Exception e) {
			e.printStackTrace();
		}
		
	}

	private static void testfindBypk() throws Exception {
		try {
			CollegeBean bean = model.findBypk(3L);

			if (bean == null) {
				System.out.println("test findbypk is fail");
			}

			System.out.println(bean.getName());
			System.out.println(bean.getAddress());
			System.out.println(bean.getState());

		} catch (ApplicationException e) {
			e.printStackTrace();

		}

	}

	private static void testdelete() throws Exception {

		try {
			CollegeBean bean = new CollegeBean();
			long pk = 1L;
			bean.setId(pk);
			model.delete(bean);

			CollegeBean deleteBean = model.findBypk(pk);

			if (deleteBean != null) {
				System.out.println("tell us delete fail");
			}

		} catch (ApplicationException e) {
			e.printStackTrace();
		}

	}

	private static void testupdate() throws ApplicationException, DuplicateException {
		try {
			CollegeBean bean = model.findBypk(1L);

			bean.setName("modita");
			model.update(bean);

		} catch (ApplicationException e) {
			e.printStackTrace();
		}
	}

	private static void testadd() throws Exception {
		CollegeBean bean = new CollegeBean();

		bean.setName("harsh");
		bean.setAddress("ips indore");
		bean.setState("mp");
		bean.setCity("indore");
		bean.setPhoneno("8098768766");
		bean.setCreatedBy("root");
		bean.setModifiedBy("root");
		bean.setCreatedDateTime(new Timestamp(new Date().getTime()));
		bean.setModifiedDateTime(new Timestamp(new Date().getTime()));

		long i = model.add(bean);
		System.out.println("add record " + i);

	}
	
	
}
