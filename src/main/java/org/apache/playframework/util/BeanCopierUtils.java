package org.apache.playframework.util;

import org.springframework.cglib.beans.BeanCopier;
import org.springframework.cglib.beans.BeanMap;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 
 * 将beancopier做成静态类，方便拷贝 <br>
 * 创建日期：2015年12月1日 <br>
 * <b>Copyright 2015 UTOUU All Rights Reserved</b>
 * 
 * @author yushaojian
 * @since 1.0
 * @version 1.0
 */
public class BeanCopierUtils {

	/** 
	 *  
	 */
	public static Map<String, BeanCopier> beanCopierMap = new HashMap<String, BeanCopier>();

	/**
	 * @Title: copyProperties
	 * @Description: (bean属性转换)
	 * @param source
	 *            资源类
	 * @param  目标类
	 * @author yushaojian
	 * @date 2015年11月25日下午4:56:44
	 */
	public static Object copyProperties(Object source, Object target) {
		if (source == null || target == null) {
			return null;
		}
		String beanKey = generateKey(source.getClass(), target.getClass());
		BeanCopier copier = null;
		if (!beanCopierMap.containsKey(beanKey)) {
			copier = BeanCopier.create(source.getClass(), target.getClass(), false);
			beanCopierMap.put(beanKey, copier);
		} else {
			copier = beanCopierMap.get(beanKey);
		}
		copier.copy(source, target, null);
		return target;
	}

	public static <T> T mapToBean(Map map, T bean) {
		BeanMap beanMap = BeanMap.create(bean);
		beanMap.putAll(map);
		return bean;
	}
	
	
	@SuppressWarnings("unchecked")
	public static <T> T copyToObject(Object source, Class<T> clasz) {
		try {
			return (T)copyProperties(source, clasz.newInstance());
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}  
	}
	
	public static <T> List<T> copyToList(List<?> sourceList, Class<T> clasz) {
		if (sourceList != null) {
			List<T> listDto = new ArrayList<T>();
			for (Object source : sourceList) {
				T t;
				try {
					t = clasz.newInstance();
					copyProperties(source, t);
					listDto.add(t);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
			return listDto;
		}
		return null;
	}

	private static String generateKey(Class<?> class1, Class<?> class2) {
		return class1.toString() + class2.toString();
	}
	/*
	 * 注： (1)相同属性名，且类型不匹配时候的处理，ok，但是未满足的属性不拷贝；
	 * (2)get和set方法不匹配的处理，创建拷贝的时候报错，无法拷贝任何属性(当且仅当sourceClass的get方法超过set方法时出现)
	 * (3)BeanCopier 初始化例子：BeanCopier copier = BeanCopier.create(Source.class,
	 * Target.class, useConverter=true)
	 * 第三个参数userConverter,是否开启Convert,默认BeanCopier只会做同名，同类型属性的copier,否则就会报错.
	 * copier = BeanCopier.create(source.getClass(), target.getClass(), false);
	 * copier.copy(source, target, null); (4)修复beanCopier对set方法强限制的约束
	 * 改写net.sf.cglib.beans.BeanCopier.Generator.generateClass(ClassVisitor)方法
	 * 将133行的 MethodInfo write =
	 * ReflectUtils.getMethodInfo(setter.getWriteMethod()); 预先存一个names2放入 109
	 * Map names2 = new HashMap(); 110 for (int i = 0; i < getters.length; ++i)
	 * { 111 names2.put(setters[i].getName(), getters[i]); }
	 * 调用这行代码前判断查询下，如果没有改writeMethod则忽略掉该字段的操作，这样就可以避免异常的发生。
	 */

}