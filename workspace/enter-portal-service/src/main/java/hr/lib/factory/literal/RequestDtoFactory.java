package hr.lib.factory.literal;

import java.io.IOException;
import java.lang.reflect.Modifier;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import hr.lib.cache.Cache;
import hr.lib.cache.CacheManager;
import hr.lib.configuration.vo.LiteralVo;
import hr.lib.constant.CacheConstant;
import hr.lib.factory.BaseFactory;
import hr.lib.util.FileUtil;
import hr.lib.util.ResourceUtil;

public class RequestDtoFactory extends BaseFactory {

	@SuppressWarnings("rawtypes")
	@Override
	public void init() throws ClassNotFoundException, IOException, InstantiationException, IllegalAccessException {
		Cache configurationCache = (Cache) CacheManager.getInstance().getCache(CacheConstant.LITERAL_REQUEST_DTO_CONFIGURATION);
		
		ConcurrentHashMap<String, Object> cacheMaps = (ConcurrentHashMap<String, Object>) configurationCache.getAll();
		for(Map.Entry<String, Object> cacheMap : cacheMaps.entrySet()) {
			String key = cacheMap.getKey();
			LiteralVo literalVo = (LiteralVo) configurationCache.get(key);
			String packageName = literalVo.getPackageName();
			Object supperClass = literalVo.getSupperClass();
			
			List<Class> initLiteralList = FileUtil.getClassesV2(packageName, (Class<?>)supperClass);
			
			Cache literalCommonCache = (Cache) CacheManager.getInstance().getCache(CacheConstant.LITERAL_REQUEST_CLASS);
			for(Class literal : initLiteralList) {
				if (Modifier.isAbstract(literal.getModifiers())
						|| Modifier.isInterface(literal.getModifiers())) {
					continue;
				}
				literalCommonCache.put(ResourceUtil.getUrlPatternConfigurationByRequestName(literal.getName()), literal);
			}
		}
		
		ResourceUtil.clearRequestUrlPatternConfiguratione();
	}

	@Override
	public void destroy() {
		
	}
	
	public static Object getInstanceByClassName(String className) {
		Cache literalCommonCache = (Cache) CacheManager.getInstance().getCache(CacheConstant.LITERAL_REQUEST_CLASS);
		Object literalClass = literalCommonCache.get(className);
		return literalClass;
	}
}
