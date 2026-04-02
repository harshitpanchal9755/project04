package in.co.rays.proj4.test;

import in.co.rays.proj4.bean.GymTrainerBean;
import in.co.rays.proj4.exception.ApplicationException;
import in.co.rays.proj4.model.GymTrainerModel;

public class TestGymTrainerModel {
	static GymTrainerModel model = new GymTrainerModel();

	public static void main(String[] args) throws Exception {
//
//		testadd();
//		testupdate();
//		testdelete();
//		testfindbypk();
		testfindbyname();

	}

	private static void testfindbyname() throws Exception {
GymTrainerBean bean = model.findByName("harsh");
		
		if(bean == null) {
			System.out.println("findbyok is invalid");
			
		}
		
		System.out.println(bean.getId());
		System.out.println(bean.getName());
		System.out.println(bean.getSpecialization());
		System.out.println(bean.getSalary());
		
	}

	private static void testfindbypk() throws Exception {
		GymTrainerBean bean = model.findBypk(1L);
		
		if(bean == null) {
			System.out.println("findbyok is invalid");
			
		}
		
		System.out.println(bean.getId());
		System.out.println(bean.getName());
		System.out.println(bean.getSpecialization());
		System.out.println(bean.getSalary());
		
	}

	private static void testdelete() {
		
		try {
			GymTrainerBean bean = new GymTrainerBean();
			long pk = 1L;
			bean.setId(pk);
			model.delete(bean);
			
			GymTrainerBean deletebean = model.findBypk(pk);
			
			if(deletebean != null) {
				System.out.println("deleted the id");
			}
		}catch(Exception e) {
			e.printStackTrace();
		}
		
	}

	private static void testupdate() throws Exception {
		try {
			
		
		GymTrainerBean bean = model.findBypk(1L);
		bean.setName("ram");
		model.update(bean);
		
		}catch(Exception e) {
			e.printStackTrace();
		}
	}

	private static void testadd() throws Exception {
		try {
		GymTrainerBean bean = new GymTrainerBean();
		bean.setName("shyam");
		bean.setSpecialization("fitness trainer");
		bean.setSalary(3000.00);
		long i = model.add(bean);
		System.out.println(i + "record is add");

	}catch(Exception e) {
		e.printStackTrace();
	}
	}

}
