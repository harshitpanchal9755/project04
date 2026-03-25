package in.co.rays.proj4.test;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import in.co.rays.proj4.bean.CourseBean;
import in.co.rays.proj4.bean.SubjectBean;
import in.co.rays.proj4.exception.ApplicationException;
import in.co.rays.proj4.exception.DuplicateException;
import in.co.rays.proj4.model.SubjectModel;

public class TestSubjectModel {
	static SubjectModel model = new SubjectModel();

	public static void main(String[] args) throws Exception {

//		testadd();
//		testupdate();
//		testdelete();
//		testfindBypk();
//		testfindByName();
		testsearch();

	}

	private static void testsearch() throws Exception {
		SubjectBean bean = new SubjectBean();
		List<SubjectBean> list = model.search(bean, 0, 0);
		
		Iterator it = list.iterator();
		
		while(it.hasNext()) {
			bean = (SubjectBean)it.next();
			System.out.println(bean.getId());
			System.out.println(bean.getName());
			System.out.println(bean.getCourseId());
			System.out.println(bean.getCourseName());
			System.out.println(bean.getDescription());
			System.out.println(bean.getCreatedBy());
			System.out.println(bean.getModifiedBy());
			System.out.println(bean.getCreatedDateTime());
			System.out.println(bean.getModifiedDateTime());
		}
		
	}

	private static void testfindByName() throws Exception {
		
		try {
		SubjectBean bean = model.findByName("vijaythalapati");

		if (bean == null) {
			System.out.println("findByName is add");

		}

		System.out.println(bean.getId());
		System.out.println(bean.getCourseId());
		System.out.println(bean.getDescription());
		System.out.println(bean.getCreatedDateTime());
		
		} 
		
		catch(ApplicationException e) {
			throw new ApplicationException("Exception : Exception is findbynam" + e.getMessage());
		}
	}

	private static void testfindBypk() throws Exception {
		SubjectBean bean = model.findByPk(2L);

		if (bean == null) {
			System.out.println("findbypk is add");
		}

		System.out.println(bean.getId());
		System.out.println(bean.getName());
		System.out.println(bean.getCourseId());
		System.out.println(bean.getCreatedDateTime());

	}

	private static void testdelete() throws ApplicationException {
		SubjectBean bean = new SubjectBean();
		long pk = 1L;
		bean.setId(pk);
		model.delete(bean);

		SubjectBean deletebean = model.findByPk(pk);

		if (deletebean == null) {
			System.out.println("id is delete");

		}

	}

	private static void testupdate() throws Exception {
		SubjectBean bean = model.findByPk(1L);
		bean.setName("vijay");
		model.update(bean);

	}

	private static void testadd() {
		SubjectBean bean = new SubjectBean();

		bean.setName("anshika Singh");
		bean.setCoursId(4);
		bean.setCourseName("php");
		bean.setDescription("yes php developer");
		bean.setCreatedBy("root");
		bean.setModifiedBy("root");
		bean.setCreatedDateTime(new Timestamp(new Date().getTime()));
		bean.setModifiedDateTime(new Timestamp(new Date().getTime()));

		try {
			long i = model.add(bean);
			System.out.println("record is add" + i);
		} catch (ApplicationException | DuplicateException e) {
			e.printStackTrace();
		}
	}

}
