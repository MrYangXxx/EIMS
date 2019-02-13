package site.jim97.geetest;

import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;

import com.alibaba.fastjson.JSONException;
import com.alibaba.fastjson.JSONObject;

import site.jim97.utils.StringUtil;

public class GeetestUtil {

	public static JSONObject getResult(HttpServletRequest request, UsernamePasswordToken token) {
		// check geetest status
		GeetestLib gtSdk = new GeetestLib(GeetestConfig.getGeetest_id(), GeetestConfig.getGeetest_key(),
				GeetestConfig.isnewfailback());
		String challenge = request.getParameter(GeetestLib.fn_geetest_challenge);
		String validate = request.getParameter(GeetestLib.fn_geetest_validate);
		String seccode = request.getParameter(GeetestLib.fn_geetest_seccode);

		// 从session中获取gt-server状态
		int gt_server_status_code = 0;
		Object gt_server_status = request.getSession().getAttribute(gtSdk.gtServerStatusSessionKey);
		if (StringUtil.isNotEmpty(gt_server_status)) {
			gt_server_status_code = (Integer) gt_server_status;
		}

		// 从session中获取userid
		String userid = (String) request.getSession().getAttribute("userid");

		// 自定义参数,可选择添加
		HashMap<String, String> param = new HashMap<String, String>();
		param.put("user_id", userid); // 网站用户id
		param.put("client_type", "web"); // web:电脑上的浏览器；h5:手机上的浏览器，包括移动应用内完全内置的web_view；native：通过原生SDK植入APP应用的方式
		param.put("ip_address", "127.0.0.1"); // 传输用户请求验证时所携带的IP

		int gtResult = 0;

		if (gt_server_status_code == 1) {
			// gt-server正常，向gt-server进行二次验证

			gtResult = gtSdk.enhencedValidateRequest(challenge, validate, seccode, param);
		} else {
			// gt-server非正常情况下，进行failback模式验证

			gtResult = gtSdk.failbackValidateRequest(challenge, validate, seccode);
		}
		JSONObject jsonObject = new JSONObject();

		Subject subject = SecurityUtils.getSubject();
		if (gtResult == 1) {
			// 验证成功
			try {
				subject.login(token);
				jsonObject.put("status", "success");
				jsonObject.put("version", gtSdk.getVersionInfo());
			} catch (Exception e) {
				e.printStackTrace(); // 登录失败shiro将抛出异常，这个异常是正常程序，发布时注释掉
				jsonObject.put("status", "fail");
				jsonObject.put("version", gtSdk.getVersionInfo());
			}
		} else {
			// 验证失败
			try {
				jsonObject.put("status", "fail");
				jsonObject.put("version", gtSdk.getVersionInfo());
			} catch (JSONException e) {
				e.printStackTrace();
			}
		}

		return jsonObject;
	}
}
