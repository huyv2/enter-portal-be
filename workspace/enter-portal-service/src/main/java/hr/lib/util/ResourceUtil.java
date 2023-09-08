package hr.lib.util;

import java.util.Enumeration;
import java.util.ResourceBundle;

import hr.lib.cache.Cache;
import hr.lib.cache.CacheManager;
import hr.lib.constant.CacheConstant;

public class ResourceUtil {
	public static void loadApplicationProperties() {
		Cache applicationPropertiesCache = (Cache) CacheManager.getInstance().getCache(CacheConstant.APPLICATION_PROPERTIES);
		loadResourceToCache("lib-application", applicationPropertiesCache, null);
	}
	
	public static void loadUrlPatternProperties() {
		Cache controllerUrlPropertiesCache = (Cache) CacheManager.getInstance().getCache(CacheConstant.CONTROLLER_URL_PROPERTIES);
		Cache requestUrlPropertiesCache = (Cache) CacheManager.getInstance().getCache(CacheConstant.REQUEST_URL_PROPERTIES);
		loadResourceToCache("urlPattern", controllerUrlPropertiesCache, requestUrlPropertiesCache);
	}
	
	private static void loadResourceToCache(String fileName, Cache firstCache, Cache secondCache) {
		String key, value;
		ResourceBundle applicationResourceBundle = ResourceBundle.getBundle(fileName);
		Enumeration<?> bundleKeys = applicationResourceBundle.getKeys();
		while(bundleKeys.hasMoreElements()) {
			key = (String) bundleKeys.nextElement();
			value = applicationResourceBundle.getString(key);
			if (secondCache == null) {
				value = getRealValue(value);
				firstCache.put(key, value);
			} else {
				String[] keys = key.split(",");
				firstCache.put(keys[0], value);
				if (keys.length > 1) {
					secondCache.put(keys[1], value);
				}
			}
		}
	}

	private static String getRealValue(String value){
		String realValue = value;

		if (value != null && value.startsWith("$@")){
			String envVariableName = value.replaceAll("\\$\\@(.+?)", "$1");
			String envVariableValue = System.getenv(envVariableName);
			if (envVariableValue != null) {
				realValue = envVariableValue;
			}
		}

		return realValue;
	}
	
	public static String getApplicationConfiguration(String key) {
		String result = "";
		
		try {
			Cache applicationPropertiesCache = (Cache) CacheManager.getInstance().getCache(CacheConstant.APPLICATION_PROPERTIES);
			result = (String) applicationPropertiesCache.get(key);
		} catch(Exception e) {
			
		}
		
		return result;
	}
	
	public static String getUrlPatternConfigurationByControllerName(String key) {
		String result = "";
		
		try {
			Cache applicationPropertiesCache = (Cache) CacheManager.getInstance().getCache(CacheConstant.CONTROLLER_URL_PROPERTIES);
			result = (String) applicationPropertiesCache.get(key);
		} catch(Exception e) {
			
		}
		
		return result;
	}
	
	public static String getUrlPatternConfigurationByRequestName(String key) {
		String result = "";
		
		try {
			Cache requestUrlPropertiesCache = (Cache) CacheManager.getInstance().getCache(CacheConstant.REQUEST_URL_PROPERTIES);
			result = (String) requestUrlPropertiesCache.get(key);
		} catch(Exception e) {
			
		}
		
		return result;
	}
	
	public static String clearRequestUrlPatternConfiguratione() {
		String result = "";
		
		try {
			Cache requestUrlPropertiesCache = (Cache) CacheManager.getInstance().getCache(CacheConstant.REQUEST_URL_PROPERTIES);
			requestUrlPropertiesCache.removeAll();
		} catch(Exception e) {
			
		}
		
		return result;
	}
	
	public static String getApplicationConfigurationFromFile(String key) {
		String result = "";
		
		try {
			ResourceBundle applicationResourceBundle = ResourceBundle.getBundle("lib-application");
			result = (String) applicationResourceBundle.getString(key);
		} catch(Exception e) {
			
		}
		
		return result;
	}
}
