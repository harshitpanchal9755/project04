package in.co.rays.proj4.test;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import in.co.rays.proj4.bean.CourseBean;
import in.co.rays.proj4.exception.ApplicationException;
import in.co.rays.proj4.exception.DuplicateException;
import in.co.rays.proj4.model.CourseModel;

public class TestCourseModel {
	static CourseModel model = new CourseModel();
	
	public static void main(String[] args) throws Exception {
		
//		testadd();
//		testupdate();
//		testdelete();
//		testfindBypk();
//		testfindByname();
		testsearch();
		
		
	}

	private static void testsearch() throws ApplicationException {
		try {
		CourseBean bean = new CourseBean();
		
		List<CourseBean> list = new ArrayList<CourseBean>();
		bean.setName("anup");
		list = model.search(bean, 0, 0);
		
		if(list.size() == 0) {
			System.out.println("test is fail");
			
		}
		
		Iterator it = list.iterator();
		
		while(it.hasNext()) {
			bean = (CourseBean)it.next();
			System.out.println(bean.getId());
			System.out.println(bean.getName());
			System.out.println(bean.getDuration());
			System.out.println(bean.getDescription());
			System.out.println(bean.getCreatedBy());
			System.out.println(bean.getModifiedBy());
			System.out.println(bean.getCreatedDateTime());
			System.out.println(bean.getModifiedDateTime());
			
		}
		
	}catch(ApplicationException e) {
		e.printStackTrace();
	}
	}

	private static void testfindByname() {
		try {
			CourseBean bean = model.findByName("anup");
			if(bean == null) {
				System.out.println("name is add");
			
			}
			
			System.out.println(bean.getId());
			System.out.println(bean.getName());
			System.out.println(bean.getDuration());
			System.out.println(bean.getDescription());
		} catch (ApplicationException e) {
			e.printStackTrace();
		}
		
	}

	private static void testfindBypk() {
		try {
			CourseBean bean = model.findBypk(2);
			
			if(bean == null) {
				System.out.println("findby pk is add");
				
			}
			
			System.out.println(bean.getId());
			System.out.println(bean.getName());
			System.out.println(bean.getDescription());
			
		} catch (ApplicationException e) {
			e.printStackTrace();
		}
		
	}

	private static void testdelete() throws Exception {
	
		CourseBean bean = new CourseBean();
		
		long pk = 2L;
		bean.setId(pk);
		model.delete(bean);
		
		CourseBean deletebean = model.findBypk(pk);
		
		if(deletebean == null) {
			System.out.println("id is delete");
		}
		
		else {
			System.out.println("id is not delete");
		}
		
		
	}

	private static void testupdate() throws Exception {
		try {
			CourseBean bean = model.findBypk(2L);
			bean.setName("Anshika Singh");
			model.update(bean);
			
		} catch (ApplicationException e) {
			e.printStackTrace();
			throw new ApplicationException("Exception : Exception is update" + e.getMessage());
		}
		
	}

	private static void testadd() throws Exception {
		
		try {
		CourseBean bean = new CourseBean();
		bean.setName("anup");
		bean.setDuration("duration of after 10 days");
		bean.setDescription("des of course 60 hrs");
		bean.setCreatedBy("root");
		bean.setModifiedBy("root");
		bean.setCreatedDateTime(new Timestamp(new Date().getTime()));
		bean.setModifiedDateTime(new Timestamp(new Date().getTime()));
		
		long id = model.add(bean);
		System.out.println("record is add" + id);
			
		} catch (ApplicationException e) {
			e.printStackTrace();
			throw new ApplicationException("Exception : Exception is findbypk" + e.getMessage());
		}
		
	}
	
	

}
