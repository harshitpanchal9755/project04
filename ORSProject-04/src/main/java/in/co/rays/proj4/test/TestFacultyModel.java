//package in.co.rays.proj4.test;
//
//import java.sql.Timestamp;
//import java.text.SimpleDateFormat;
//import java.util.Date;
//
//import in.co.rays.proj4.bean.FacultyBean;
//import in.co.rays.proj4.exception.ApplicationException;
//import in.co.rays.proj4.exception.DuplicateException;
//import in.co.rays.proj4.model.FacultyModel;
//
//public class TestFacultyModel {
//    static FacultyModel model = new FacultyModel();
//	public static void main(String[] args) {
//		
//		testadd();
////		//testDelete();
////		//testUpdate();
////		//testFindByPk();
////		//testFindByEmailId();
////		//testList();
////		//testsearch();
////		
//		
//	}
//	
//	
//	
//	
//public static void testadd() throws DuplicateException {
//		
//		try {
//			FacultyBean bean = new FacultyBean();
//			SimpleDateFormat sdf=new SimpleDateFormat("dd/MM/yyyy");
//			
//			System.out.println("2222222222");
//			bean.setFirstname("Rohan");
//			bean.setLastname("karma");
//			bean.setGender("male");
//			bean.setEmail("rohan@gmail.com");
//			bean.setMobileno("9087654329");
//			bean.setCollegeid(3);
//			bean.setCollegename("rpl");
//			bean.setCollegeid(4);
//			bean.setCollegename("m.com");
//			bean.setDob(sdf.parse("22/09/1999"));
//			bean.setSubjectid(5);
//			bean.setSubjectname("maths");
//			bean.setCreatedBy("admin");
//			bean.setModifiedBy("admin");
//			bean.setCreatedDateTime(new Timestamp(new Date().getTime()));
//			bean.setCreatedDateTime(new Timestamp(new Date().getTime()));
//			
//			model.add(bean);
//			System.out.println("success");
//		}catch(Exception e) {
//			e.printStackTrace();
//		}
//	}
//public static void testDelete(){
//	
//	try{
//		FacultyBean bean=new FacultyBean();
//		long pk=1L;
//		
//		bean.setId(pk);
//		model.delete(bean);
//		FacultyBean deletebean = model.findByPK(pk);
//		if(deletebean != null){
//			System.out.println("Test Delete fail");
//		}
//	}catch(ApplicationException e){
//		e.printStackTrace();
//	}
//}
//public static void testUpdate() {
//	try {
//		FacultyBean bean = model.findByPK(1L);
//		bean.setFirstname("akash");
//		//bean.setDescription("commerce");
//		model.update(bean);
//		System.out.println("update succ");
//		
//			/*
//			 * FacultyBean updateBean=model.findByPK(2L);
//			 * if(!"ram".equals(updateBean.getFirstName())){
//			 * System.out.println("test update fail"); }
//			 */
//		 
//		 
//	}catch(ApplicationException e) {
//		e.printStackTrace();
//	}catch(DuplicateRecordException e) {
//		e.printStackTrace();
//	}
//}
//public static void testFindByPk() {
//	try {
//		FacultyBean bean=new FacultyBean();
//		long pk=1L;
//		bean=model.findByPK(pk);
//		if(bean==null) {
//			System.out.println("test findbypk fail");
//		}
//		System.out.println(bean.getId());
//		System.out.println(bean.getFirstName());
//		System.out.println(bean.getLastName());
//		System.out.println(bean.getGender());
//		System.out.println(bean.getEmailId());
//		System.out.println(bean.getMobileNo());
//		System.out.println(bean.getCreatedBy());
//		System.out.println(bean.getModifiedBy());
//		System.out.println(bean.getCreatedDatetime());
//		System.out.println(bean.getModifiedDatetime());
//	}catch(ApplicationException e) {
//		e.printStackTrace();
//	}
//}
//public static void testFindByEmailId() {
//    try {
//        FacultyBean bean = new FacultyBean();
//        bean = model.findByEmailId("ram@gmail.com");
//        if (bean != null) {
//            System.out.println("Test Find By EmailId fail");
//        }
//        System.out.println(bean.getId());
//		System.out.println(bean.getFirstName());
//		System.out.println(bean.getLastName());
//		System.out.println(bean.getGender());
//		System.out.println(bean.getEmailId());
//		System.out.println(bean.getMobileNo());
//		System.out.println(bean.getCreatedBy());
//		System.out.println(bean.getModifiedBy());
//		System.out.println(bean.getCreatedDatetime());
//		System.out.println(bean.getModifiedDatetime());
//    } catch (ApplicationException e) {
//        e.printStackTrace();
//    }
//}
//
//public static void testList(){
//	 try{
//		FacultyBean bean = new FacultyBean();
//		 List list=new ArrayList();
//		 list=model.list(1,10);
//		 
//		 if(list.size() < 0){
//			 System.out.println("Test list fail");
//		 }
//		 Iterator it = list.iterator();
//		 while(it.hasNext()){
//			 bean=(FacultyBean) it.next();
//			 System.out.println(bean.getId());
//				System.out.println(bean.getFirstName());
//				System.out.println(bean.getLastName());
//				System.out.println(bean.getGender());
//				System.out.println(bean.getEmailId());
//				System.out.println(bean.getMobileNo());
//				System.out.println(bean.getCollegeId());
//				System.out.println(bean.getCollegeName());
//				System.out.println(bean.getCreatedBy());
//				System.out.println(bean.getModifiedBy());
//				System.out.println(bean.getCreatedDatetime());
//				System.out.println(bean.getModifiedDatetime());
//			 
//		 }
//	 }catch(ApplicationException e){
//		 e.printStackTrace();
//	 }
//	 
//}
//
//public static void testsearch() {
//	try {
//		FacultyBean bean = new FacultyBean();
//		List list = new ArrayList();
//		list=model.search(bean);
//		
//		Iterator it = list.iterator();
//		while(it.hasNext()) {
//			bean= (FacultyBean) it.next();
//			
//			System.out.println(bean.getId());
//			System.out.println(bean.getFirstName());
//			System.out.println(bean.getLastName());
//			System.out.println(bean.getGender());
//			System.out.println(bean.getEmailId());
//			System.out.println(bean.getMobileNo());
//			
//		}
//	}catch(ApplicationException e) {
//		e.printStackTrace();
//	}
//}
//	}
//}
