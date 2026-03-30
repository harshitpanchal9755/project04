package in.co.rays.proj4.test;

import in.co.rays.proj4.bean.LockerBean;
import in.co.rays.proj4.exception.ApplicationException;
import in.co.rays.proj4.exception.DuplicateException;
import in.co.rays.proj4.model.LockerModel;

public class TestLockerModel {
	static LockerModel model = new LockerModel();
	
	public static void main(String[] args) throws Exception{
//		testadd();
//		testupdate();
//		testdelete();
		testfindBypk();
		
	}

	private static void testfindBypk() throws ApplicationException {
		LockerBean bean = model.findBypk(2);
		
		if(bean == null) {
			System.out.println("findBypk is run");
		}
		
		System.out.println(bean.getId());
		System.out.println(bean.getLockerNumber());
		System.out.println(bean.getLockerType());
		System.out.println(bean.getAnnualFee());
		
		
	}

	private static void testdelete() throws Exception{
		LockerBean bean = new LockerBean();
		long pk = 1L;
		bean.setId(pk);
		model.delete(bean);
		
		LockerBean deletebean = model.findBypk(pk);
		
		if(deletebean != null) {
			System.out.println("id is delete");
			
		}
		
		
	}

	private static void testupdate() throws Exception {
		try {
			LockerBean bean = model.findBypk(1);
			bean.setLockerNumber("L102");
			model.update(bean);
		}catch(Exception e) {
			e.printStackTrace();
		}
		
		
	}

	private static void testadd() throws Exception {
		LockerBean bean = new LockerBean();
		bean.setLockerNumber("L103");
		bean.setLockerType("medium");
		bean.setAnnualFee(1500.00);
		
		long i = model.add(bean);
		System.out.println("record is add" + i);
		
	}

}
