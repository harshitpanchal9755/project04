package in.co.rays.proj4.test;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Date;

import in.co.rays.proj4.bean.TimetableBean;
import in.co.rays.proj4.exception.ApplicationException;
import in.co.rays.proj4.exception.DataBaseException;
import in.co.rays.proj4.model.TimetableModel;

public class TestTimetableModel {
	static TimetableModel model = new TimetableModel();

	public static void main(String[] args) throws Exception {

//		testadd();
		testupdate();
//		testdelete();
//		testnextpk();
//		testfindBYpk();
	}

	private static void testfindBYpk() {
		try {
			TimetableBean bean = model.findBYpk(1L);
			
			if(bean == null) {
				System.out.println("findby pk is add");
				
			}
			System.out.println(bean.getId());
			System.out.println(bean.getCoursename());
			System.out.println(bean.getCourseid());
		
	}catch(ApplicationException e) {
		e.printStackTrace();
	}
	}

	private static void testnextpk() throws DataBaseException {
		TimetableBean bean = new TimetableBean();
		int i = model.nextpk();
		System.out.println("nextpk is add" + i);
		
	}

	private static void testdelete() throws ApplicationException {
     
		TimetableBean bean = new TimetableBean();
		
		long pk = 1L;
		bean.setId(pk);
		model.delete(bean);
		
		TimetableBean deletebean = model.findBYpk(pk);
		
		if(deletebean == null) {
			System.out.println("id is delete");
	
		}else {
			System.out.println("id is not delete");
		}
		
	}

	private static void testupdate() throws Exception {
	TimetableBean bean = new TimetableBean();
	SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
	bean.setId(1);
	bean.setSemester("9");
	bean.setDescription("mca collage");
	bean.setExamdate(sdf.parse("11-09-2026"));
	bean.setExamtime("2:00");
	bean.setCourseid(3);
	bean.setCoursename("c++");
	bean.setSubjectid(2);
	bean.setSubjectname("mca");
	bean.setCreatedBy("root");
	bean.setModifiedBy("root");
	bean.setCreatedDateTime(new Timestamp(new Date().getTime()));
	bean.setModifiedDateTime(new Timestamp(new Date().getTime()));
		model.update(bean);
	}

	private static void testadd() throws Exception {
		try {
			SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
			TimetableBean bean = new TimetableBean();

			bean.setSemester("8");
			bean.setDescription("collage");
			bean.setExamdate(sdf.parse("10-09-2026"));
			bean.setExamtime("200");
			bean.setCourseid(3);
			bean.setCoursename("Iwt");
			bean.setSubjectid(3);
			bean.setSubjectname("aws");
			bean.setCreatedBy("root");
			bean.setModifiedBy("root");
			bean.setCreatedDateTime(new Timestamp(new Date().getTime()));
			bean.setModifiedDateTime(new Timestamp(new Date().getTime()));

			model.add(bean);

		} catch (ApplicationException e) {
			e.printStackTrace();
		}
	}

}
