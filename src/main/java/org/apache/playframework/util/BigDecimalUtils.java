package org.apache.playframework.util;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Objects;

public class BigDecimalUtils {

	/**
	 * 相除，返回百分比
	 * @param divisor 除数
	 * @param dividend 被除数
	 * @param defaultValue 默认值
	 * @return
	 */
	public static Double divideRatio(BigDecimal divisor, BigDecimal dividend, Number defaultValue) {
		Double ratio = null;
		if (divisor != null && dividend != null && dividend.doubleValue() > 0) {
			ratio = divisor.divide(dividend, 2, BigDecimal.ROUND_HALF_UP).multiply(new BigDecimal(100)).doubleValue();
		} 
		if (ratio == null && defaultValue != null) {
			ratio = defaultValue.doubleValue();
		}
		return ratio;
	}

    /**
     * 相除，返回百分比
     * @param divisor 除数
     * @param dividend 被除数
     * @return
     */
	public static Double divideRatio(BigDecimal divisor, BigDecimal dividend) {
		return divideRatio(divisor, dividend, null);
	}
 

	/**
	 * 相除，如果vlaue为空，默认值有值，就返回默认值
	 * @param divisor 除数
	 * @param dividend 被除数
	 * @param scale 要返回的BigDecimal商的大小
	 * @param defaultValue 默认值
	 * @return
	 */
	public static BigDecimal divide(BigDecimal divisor, BigDecimal dividend, int scale, Number defaultValue) {
		BigDecimal result = null;
		if (divisor != null && dividend != null && dividend.doubleValue() > 0) {
			result = divisor.divide(dividend, scale, BigDecimal.ROUND_HALF_UP);
		}
		if (result == null && defaultValue != null) {
			result = new BigDecimal(String.valueOf(defaultValue));
		}
		return result;
	}

	/**
	 * 相除
	 * @param divisor 除数
	 * @param dividend 被除数
	 * @param scale 要返回的BigDecimal商的大小
	 * @return
	 */
	public static BigDecimal divide(BigDecimal divisor, BigDecimal dividend, int scale) {
		return divide(divisor, dividend,  scale, null);
	}
	
	/**
	 * 相除
	 * @param divisor 除数
	 * @param dividend 被除数
	 * @return
	 */
	public static BigDecimal divide(BigDecimal divisor, BigDecimal dividend) {
		return divide(divisor, dividend,  2, null);
	}
	
	/**
	 * 把所有的值相加，如果vlaue为空，默认值有值，就返回默认值
	 * @param values 
	 * @param defaultValue 默认值
	 * @return
	 */
	public static BigDecimal add(BigDecimal[] values, Number defaultValue) {
		BigDecimal result = null;
		if (values != null && values.length > 0) {
			//循环数组，有值不为空才 初始化 result
			for (BigDecimal value : values) {
				if (value != null) {
					result = new BigDecimal(0);
					break;
				}
			}
			for (BigDecimal value : values) {
				if (value != null) {
					result = result.add(value);
				}
			}
		}
		if (result == null && defaultValue != null) {
			result = new BigDecimal(String.valueOf(defaultValue));
		}
		return result;
	}
	
	/**
	 * 把所有的值相加
	 * @param values 
	 * @return
	 */
	public static BigDecimal add(BigDecimal[] values) {
		return add(values, null);
	}

	/**
	 * 把object转换成 BigDecimal, 如果vlaue为空，默认值有值，就返回默认值
	 * @param value
	 * @param defaultValue 默认值
	 * @return
	 */
	public static BigDecimal valueOf(Object value, Number defaultValue) {
		BigDecimal ret = null;
		if (value != null) {
			if (value instanceof BigDecimal) {
				ret = (BigDecimal) value;
			} else if (value instanceof String) {
				ret = new BigDecimal((String) value);
			} else if (value instanceof BigInteger) {
				ret = new BigDecimal((BigInteger) value);
			} else if (value instanceof Number) {
				ret = new BigDecimal(String.valueOf(value));
			} else {
				throw new ClassCastException("Not possible to coerce [" + value + "] from class " + value.getClass() + " into a BigDecimal.");
			}
		} else {
			if (defaultValue != null) {
				ret = new BigDecimal(String.valueOf(defaultValue));
			}
		}
		return ret;
	}
	
	/**
	 * 把object转换成 BigDecimal，默认值为null 
	 * @param value
	 * @return
	 */
	public static BigDecimal valueOf(Object value) {
		return valueOf(value, null);
	}


	public static boolean equals(BigDecimal value1, BigDecimal value2) {
		if (value1 == null || value2 == null) {
			return false;
		}
		return value1.compareTo(value2) == 0;
	}
	
	public static void main(String[] args) {
		System.out.println(valueOf(null));
		System.out.println(add(new BigDecimal[]{null,null}, new BigDecimal(5)));
		System.out.println(divide(new BigDecimal(10), new BigDecimal(3), 4));
		System.out.println(divideRatio(new BigDecimal(3), new BigDecimal(10), 0));

		System.out.println(Objects.equals(new BigDecimal(0), new BigDecimal(0.00)));
	}
}
