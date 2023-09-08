package hr.lib.factory.bean;

import java.io.IOException;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.List;

import hr.lib.cache.Cache;
import hr.lib.cache.CacheManager;
import hr.lib.constant.CacheConstant;
import hr.lib.constant.ParamConstant;
import hr.lib.factory.BaseFactory;
import hr.lib.util.FileUtil;

public class BeanFactory extends BaseFactory {

	@SuppressWarnings({ "rawtypes", "deprecation", "unchecked" })
	@Override
	public void init() throws ClassNotFoundException, IOException, InstantiationException, IllegalAccessException {
		Cache literalBeanCache = (Cache) CacheManager.getInstance().getCache(CacheConstant.LITERAL_BEAN_CLASS);
		
		String rootPackage = ParamConstant.ROOT_PACKAGE;
		
		List<Class> initLiteralList = FileUtil.getClassesV2(rootPackage, Bean.class);
		
		List<Class> literalInterfaceList = new ArrayList<Class>();
		List<Class> literalNormalClass = new ArrayList<Class>();
		for(Class literal: initLiteralList) {
			if (Modifier.isInterface(literal.getModifiers())) {
				literalInterfaceList.add(literal);
			} else {
				literalNormalClass.add(literal);
			}
		}
		for(Class literalInterface : literalInterfaceList) {
			for(Class literal : literalNormalClass) {
				if (literalInterface.isAssignableFrom(literal)) {
					literalBeanCache.put(literalInterface.getName(), literal.newInstance());
					break;
				}
			}
		}
	}

	@Override
	public void destroy() {
		
	}
	
	public static Bean getInstanceByClassName(String className) {
		Cache literalBeanCache = (Cache) CacheManager.getInstance().getCache(CacheConstant.LITERAL_BEAN_CLASS);
		Bean bean = (Bean) literalBeanCache.get(className);
		return bean;
	}
}
