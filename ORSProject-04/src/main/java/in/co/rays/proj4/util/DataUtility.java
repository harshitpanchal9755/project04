package in.co.rays.proj4.util;

import java.sql.Time;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DataUtility {

	public static final String APP_DATE_FORMAT = "yyyy-MM-dd";

	public static final String APP_TIME_FORMAT = "dd-MM-yyyy HH:mm:ss";

	private static final SimpleDateFormat formatter = new SimpleDateFormat(APP_DATE_FORMAT);

	private static final SimpleDateFormat timeFormatter = new SimpleDateFormat(APP_TIME_FORMAT);

	public static String getString(String val) {
		if (DataValidator.isNotNull(val)) {
			return val.trim();
		} else {
			return val;
		}
	}

	public static String getStringData(Object val) {
		if (val != null) {
			return val.toString();
		} else {
			return "";
		}
	}

	public static int getInt(String val) {
		if (DataValidator.isInteger(val)) {
			return Integer.parseInt(val);
		} else {
			return 0;
		}
	}

	public static long getLong(String val) {
		if (DataValidator.isLong(val)) {
			return Long.parseLong(val);
		} else {
			return 0;
		}
	}

	public static Date getDate(String val) {
		Date date = null;
		try {
			date = formatter.parse(val);
		} catch (Exception e) {

		}
		return date;
	}

	public static String getDateString(Date date) {
		try {
			return formatter.format(date);
		} catch (Exception e) {
		}
		return "";
	}

	public static Timestamp getTimestamp(String val) {
		Timestamp timeStamp = null;
		try {
			timeStamp = new Timestamp((timeFormatter.parse(val)).getTime());
		} catch (Exception e) {
			return null;
		}
		return timeStamp;
	}

	public static Timestamp getTimestamp(long l) {
		Timestamp timeStamp = null;
		try {
			timeStamp = new Timestamp(l);
		} catch (Exception e) {
			return null;
		}
		return timeStamp;
	}

	public static Timestamp getCurrentTimestamp() {
		Timestamp timeStamp = null;
		try {
			timeStamp = new Timestamp(new Date().getTime());
		} catch (Exception e) {
		}
		return timeStamp;

	}

	public static long getTimestamp(Timestamp tm) {
		try {
			return tm.getTime();
		} catch (Exception e) {
			return 0;
		}
	}

	public static void main(String[] args) {
		System.out.println("getString :");
		System.out.println("original string : hello " + getString("      ram   "));
		
		System.out.println("getString : ");
		System.out.println("object val : abc :" + getString("123"));
		
		System.out.println("integer : ");
		System.out.println("valid integer 123 : " + getInt("123"));
		System.out.println("invalid integer abc : " + getInt("abc"));
	
		System.out.println("long : ");
		System.out.println("valid long : 1234324323" + getLong("24563545"));
		System.out.println("invalid long: abc" + getLong("abc"));
		
		System.out.println("getString :");
		String datestr = "2007-09-22";
		System.out.println(datestr);
		
		System.out.println("getDate :"); 
		System.out.println("valid is date is String : " + getDateString(new Date()));
		
		System.out.println("getTimeStamp :");
		String datestr1 = "24-09-2008: 10:43:20";
		System.out.println("valid is 24-09-2008: 10:43:20" + getTimestamp(datestr1));
		
		
	}
}
