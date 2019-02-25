package site.jim97.utils;

import java.util.Date;
import java.util.Enumeration;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.beanutils.ConvertUtils;
import org.apache.commons.beanutils.converters.DateConverter;

import com.alibaba.fastjson.JSONObject;

public class HttpServletRequestUtil {

	public static int getInt(HttpServletRequest request, String name) {
		try {
			return Integer.decode(request.getParameter(name));
		} catch (Exception e) {
			return -1;
		}
	}

	public static long getLong(HttpServletRequest request, String name) {
		try {
			return Long.valueOf(request.getParameter(name));
		} catch (Exception e) {
			return -1;
		}
	}

	public static Double getDouble(HttpServletRequest request, String name) {
		try {
			return Double.valueOf(request.getParameter(name));
		} catch (Exception e) {
			return -1d;
		}
	}

	public static Boolean getBoolean(HttpServletRequest request, String name) {
		try {
			return Boolean.valueOf(request.getParameter(name));
		} catch (Exception e) {
			return false;
		}
	}

	public static String getString(HttpServletRequest request, String name) {
		try {
			String result = request.getParameter(name);
			if (result != null) {
				result = result.trim();
			}
			if ("".equals(result))
				result = null;
			return result;
		} catch (Exception e) {
			return null;
		}
	}

	public static void setSessionAttr(HttpServletRequest request, String name, Object value) {
		request.getSession().setAttribute(name, value);
	}

	public static Object getSessionAttr(HttpServletRequest request, String name) {
		return request.getSession().getAttribute(name);
	}

	public static JSONObject getJson(HttpServletRequest request) {
		JSONObject json = new JSONObject();
		Enumeration<String> parameterNames = request.getParameterNames();
		DateConverter convert = new DateConverter();// 写一个日期转换器
		String[] patterns = { "yyyyMMdd", "yyyy-MM-dd" };// 限定日期的格式字符串数组
		convert.setPatterns(patterns);
		ConvertUtils.register(convert, Date.class);
		while (parameterNames.hasMoreElements()) {
			String name = (String) parameterNames.nextElement();
			String value = request.getParameter(name);
			json.put(name, value);
		}
		return json;
	}

	public static String getIp(HttpServletRequest request){
	    String ip = request.getHeader("x-forwarded-for");
	    if(ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)){
	        ip = request.getHeader("Proxy-Client-IP");
	    }
	    if(ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)){
	        ip = request.getHeader("WL-Proxy-Client-IP");
	    }
	    if(ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)){
	        ip = request.getRemoteAddr();
	    }
	    return ip.equals("0:0:0:0:0:0:0:1")?"127.0.0.1":ip;
	}
}