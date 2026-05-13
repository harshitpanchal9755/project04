package in.co.rays.proj4.test;
import in.co.rays.proj4.bean.BatchProcessingBean;
import in.co.rays.proj4.exception.ApplicationException;
import in.co.rays.proj4.exception.DuplicateException;
import in.co.rays.proj4.model.BatchProcessingModel;

public class TestBatchProcessingModel {
	static BatchProcessingModel model = new  BatchProcessingModel();
	
	public static void main(String[] args) throws ApplicationException, DuplicateException {
		testadd();
	}

	private static void testadd() throws ApplicationException, DuplicateException {
		BatchProcessingBean bean = new BatchProcessingBean();
		bean.setBatchCode("B001");
		bean.setBatchName("user import");
		bean.setTotalRecords(500);
		bean.setStatus("complteted");
		
		long id = model.add(bean);
		System.out.println("record is add" + id);
		
		
	}

}
