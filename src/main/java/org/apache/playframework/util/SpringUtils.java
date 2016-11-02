package org.apache.playframework.util;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.BeanFactoryAware;

/**
 * spring bean工具类
 * @author willenfoo
 *
 */
public class SpringUtils implements BeanFactoryAware {
	
	private static BeanFactory beanFactory = null;

	public void setBeanFactory(BeanFactory factory) throws BeansException {
		beanFactory = factory;
	}

	public BeanFactory getBeanFactory() {
		return beanFactory;
	}


	/**
	 * 根据提供的bean名称得到相应的服务类
	 * @param servName bean名称
	 */
	public static Object getBean(String beanName) {
		return beanFactory.getBean(beanName);
	}

	/**
	 * 根据提供的bean名称得到对应于指定类型的服务类
	 * @param servName  bean名称
	 * @param clazz 返回的bean类型,若类型不匹配,将抛出异常
	 */
	public static <T> T getBean(String beanName, Class<T> clazz) {
		return beanFactory.getBean(beanName, clazz);
	}
	
	/**
	 * 判断bean是否存在
	 * @param beanName
	 * @return
	 */
	public static boolean containsBean(String beanName) {
		return beanFactory.containsBean(beanName);
	}
}
