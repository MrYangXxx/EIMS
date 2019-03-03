package site.jim97.controller;

import java.util.Date;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;

import site.jim97.entity.Log;
import site.jim97.utils.AjaxUtil;
import site.jim97.utils.DateUtil;
import site.jim97.utils.HttpServletRequestUtil;

@RestController
@RequestMapping("/log")
public class LogController extends BaseController<Log> {

	@Override
	@GetMapping("/list")
	public void list(Log t, HttpServletRequest request, HttpServletResponse response) throws Exception {
		int size = HttpServletRequestUtil.getInt(request, "limit");
		int page = HttpServletRequestUtil.getInt(request, "page");
		String startTime = HttpServletRequestUtil.getString(request, "startTime");
		String endTime = HttpServletRequestUtil.getString(request, "endTime");
		Map<String, Date> timeMap = DateUtil.getTimeMap(startTime, endTime); //根据时间范围查询
		IPage<Log> list = service.list(t, new Page<>(page, size), "time", timeMap,"trueName","typeName","orderByDesc_time");
		AjaxUtil.create().put("code", 0).put("list", list).write(response); // code是前台layui要使用来判断是否接收到数据的标记值
	}
	
}
