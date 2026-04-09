package in.co.rays.proj4.test;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import javax.management.modelmbean.ModelMBean;

import in.co.rays.proj4.bean.CollegeBean;
import in.co.rays.proj4.bean.StudentBean;
import in.co.rays.proj4.exception.ApplicationException;
import in.co.rays.proj4.model.StudentModel;

public class TestStudentModel {
	static StudentModel model = new StudentModel();
	public static void main(String[] args) throws Exception {
		

		 testadd();
//		 testupdate();
//			testdelete();
//			testfindBypk();
//			testEmail();
//			testsearch();

	}
	
	private static void testsearch() {
		try {
			StudentBean bean = new StudentBean();
			List<StudentBean> list = model.search(bean, 0, 0);
			
			Iterator it = list.iterator();
			
			while(it.hasNext()) {
				bean = (StudentBean) it.next();
				System.out.println(bean.getId());
				System.out.println(bean.getFirstName());
				System.out.println(bean.getLastName());
				System.out.println(bean.getDob());
				System.out.println(bean.getGender());
				System.out.println(bean.getMobileno());
				System.out.println(bean.getCollegeId());
				System.out.println(bean.getCollegeName());
				System.out.println(bean.getCreatedBy());
				System.out.println(bean.getModifiedBy());
				System.out.println(bean.getCreatedDateTime());
				System.out.println(bean.getModifiedDateTime());
			}
			
		}catch(Exception e) {
			e.printStackTrace();
		}
		
	}

	private static void testEmail() {
		try {
			StudentBean bean = model.findByEmailId("anupaa@mail.com");
			
			if(bean == null) {
				System.out.println("emil is fail ");
				
			}
			
			System.out.println(bean.getFirstName());
			System.out.println(bean.getLastName());
			System.out.println(bean.getCollegeId());

		} catch (ApplicationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

	private static void testfindBypk() {
	try {
		StudentBean bean = model.findByPk(3L);
		
		if(bean == null) {
			System.out.println("tel about pk is fail");
			
		}
		
		System.out.println(bean.getFirstName());
		System.out.println(bean.getLastName());
	
		
	} catch (ApplicationException e) {
		
		e.printStackTrace();
	}
	
		
	}

	private static void testdelete() {
	    
		try {
			StudentBean bean = new StudentBean();
			long pk = 1L;
			bean.setId(1);
			model.delete(bean);
			
			StudentBean deletebean= model.findByPk(pk);
			
			if(deletebean != null) {
				System.out.println("tell me delete fails ");
				
			}
			}catch(Exception e) {
				e.printStackTrace();
				
			}
		
	}

	private static void testupdate() throws Exception {
		try {
			StudentBean bean = model.findByPk(1L);
			
			bean.setFirstName("anupa");
			bean.setLastName("rathor");
			bean.setCollegeId(2L);
		  	model.update(bean);
			
		}catch(Exception e) {
			e.printStackTrace();
		}
		}

	private static void testadd() throws Exception {
		
		try {
			StudentBean bean = new StudentBean();

			bean.setFirstName("harshit");
			bean.setLastName("singh");
			bean.setDob(new java.sql.Date(new Date().getTime()));
			bean.setGender("male");
			bean.setMobileno("8098768766");
			bean.setEmail("harshitsingh@mail.com");
			bean.setCollegeId(2);
			bean.setCollegeName("Ips University");
			bean.setCreatedBy("root");
			bean.setModifiedBy("root");
			bean.setCreatedDateTime(new Timestamp(new Date().getTime()));
			bean.setModifiedDateTime(new Timestamp(new Date().getTime()));
			
			long i = model.add(bean);
			System.out.println("record is add" + i);

		}catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	
	
	
}


