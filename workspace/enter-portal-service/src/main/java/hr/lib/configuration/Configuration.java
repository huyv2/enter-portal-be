package hr.lib.configuration;

import hr.lib.cache.Cache;
import hr.lib.cache.CacheManager;
import hr.lib.configuration.vo.LiteralVo;
import hr.lib.constant.CacheConstant;
import hr.lib.main.Init;

public class Configuration {
	private static Configuration configuration = new Configuration();
	
	private Configuration() {
		
	}
	
	public static Configuration getInstance() {
		Init.initCache();
		return configuration;
	}
	
	public void configureLiteralInstance(String packageName, Object supperClass) {
		LiteralVo classVo = new LiteralVo();
		classVo.setPackageName(packageName);
		classVo.setSupperClass(supperClass);
		
		Cache literalServiceLocatorCache = (Cache) CacheManager.getInstance().getCache(CacheConstant.LITERAL_INSTANCE_CONFIGURATION);
		literalServiceLocatorCache.put(packageName, classVo);
	}
	
	public void configureRequestDtoLoading(String packageName, Object supperClass) {
		LiteralVo classVo = new LiteralVo();
		classVo.setPackageName(packageName);
		classVo.setSupperClass(supperClass);
		
		Cache literalServiceLocatorCache = (Cache) CacheManager.getInstance().getCache(CacheConstant.LITERAL_REQUEST_DTO_CONFIGURATION);
		literalServiceLocatorCache.put(packageName, classVo);
	}
}
