package in.co.rays.proj4.test;

import java.sql.Timestamp;
import java.util.Date;

import in.co.rays.proj4.bean.LoginAttemptBean;
import in.co.rays.proj4.exception.ApplicationException;
import in.co.rays.proj4.exception.DuplicateException;
import in.co.rays.proj4.model.LoginAttemptModel;

public class TestLoginAttemptModel {
	
	static LoginAttemptModel model = new LoginAttemptModel();
	
	public static void main(String[] args) throws ApplicationException, DuplicateException {
		testadd();
	}

	private static void testadd() throws ApplicationException, DuplicateException {
		LoginAttemptBean bean = new LoginAttemptBean();
		bean.setId(1);
		bean.setAttemptcode("LogC001");
		bean.setAttempttime(new Timestamp(new Date().getTime()));
		bean.setUsername("anuroop");
		bean.setStatus("login successfull");
		long id = model.add(bean);
		
		System.out.println("record is add" + id);
		
	}

}
