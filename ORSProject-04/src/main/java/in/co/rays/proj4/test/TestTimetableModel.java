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
			TimetableBean bean = model.findByPk(1L);
			
			if(bean == null) {
				System.out.println("findby pk is add");
				
			}
			System.out.println(bean.getId());
			System.out.println(bean.getCourseName());
			System.out.println(bean.getCourseId());
		
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
		
		TimetableBean deletebean = model.findByPk(pk);
		
		if(deletebean == null) {
			System.out.println("id is delete");
	
		}else {
			System.out.println("id is not delete");
		}
		
	}

	private static void testupdate() throws Exception {
		try {
			TimetableBean bean = new TimetableBean();
			SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
			
			bean.setId(1L);
			
			bean.setCourseId(6);
			bean.setCourseName("mca");
			bean.setSubjectId(5);
			bean.setSubjectName("java");
			bean.setExamTime("1 to 4 pm");
			bean.setExamDate(sdf.parse("22/08/2021"));
			model.update(bean);
		}catch(ApplicationException e) {
			e.printStackTrace();
		}
	
	}

	private static void testadd() throws Exception {
		try {

			TimetableBean bean = new TimetableBean();
			SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");

			bean.setSemester("4");
			bean.setDescription("collage timetable");
			bean.setExamDate(sdf.parse("10-09-2026"));
			bean.setExamTime("8:00 to 11:00");
			bean.setCourseId(4);
			bean.setCourseName("web development");
			bean.setSubjectId(4);
			bean.setSubjectName("html");
			bean.setCreatedBy("root");
			bean.setModifiedBy("root");
			bean.setCreatedDateTime(new Timestamp(new Date().getTime()));
			bean.setModifiedDateTime(new Timestamp(new Date().getTime()));

			long pk = model.add(bean);
			System.out.println("record is add" + pk);

		} catch (ApplicationException e) {
			e.printStackTrace();
		}
	}

}
