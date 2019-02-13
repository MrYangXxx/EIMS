package site.jim97.utils;

import java.io.PrintWriter;

import javax.servlet.http.HttpServletResponse;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.SimplePropertyPreFilter;

public class AjaxUtil {

	private static JSONObject json;
	private static AjaxUtil ajaxUtil = new AjaxUtil();

	/**
	 * 创建空JsonObject对象
	 * 
	 * @return
	 */
	public static AjaxUtil create() {
		json = new JSONObject();
		return ajaxUtil;
	}

	/**
	 * 根据传入的javabean创建JsonObject对象
	 * 
	 * @param o
	 * @return
	 */
	public static AjaxUtil create(Object o) {
		if (o != null) {
			json = (JSONObject) JSONObject.toJSON(o);
		} else {
			json = new JSONObject();
		}
		return ajaxUtil;
	}

	/**
	 * 推荐使用 根据传入的javabean和需要的字段名，创建含规定字段的 JsonObject对象
	 * 
	 * @param o
	 * @param white
	 * @return
	 */
	public static AjaxUtil create(Object o, String... white) {
		SimplePropertyPreFilter simplePropertyPreFilter = new SimplePropertyPreFilter(o.getClass(), white);
		json = JSONObject.parseObject(JSON.toJSONString(o, simplePropertyPreFilter));
		return ajaxUtil;
	}

	/**
	 * 过滤掉不需要的字段
	 * 
	 * @param black
	 * @return
	 */
	public AjaxUtil exclude(String... black) {
		for (String key : black) {
			if (json.containsKey(key))
				json.remove(key);
		}
		return ajaxUtil;
	}

	/**
	 * 推荐在创建时直接指定所需字段 只留下限定字段的JsonObject
	 * 
	 * @deprecated
	 * @param clazz
	 * @param white
	 * @return
	 */
	public <T> AjaxUtil include(Class<T> clazz, String... white) {
		SimplePropertyPreFilter simplePropertyPreFilter = new SimplePropertyPreFilter(clazz, white);
		json = JSONObject.parseObject(JSON.toJSONString(json.toJavaObject(clazz), simplePropertyPreFilter));
		return ajaxUtil;
	}

	/**
	 * 在JsonObject里追加属性
	 * 
	 * @param key
	 * @param value
	 * @return
	 */
	public AjaxUtil put(String key, Object value) {
		json.put(key, value);
		return ajaxUtil;
	}

	/**
	 * 写出到页面
	 * 
	 * @param response
	 * @throws Exception
	 */
	public void write(HttpServletResponse response) throws Exception {
		response.setContentType("text/html;charset=utf-8");
		PrintWriter out = response.getWriter();
		out.println(json.toString());
		out.flush();
		out.close();
	}

	/**
	 * 直接将对象值写出到页面
	 * 
	 * @param response
	 * @param o
	 * @throws Exception
	 */
	public static void write(HttpServletResponse response, Object o) throws Exception {
		response.setContentType("text/html;charset=utf-8");
		PrintWriter out = response.getWriter();
		out.println(o.toString());
		out.flush();
		out.close();
	}

	/**
	 * 直接返回success为true的值
	 * 
	 * @throws Exception
	 */
	public static void success(HttpServletResponse response) throws Exception {
		create().put("success", true).write(response);
	}

	/**
	 * 直接返回对象和success为true的值
	 * 
	 * @param <T>
	 * @throws Exception
	 */
	public static <T> void success(T t, HttpServletResponse response) throws Exception {
		create(t).put("success", true).put("msg", "操作成功").write(response);
	}
	
	/**
	 * 直接返回success为true的值并携带自定义信息
	 * 
	 * @throws Exception
	 */
	public static void success(HttpServletResponse response, String msg) throws Exception {
		create().put("success", true).put("msg", msg).write(response);
	}

	/**
	 * 直接返回success为false的值
	 * 
	 * @throws Exception
	 */
	public static void fail(HttpServletResponse response) throws Exception {
		create().put("success", false).put("msg", "操作失败").write(response);
	}

	/**
	 * 直接返回success为false的值并携带自定义错误信息
	 * 
	 * @throws Exception
	 */
	public static void fail(HttpServletResponse response, String msg) throws Exception {
		create().put("success", false).put("msg", msg).write(response);
	}

	@Override
	public String toString() {
		return AjaxUtil.json.toString();
	}

}
