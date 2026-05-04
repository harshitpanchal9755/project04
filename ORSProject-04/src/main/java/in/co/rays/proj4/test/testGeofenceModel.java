package in.co.rays.proj4.test;

import java.sql.Timestamp;
import java.util.Date;

import in.co.rays.proj4.bean.GeofenceBean;
import in.co.rays.proj4.exception.ApplicationException;
import in.co.rays.proj4.exception.DuplicateException;
import in.co.rays.proj4.model.GeofenceModel;

public class testGeofenceModel {
	
	static GeofenceModel model = new GeofenceModel();
	
	public static void main(String[] args) throws ApplicationException, DuplicateException {
		
		testadd();
		
	}

	private static void testadd() throws ApplicationException, DuplicateException {
		GeofenceBean bean = new  GeofenceBean();
		bean.setId(2);
		bean.setGeoFenceCode("gf002");
		bean.setLocationName("indorezone");
		bean.setRadius("500");
		bean.setStatus("active");
		bean.setCreatedBy("harshitpanchal@gamail.com");
		bean.setModifiedBy("harshitpanchal@gamail.com");
		bean.setCreatedDateTime(new Timestamp(new Date().getTime()));
		bean.setModifiedDateTime(new Timestamp(new Date().getTime()));
		
		long id = model.add(bean);
		
		System.out.println("record is addd" + id);
		
		
	}

}
