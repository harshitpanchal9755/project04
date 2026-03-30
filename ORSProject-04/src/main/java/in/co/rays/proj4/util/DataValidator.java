package in.co.rays.proj4.util;

import java.util.Calendar;
import java.util.Date;

public class DataValidator {

	public static boolean isNull(String val) {
		if (val == null || val.trim().length() == 0) {
			return true;
		}
		return false;

	}

	public static boolean isNotNull(String val) {
		return isNull(val) == false;
	}

	public static boolean isInteger(String val) {
		if (isNotNull(val)) {
			try {
				Integer.parseInt(val);
				return true;

			} catch (NumberFormatException e) {
				return false;
			}
		} else {
			return false;
		}
	}

	public static boolean isLong(String val) {
		if (isNotNull(val)) {
			try {
				Long.parseLong(val);
				return true;
			} catch (NumberFormatException e) {
				return false;
			}
		} else {
			return false;
		}

	}

	public static boolean isEmail(String val) {

		String emailreg = "^[_A-Za-z0-9-]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";
		if (isNotNull(val)) {

			try {
				return val.matches(emailreg);
			} catch (NumberFormatException e) {
			}
		} else {
			return false;
		}
		return false;
	}

	public static boolean isName(String val) {

		String namereg = "^[^-\\s][\\p{L} .'-]+$";
		if (isNotNull(val)) {
			try {
				return val.matches(namereg);

			} catch (NumberFormatException e) {
				return false;
			}
		} else {
			return false;
		}
	}
	
	public static boolean isRollNo(String val) {
		
		String rollno = "[a-zA-Z]{2}[0-9]{3}";
		if(isNotNull(val)) {
			try {
				return val.matches(rollno);
			}catch(NumberFormatException e) {
			}
			}else {
				return false;
				
			}
		return false;
	}
	
	public static boolean isPassword(String val) {
		String password =  "(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=])(?=\\S+$).{8,12}";
		if(isNotNull(val)) {
			try {
				return val.matches(password);
				
				}catch(NumberFormatException e) {
					return false;
				}
			}else {
				return false;
		}
	}
	
	public static boolean isPasswordLength(String val) {
		if(isNotNull(val)&& val.length() > 8 && val.length() < 12) {
			return true;
		}
		return false;
	}
	
	public static boolean isPhoneNo(String val) {

		String phonereg = "^[6-9][0-9]{9}$";

		if (isNotNull(val)) {
			try {
				return val.matches(phonereg);
			} catch (NumberFormatException e) {
				return false;
			}

		} else {
			return false;
		}
	}

	public static boolean isPhoneLength(String val) {

		if (isNotNull(val) && val.length() == 10) {
			return true;
		} else {
			return false;
		}
	}

	public static boolean isDate(String val) {

		Date d = null;
		if (isNotNull(val)) {
			d = DataUtility.getDate(val);
		}
		return d != null;
	}

	public static boolean isSunday(String val) {

		Calendar cal = Calendar.getInstance();
		cal.setTime(DataUtility.getDate(val));
		int i = cal.get(Calendar.DAY_OF_WEEK);

		if (i == Calendar.SUNDAY) {
			return true;
		} else {
			return false;
		}
	}

	public static void main(String[] args) {
		System.out.println(DataValidator.isNull("") );
		
		System.out.println(DataValidator.isNotNull("ram"));
		System.out.println(DataValidator.isInteger("123"));
		System.out.println(DataValidator.isInteger("abx"));
		System.out.println(DataValidator.isLong("12345432"));
		System.out.println(DataValidator.isEmail("ram@gmail.com"));
		System.out.println(DataValidator.isName("ram"));
		System.out.println(DataValidator.isRollNo("AB123"));
		System.out.println(DataValidator.isPassword("Harshit123@"));
		System.out.println(DataValidator.isPhoneNo("9876987612"));
		System.out.println(DataValidator.isDate("22-08-2009"));
		System.out.println(DataValidator.isSunday("22-03-2026"));

	}

	

}
