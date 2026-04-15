package in.co.rays.proj4.test;

import org.apache.log4j.Logger;

public class TestLog4J {
	
	public static void main(String[] args) {

		Logger log = Logger.getLogger(TestLog4J.class);

		log.debug("This is Debug...!!");
		log.info("This is Info...!!");
		log.warn("This is Warn...!!");
		log.error("This is Error...!!");
		log.fatal("This is Fatal...!!");

	}
	

}
