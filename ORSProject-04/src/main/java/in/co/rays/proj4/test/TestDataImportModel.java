package in.co.rays.proj4.test;

import in.co.rays.proj4.bean.DataImportLogBean;
import in.co.rays.proj4.exception.ApplicationException;
import in.co.rays.proj4.exception.DuplicateException;
import in.co.rays.proj4.model.DataImportModel;

public class TestDataImportModel {
	
	static DataImportModel model = new DataImportModel();
	public static void main(String[] args) throws DuplicateException, ApplicationException {
		testadd();
		
	}

	private static void testadd() throws DuplicateException, ApplicationException {
		DataImportLogBean bean = new DataImportLogBean();
		bean.setId(2);
		bean.setImportlogcode("CoLog001");
		bean.setFilename("cscFile.yml");
		bean.setImportedby("Admin");
		bean.setStatus("Success");
		
		long id = model.add(bean);
		System.out.println("record is added" + id);
		
	}

}
