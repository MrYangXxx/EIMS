package site.jim97.controller;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;

import site.jim97.service.BaseService;
import site.jim97.utils.AjaxUtil;
import site.jim97.utils.HttpServletRequestUtil;

public abstract class BaseController<T>{
	
	@Autowired
	protected BaseService<T> service;

	@GetMapping("/list")
	public void list(T t,HttpServletRequest request, HttpServletResponse response) throws Exception{
		int size = HttpServletRequestUtil.getInt(request, "limit");
		int page = HttpServletRequestUtil.getInt(request, "page");
		IPage<T> list = service.list(t, new Page<>(page, size));
		AjaxUtil.create().put("code", 0).put("list", list).write(response); //code是前台layui要使用来判断是否接收到数据的标记值
	}
	
	@GetMapping("/{id}")
	public void findOne(@PathVariable("id") Integer id,HttpServletResponse response) throws Exception{
		T t=service.findById(id);
		AjaxUtil.create(t).write(response);
	}
	
	@PostMapping("/save")
	public void save(T t,HttpServletResponse response,HttpSession session) throws Exception{
		T newT = service.save(t);
		if(newT != null){
			AjaxUtil.success(newT,response);
		}else{
			AjaxUtil.fail(response);
		}
	}
	
	@PostMapping("/delete")
	public void delete(String ids,HttpServletResponse response) throws Exception{
		String msg = service.delete(ids);
		if("success".equals(msg)){
			AjaxUtil.success(response,msg);
		}else{
			AjaxUtil.fail(response,msg);
		}
	}
	
	/**
	 * 前端传入的时间字符串转换为Date
	 * @param binder
	 */
	@InitBinder
	public void initBinder(WebDataBinder binder){
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	    binder.registerCustomEditor(Date.class, new CustomDateEditor(dateFormat, true));    
	}
}
