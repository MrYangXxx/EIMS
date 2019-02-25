package site.jim97.aop;

import javax.servlet.http.HttpServletRequest;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import site.jim97.entity.Log;
import site.jim97.entity.User;
import site.jim97.service.LogService;
import site.jim97.utils.DateUtil;
import site.jim97.utils.HttpServletRequestUtil;

/**
 * 操作记录日志好像应该切至service层中，但没想好怎么做好一点 先切入到controller层中
 * 
 * @author Jim
 *
 */
@Aspect
@Component
public class LogAspect {

	@Pointcut("execution(public * site.jim97.controller.*.list(..))")
	private void listLog() {
	}

	@Pointcut("execution(public * site.jim97.controller.*.save(..))")
	private void saveLog() {
	}

	@Pointcut("execution(public * site.jim97.controller.*.findOne(..))")
	private void findOneLog() {
	}

	@Pointcut("execution(public * site.jim97.controller.*.delete(..))")
	private void deleteLog() {
	}

	@Autowired
	private LogService logService;

	/*
	 * @Before("webLog()") public void doBefore(JoinPoint jp){}
	 * 
	 * @After("webLog()") public void doAfter(JoinPoint jp){}
	 * 
	 * @AfterReturning("webLog()") public void doReturn(JoinPoint jp,Object
	 * result){}
	 * 
	 * @Around("webLog()") public void doAround(JoinPoint jp){}
	 * 
	 * @AfterThrowing("webLog()") public void doException(JoinPoint jp){}
	 */

	@After("listLog()")
	public void listAfter(JoinPoint jp) {
		// 获取request，也可采用自动注入的方式获取
		HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes())
				.getRequest();
		String ip = HttpServletRequestUtil.getIp(request);
		User user = (User) HttpServletRequestUtil.getSessionAttr(request, "userInfo");
		// 获取当前操作模块名
		String modelName = getModelName(request);
		// 保存日志信息
		Log log = new Log();
		log.setContent("查询" + modelName + "信息");
		log.setTime(DateUtil.getNowDateTime());
		log.setTrueName(user.getTrueName());
		log.setType("查询操作");
		log.setIp(ip);
		logService.save(log);
	}

	@After("saveLog()")
	public void saveAfter(JoinPoint jp) {
		// 获取request，也可采用自动注入的方式获取
		HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes())
				.getRequest();
		String ip = HttpServletRequestUtil.getIp(request);
		User user = (User) HttpServletRequestUtil.getSessionAttr(request, "userInfo");
		// 获取当前操作模块名
		String modelName = getModelName(request);
		// 保存日志信息
		Log log = new Log();
		log.setContent("查询" + modelName + "信息");
		log.setTime(DateUtil.getNowDateTime());
		log.setTrueName(user.getTrueName());
		log.setType("查询操作");
		log.setIp(ip);
		logService.save(log);
	}

	@After("findOneLog()")
	public void findOneAfter(JoinPoint jp) {
		// 获取request，也可采用自动注入的方式获取
		HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes())
				.getRequest();
		String ip = HttpServletRequestUtil.getIp(request);
		User user = (User) HttpServletRequestUtil.getSessionAttr(request, "userInfo");
		// 获取当前操作模块名
		String modelName = getModelName(request);
		// 保存日志信息
		Log log = new Log();
		log.setContent("查询" + modelName + "信息");
		log.setTime(DateUtil.getNowDateTime());
		log.setTrueName(user.getTrueName());
		log.setType("查询操作");
		log.setIp(ip);
		logService.save(log);
	}

	@After("deleteLog()")
	public void deleteAfter(JoinPoint jp) {
		// 获取request，也可采用自动注入的方式获取
		HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes())
				.getRequest();
		String ip = HttpServletRequestUtil.getIp(request);
		User user = (User) HttpServletRequestUtil.getSessionAttr(request, "userInfo");
		// 获取当前操作模块名
		String modelName = getModelName(request);
		// 保存日志信息
		Log log = new Log();
		log.setContent("查询" + modelName + "信息");
		log.setTime(DateUtil.getNowDateTime());
		log.setTrueName(user.getTrueName());
		log.setType("查询操作");
		log.setIp(ip);
		logService.save(log);
	}

	@AfterThrowing("listLog()")
	public void doException(JoinPoint jp) {
		// 获取request，也可采用自动注入的方式获取
		HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes())
				.getRequest();
		String ip = HttpServletRequestUtil.getIp(request);
		User user = (User) HttpServletRequestUtil.getSessionAttr(request, "userInfo");
		// 获取当前操作模块名
		String modelName = getModelName(request);
		// 保存日志信息
		Log log = new Log();
		log.setContent("查询" + modelName + "信息");
		log.setTime(DateUtil.getNowDateTime());
		log.setTrueName(user.getTrueName());
		log.setType("查询操作");
		log.setIp(ip);
		logService.save(log);
	}

	/**
	 * 根据首个url地址判断访问哪个模块，返回模块名用于日志保存
	 * 
	 * @param request
	 * @return
	 */
	private String getModelName(HttpServletRequest request) {
		String requestURI = request.getRequestURI();
		String firstUrl = requestURI.split("\\/")[1];
		// 根据url中的第一个字段判断访问哪个模块
		switch (firstUrl) {
		case "goods":
			return "商品";
		case "customer":
			return "客户";
		case "customerreturnlist":
			return "客户退货";
		case "damagelist":
			return "商品报损";
		case "goodstype":
			return "商品类型";
		case "log":
			return "日志";
		case "overflowlist":
			return "商品报溢";
		case "purchaselist":
			return "商品采购";
		case "returnlist":
			return "商品退货";
		case "role":
			return "角色";
		case "salelist":
			return "商品销售";
		case "supplier":
			return "供应商";
		case "user":
			return "用户";
		default:
			return firstUrl;
		}
	}
}
