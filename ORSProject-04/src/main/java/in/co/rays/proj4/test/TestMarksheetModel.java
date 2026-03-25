package in.co.rays.proj4.test;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import in.co.rays.proj4.bean.MarksheetBean;
import in.co.rays.proj4.exception.ApplicationException;
import in.co.rays.proj4.exception.DuplicateException;
import in.co.rays.proj4.model.MarksheetModel;

public class TestMarksheetModel {
	static MarksheetModel model = new MarksheetModel();
	
	public static void main(String[] args) throws Exception {
		
//		testadd();
//		testupdate();
//		testdelete();
//		testfindByroll();
//		testfindBypk();
		testsearch();
		
		
		
	}

	private static void testsearch() throws Exception {
		try {
			MarksheetBean bean = new  MarksheetBean();
			bean.setName("harshit");
			
			 List<MarksheetBean> list = model.search(bean, 0, 0);
			 
			 Iterator it =  list.iterator();
			 while(it.hasNext()) {
				 bean = (MarksheetBean)it.next();
				 
				 System.out.println(bean.getId());
				 System.out.println(bean.getRollno());
				 System.out.println(bean.getStudentId());
				 System.out.println(bean.getName());
				 System.out.println(bean.getPhysics());
				 System.out.println(bean.getChemistry());
				 System.out.println(bean.getMaths());
				 System.out.println(bean.getCreatedBy());
				 System.out.println(bean.getModifiedBy());
				 System.out.println(bean.getCreatedDateTime());
				 System.out.println(bean.getModifiedDateTime());
				 
			 }
			 
			 }catch(ApplicationException e) {
				 e.printStackTrace();
			 }
	}
		
			
		

	private static void testfindBypk() throws Exception {
		MarksheetBean bean = model.findByPk(2);
		
		if(bean != null) {
			System.out.println(bean.getId());
			System.out.println(bean.getName());
			System.out.println(bean.getRollno());
			System.out.println(bean.getPhysics());
			System.out.println(bean.getChemistry());
			System.out.println(bean.getMaths());
			
		
	}else {
		System.out.println("findbypk is invalid");
	}	
	}
	
//	if(bean != null) {
//		
//	}
//		System.out.println(bean.getId());
//		System.out.println(bean.getName());
//		System.out.println(bean.getRollno());
//		System.out.println(bean.getPhysics());
//		System.out.println(bean.getChemistry());
//		System.out.println(bean.getMaths());
//		
//}}
	
	

	private static void testfindByroll() throws Exception {
		
			MarksheetBean bean = model.findByRollNo("33");
			
			if(bean == null) {
				System.out.println("rollno is invalid");
			}
			System.out.println(bean.getId());
			System.out.println(bean.getName());
			System.out.println(bean.getPhysics());
			System.out.println(bean.getChemistry());
	}
		
	private static void testdelete() throws  Exception {
		
		try {
		MarksheetBean bean = new MarksheetBean();
		
		long pk = 1L;
		bean.setId(1);
		model.delete(bean);
		
		MarksheetBean deletebean = model.findByPk(pk);
		
		if(deletebean != null) {
			System.out.println("delete id is sucessfull");
			
		}else {
			System.out.println("id is not delete fail");
		
		}
		
		}catch(ApplicationException e) {
			e.printStackTrace();
		}
		
		
	}

	private static void testupdate() throws Exception {
		try {
			MarksheetBean bean = model.findByPk(1L);
			bean.setRollno("177");
			bean.setStudentId(2);
			bean.setName("harshit");
			bean.setPhysics(99);
			bean.setChemistry(66);
			bean.setMaths(66);
			bean.setCreatedBy("root");
			bean.setModifiedBy("root");
			bean.setCreatedDateTime(new Timestamp(new Date().getTime()));
			bean.setModifiedDateTime(new Timestamp(new Date().getTime()));
			
			model.update(bean);
			
			
		} catch (ApplicationException e) {
			e.printStackTrace();
			throw new ApplicationException("Exception : Exception is getting pk" + e.getMessage());
		}
		
	}

	private static void testadd() throws Exception {
		try {
			MarksheetBean bean = new MarksheetBean();
			bean.setRollno("33");
			bean.setStudentId(3);
			bean.setName("saket");
			bean.setPhysics(23);
			bean.setChemistry(55);
			bean.setMaths(66);
			bean.setCreatedBy("root");
			bean.setModifiedBy("root");
			bean.setCreatedDateTime(new Timestamp(new Date().getTime()));
			bean.setModifiedDateTime(new Timestamp(new Date().getTime()));
			
			long id = model.add(bean);
			System.out.println("record is addd" + id);
			
		} catch (ApplicationException e) {
			e.printStackTrace();
		}
		
		
		
	}

}
