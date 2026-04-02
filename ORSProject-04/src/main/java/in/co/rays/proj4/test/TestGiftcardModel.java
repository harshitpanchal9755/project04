package in.co.rays.proj4.test;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.Date;

import in.co.rays.proj4.bean.GiftcardBean;
import in.co.rays.proj4.exception.ApplicationException;
import in.co.rays.proj4.exception.DuplicateException;
import in.co.rays.proj4.model.GiftcardModel;

public class TestGiftcardModel {
	static GiftcardModel model = new GiftcardModel();
	
	public static void main(String[] args) throws Exception{
	
//		testadd();
//		testupdate();
//		testdelete();
//		testfindBypk();
		testfindBycode();
		
		
		
	}

	private static void testfindBycode() throws Exception {
		try {
		GiftcardBean bean = model.findBycode("giftcard date ");
		if(bean == null) {
			System.out.println("findbycode is valid");
		}

		System.out.println(bean.getId());
		System.out.println(bean.getAmount());
		System.out.println(bean.getExpiredate());
		System.out.println(bean.getStatus());
		
		}
		catch(ApplicationException e) {
			e.printStackTrace();
			throw new ApplicationException("Exception is ");
		
		}
	
	}

	private static void testfindBypk() throws Exception {
		GiftcardBean bean = model.findBypk(1);
		
		if(bean == null) {
			System.out.println("find by pk is add");
			
		} else {
			System.out.println("invalid findbypk");
		}
		System.out.println(bean.getId());
		System.out.println(bean.getStatus());
		
		
	}

	private static void testdelete() throws ApplicationException {
		GiftcardBean bean = new GiftcardBean();
		long pk = 1L;
		bean.setStatus("giftcard date ");
		model.delete(bean);
		
		GiftcardBean deletebean = model.findBypk(pk);
		if(deletebean !=  null) {
			System.out.println("id is delete");
		}
		
		
		
	}

	private static void testupdate() throws Exception {
		GiftcardBean bean = model.findBycode("hello c0123");
		
		bean.setAmount(new BigDecimal("1200"));
		
		bean.setStatus("java in gift card");
		model.update(bean);
		
		
	}

	private static void testadd() throws Exception {
		GiftcardBean bean = new GiftcardBean();
		
		bean.setCode("xc001");
		bean.setAmount(new BigDecimal("1500.00"));
		bean.setExpiredate(new Timestamp(new Date().getTime()));
		bean.setStatus("giftcard date ");
		long i  = model.add(bean);
		System.out.println("record is add" + i);
		
		
	}

}
