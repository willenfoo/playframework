package org.apache.playframework.util;

import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.function.Function;
import java.util.function.Supplier;

import org.apache.commons.beanutils.PropertyUtils;
import org.springframework.util.ReflectionUtils;
import org.springframework.util.StringUtils;

public class ReflectUtils {

	public static void setProperty(Object bean, String name, Object value) {
		if (value == null) {
			return ;
		}
		try {
			 if (!PropertyUtils.isWriteable(bean, name)) {
				 return ;
			 }
			Field field = null;
			try {
				field = bean.getClass().getDeclaredField(name);
			} catch (Exception e) {
				field = bean.getClass().getSuperclass().getDeclaredField(name);
			}
			 if (String.class.equals(field.getType())) {
				 PropertyUtils.setProperty(bean, name, new String(value.toString()));
			 } else if (Long.class.equals(field.getType())) {
				 PropertyUtils.setProperty(bean, name, new Long(value.toString()));
			 } else if (Integer.class.equals(field.getType())) {
				 PropertyUtils.setProperty(bean, name, new Integer(value.toString()));
			 } else if (Date.class.equals(field.getType())) {
				 if (value instanceof Date) {
					 PropertyUtils.setProperty(bean, name, value);
				 } else {
					 PropertyUtils.setProperty(bean, name, DateUtils.format(value.toString()));
				 }
			 } else if (BigDecimal.class.equals(field.getType())) {
				 PropertyUtils.setProperty(bean, name, new BigDecimal(value.toString()));
			 } else {
				 PropertyUtils.setProperty(bean, name, value);
			 }
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	
	public static <T> T getValue(List<?> list, String eqField, String eqValue, String returnField,Class<T> returnClass) {
		for (Object object : list) {
			Field field = ReflectionUtils.findField(object.getClass(), eqField, String.class);
			field.setAccessible(true);
			Object value = ReflectionUtils.getField(field, object);
			if (StringUtils.pathEquals(eqValue, String.valueOf(value))) {
				field = ReflectionUtils.findField(object.getClass(), returnField);
				field.setAccessible(true);
				return (T)ReflectionUtils.getField(field, object);
			}
		}
		return null;
	}

	public void setValue(List<?> sourceList, Supplier<Integer> sourceField, List<?> targetList, Function targetField, String[][] templates) {

	}
	
}
