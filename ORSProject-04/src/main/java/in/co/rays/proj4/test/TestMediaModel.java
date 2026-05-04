package in.co.rays.proj4.test;

import in.co.rays.proj4.bean.MediaBean;
import in.co.rays.proj4.exception.ApplicationException;
import in.co.rays.proj4.exception.DuplicateException;
import in.co.rays.proj4.model.MediaModel;

public class TestMediaModel {
	static MediaModel model = new MediaModel();
	
	public static void main(String[] args) throws Exception {
//		testadd();
		testdelete();
	}

	private static void testdelete() throws ApplicationException {
		MediaBean bean = new MediaBean();
		long pk = 2L;
		bean.setId(pk);
		
		model.delete(bean);
		
		MediaBean deletebean = model.findbypk(pk);
		
		if(deletebean != null) {
			System.out.println("id is deletesuccessfull");
			
		}
		
		
	}

	private static void testadd() throws Exception {
		MediaBean bean = new MediaBean();
		
		bean.setId(2);
		bean.setMediacode("102");
		bean.setMediatype("Cooovr3");
		bean.setUri("/vedio/pizza.vsido");
		bean.setStatus("vedio");
		
		long id = model.add(bean);
		System.out.println("record Is add" + id );
		
	}
	
	

}
