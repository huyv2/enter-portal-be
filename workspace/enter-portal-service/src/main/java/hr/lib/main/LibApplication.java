package hr.lib.main;

import java.io.File;
import java.lang.reflect.Modifier;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import javax.servlet.http.HttpServlet;

import org.apache.catalina.Context;
import org.apache.catalina.LifecycleException;
import org.apache.catalina.Wrapper;
import org.apache.catalina.startup.Tomcat;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import hr.lib.cache.Cache;
import hr.lib.cache.CacheManager;
import hr.lib.cache.object.ExpireObject;
import hr.lib.constant.CacheConstant;
import hr.lib.constant.ParamConstant;
import hr.lib.factory.BaseFactory;
import hr.lib.factory.threadpool.ExecutorThreadPoolFactory;
import hr.lib.util.FileUtil;
import hr.lib.util.ResourceUtil;

public class LibApplication {
	public final static Logger log = LogManager.getLogger(LibApplication.class);
	
	@SuppressWarnings({ "rawtypes", "deprecation" })
	public static void run() {
		try {
			
			Init.initCache();
			
			Cache applicationSystemCache = (Cache) CacheManager.getInstance().getCache(CacheConstant.APPLICATION_SYSTEM);
			Cache literalClassCache = (Cache) CacheManager.getInstance().getCache(CacheConstant.LITERAL_INIT_CLASS);
			
			// Have to load in the main
			ResourceUtil.loadApplicationProperties();
			ResourceUtil.loadUrlPatternProperties();
			//
			
	        String serverPort = ResourceUtil.getApplicationConfiguration("server.port");
	        if(serverPort == null || serverPort.isEmpty()) {
	        	serverPort = ParamConstant.DEFAULT_PORT;
	        }
			
			Tomcat tomcat = new Tomcat();
			applicationSystemCache.put(ParamConstant.TOMCAT_WEB_SERVER, tomcat);
			//tomcat.setBaseDir("temp");
			tomcat.setPort(Integer.valueOf(serverPort));
			
			String contextPath = "";
			String docBase = new File(".").getAbsolutePath();
			Context context = tomcat.addContext(contextPath, docBase);
			String servletName = "";
			String urlPattern = ""; //ResourceUtil.getUrlPatternConfigurationByControllerName("TestServlet");
			
			String rootPackage = ParamConstant.ROOT_PACKAGE;
			
			List<Class> servletLiteralList = FileUtil.getClassesV2(rootPackage, HttpServlet.class);
			for(Class literal : servletLiteralList) {
				if (Modifier.isAbstract(literal.getModifiers())
						|| Modifier.isInterface(literal.getModifiers())) {
					continue;
				}
				servletName = literal.getName();
				Wrapper wrapper = tomcat.addServlet(contextPath, servletName, servletName);
				wrapper.setAsyncSupported(true);
				urlPattern = ResourceUtil.getUrlPatternConfigurationByControllerName(literal.getName());
				context.addServletMappingDecoded(urlPattern, servletName);
			}
			
			List<Class> initLiteralList = FileUtil.getClassesV2(rootPackage, BaseFactory.class);
			for(Class literal : initLiteralList) {
				if (Modifier.isAbstract(literal.getModifiers())
						|| Modifier.isInterface(literal.getModifiers())) {
					continue;
				}
				literalClassCache.put(literal.getName(), literal.newInstance());
				/*BaseFactory initBase = (BaseFactory) literalClassCache.get(literal.getName());
				initBase.initBase();*/
			}
			ConcurrentHashMap<String, Object> cacheMaps = (ConcurrentHashMap<String, Object>) literalClassCache.getAll();
			for(Map.Entry<String, Object> cacheMap : cacheMaps.entrySet()) {
				ExpireObject expireOjbect = (ExpireObject) cacheMap.getValue();
				BaseFactory factoryBase = (BaseFactory) expireOjbect.getValueObject();
				factoryBase.initBase();
			}
			
			ExecutorThreadPoolFactory.init();
			
			if (Runtime.getRuntime() != null) {
				Runtime.getRuntime().addShutdownHook(new Thread() {
					public void run() {
						Cache applicationSystemCache = (Cache) CacheManager.getInstance().getCache(CacheConstant.APPLICATION_SYSTEM);
						Tomcat tomcat = (Tomcat) applicationSystemCache.get(ParamConstant.TOMCAT_WEB_SERVER);
						
						Cache literalClassCache = (Cache) CacheManager.getInstance().getCache(CacheConstant.LITERAL_INIT_CLASS);
						ConcurrentHashMap<String, Object> cacheMaps = (ConcurrentHashMap<String, Object>) literalClassCache.getAll();
						for(Map.Entry<String, Object> cacheMap : cacheMaps.entrySet()) {
							String key = cacheMap.getKey();
							BaseFactory initBase = (BaseFactory) cacheMap.getValue();
							initBase.destroyBase();
							literalClassCache.remove(key);
						}
						try {
							CacheManager.getInstance().destroy();
							ExecutorThreadPoolFactory.shutdown();
							tomcat.stop();
						} catch (LifecycleException e) {
							
						}
					}
				});
			}
			
			tomcat.start();
			tomcat.getServer().await();
		} catch(Exception e) {
			log.error(e);
		}
	}
}
