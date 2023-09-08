package hr.lib.factory.literal;

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

public class LiteralInstanceFactory extends BaseFactory {

	@SuppressWarnings({ "rawtypes", "deprecation" })
	@Override
	public void init() throws Exception {
		Cache configurationCache = (Cache) CacheManager.getInstance().getCache(CacheConstant.LITERAL_INSTANCE_CONFIGURATION);
		
		ConcurrentHashMap<String, Object> cacheMaps = (ConcurrentHashMap<String, Object>) configurationCache.getAll();
		for(Map.Entry<String, Object> cacheMap : cacheMaps.entrySet()) {
			String key = cacheMap.getKey();
			LiteralVo literalVo = (LiteralVo) configurationCache.get(key);
			String packageName = literalVo.getPackageName();
			Object supperClass = literalVo.getSupperClass();
			
			List<Class> initLiteralList = FileUtil.getClassesV2(packageName, (Class<?>)supperClass);
			
			Cache literalCommonCache = (Cache) CacheManager.getInstance().getCache(CacheConstant.LITERAL_COMMON_CLASS);
			for(Class literal : initLiteralList) {
				if (Modifier.isAbstract(literal.getModifiers())
						|| Modifier.isInterface(literal.getModifiers())) {
					continue;
				}
				literalCommonCache.put(literal.getName(), literal.newInstance());
			}
		}
	}

	@Override
	public void destroy() throws Exception {
		
	}
	
	public static Object getInstanceByClassName(String className) {
		Cache literalCommonCache = (Cache) CacheManager.getInstance().getCache(CacheConstant.LITERAL_COMMON_CLASS);
		Object literalClass = literalCommonCache.get(className);
		return literalClass;
	}
}
