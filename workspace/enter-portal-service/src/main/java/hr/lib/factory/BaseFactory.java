package hr.lib.factory;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public abstract class BaseFactory {
	protected final Logger log = LogManager.getLogger(this.getClass());
	
	protected String thePackage;
	protected Object supperClass;
	
	public void setThePackage(String thePackage) {
		this.thePackage = thePackage;
	}
	public void setSupperClass(Object supperClass) {
		this.supperClass = supperClass;
	}
	
	public void initBase() {
		try {
			init();
		} catch(Exception e) {
			log.error(e);
		}
	}
	public void destroyBase() {
		try {
			destroy();
		} catch(Exception e) {
			log.error(e);
		}
	}
	public abstract void init() throws Exception;
	public abstract void destroy() throws Exception;
}
