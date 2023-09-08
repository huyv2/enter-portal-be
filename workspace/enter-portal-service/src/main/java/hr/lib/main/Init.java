package hr.lib.main;

import hr.lib.cache.CacheManager;
import hr.lib.constant.CacheConstant;

public class Init {
	public static void initCache() {
		if (CacheManager.isRunning() && CacheManager.getInstance().getSize() == 0) {
			CacheManager.getInstance().createCache(CacheConstant.APPLICATION_SYSTEM);
			CacheManager.getInstance().createCache(CacheConstant.LITERAL_INIT_CLASS);
			CacheManager.getInstance().createCache(CacheConstant.APPLICATION_PROPERTIES);
			CacheManager.getInstance().createCache(CacheConstant.CONTROLLER_URL_PROPERTIES);
			CacheManager.getInstance().createCache(CacheConstant.REQUEST_URL_PROPERTIES);
			CacheManager.getInstance().createCache(CacheConstant.LITERAL_REQUEST_DTO_CONFIGURATION);
			CacheManager.getInstance().createCache(CacheConstant.LITERAL_BEAN_CLASS);
			CacheManager.getInstance().createCache(CacheConstant.LITERAL_REQUEST_CLASS);
			CacheManager.getInstance().createCache(CacheConstant.LITERAL_COMMON_CLASS);
			CacheManager.getInstance().createCache(CacheConstant.LITERAL_INSTANCE_CONFIGURATION);
			CacheManager.getInstance().init();
		}
	}
}
