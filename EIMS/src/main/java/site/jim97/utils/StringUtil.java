package site.jim97.utils;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class StringUtil {

	public static boolean isEmpty(String str) {
		if (str == null || "".equals(str.trim())) {
			return true;
		} else {
			return false;
		}
	}

	public static boolean isNotEmpty(String str) {
		if ((str != null) && !"".equals(str.trim())) {
			return true;
		} else {
			return false;
		}
	}

	public static boolean isNotEmpty(Object str) {
		if ((str != null) && !"".equals(str.toString())) {
			return true;
		} else {
			return false;
		}
	}

	public static boolean isEmpty(Object str) {
		if ((str == null) || "".equals(str.toString())) {
			return true;
		} else {
			return false;
		}
	}

	/***
	 * 下划线命名转为驼峰命名
	 * 
	 * @param para
	 *            下划线命名的字符串
	 */
	public static String UnderlineToHump(String para) {
		StringBuilder result = new StringBuilder();
		String a[] = para.split("_");
		for (String s : a) {
			if (result.length() == 0) {
				result.append(s.toLowerCase());
			} else {
				result.append(s.substring(0, 1).toUpperCase());
				result.append(s.substring(1).toLowerCase());
			}
		}
		return result.toString();
	}

	/***
	 * 驼峰命名转为下划线命名
	 * 
	 * @param para
	 *            驼峰命名的字符串
	 */
	public static String HumpToUnderline(String para) {
		StringBuilder sb = new StringBuilder(para);
		int temp = 0;// 定位
		for (int i = 0; i < para.length(); i++) {
			if (Character.isUpperCase(para.charAt(i))) {
				sb.insert(i + temp, "_");
				temp += 1;
			}
		}
		return sb.toString().toUpperCase();
	}

	/**
	 * 获取属性名数组
	 * 
	 * @param t
	 * @return
	 */
	public static <T> String[] getFiledName(T t) {
		Field[] fields = t.getClass().getDeclaredFields();
		String[] fieldNamStrings = new String[fields.length];
		for (int i = 0; i < fieldNamStrings.length; i++) {
			fieldNamStrings[i] = fields[i].getName();
		}
		return fieldNamStrings;
	}

	/**
	 * 根据属性名 获取值（value）
	 * 
	 * @param name
	 * @param t
	 * @return
	 * @throws IllegalAccessException
	 */

	public static <T> Object getFieldValueByName(String name, T t) {

		String firstletter = name.substring(0, 1).toUpperCase();

		String getter = "get" + firstletter + name.substring(1);

		Method method;
		Object value;
		try {
			method = t.getClass().getMethod(getter, new Class[] {});
			value = method.invoke(t);
			return value;
		} catch (NoSuchMethodException e) {
			e.printStackTrace();
		} catch (SecurityException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			e.printStackTrace();
		}
		return null;
	}

}