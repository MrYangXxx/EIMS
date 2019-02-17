package site.jim97.controller;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import site.jim97.entity.SaleList;
import site.jim97.entity.User;
import site.jim97.service.SaleListService;
import site.jim97.utils.AjaxUtil;
import site.jim97.utils.DateUtil;
import site.jim97.utils.HttpServletRequestUtil;
import site.jim97.utils.StringUtil;

@RestController
@RequestMapping("/salelist")
public class SaleListController extends BaseController<SaleList>{
	@Autowired
	private SaleListService service;

	@Override
	@PostMapping("/save")
	public void save(SaleList t, HttpServletResponse response, HttpSession session) throws Exception {
		User user = (User) session.getAttribute("userInfo");
		t.setUserId(user.getId());
		t.setUserName(user.getTrueName());
		if (StringUtil.isEmpty(t.getSaleCode())) {
			t.setSaleCode("XS" + DateUtil.getStringAllDateTime());
		}
		SaleList newT = service.save(t);
		if (newT != null) {
			AjaxUtil.success(newT, response);
		} else {
			AjaxUtil.fail(response);
		}
	}
	
	@GetMapping("/count/{id}")
	public void count(@PathVariable("id") Integer id, HttpServletResponse response) throws Exception {
		List<Map<String, Integer>> countSalePrice = service.countSalePrice(id);
		String legend = "";
		String data = "";
		for (Map<String, Integer> map : countSalePrice) {
			legend += map.get("date") + ",";
			data += map.get("price") + ",";
		}
		AjaxUtil.create().put("statisticsTitle", "历史售卖价格").put("legend", legend.split("\\,"))
				.put("statisticsData", data.split("\\,")).write(response);
	}
	
	@GetMapping(value={"/revenue/{id}","/revenue"})
	public void revenue(@PathVariable(required=false,value="id") Integer id, HttpServletRequest request,HttpServletResponse response) throws Exception {
		String type = HttpServletRequestUtil.getString(request, "type");
		List<Map<String, Integer>> countRevenue;
		if(StringUtil.isNotEmpty(type) && type.equals("month")){
			countRevenue = service.countMonthRevenue(id);
		}else{
			countRevenue = service.countDayRevenue(id);
		}
		String legend = "";
		String data = "";
		for (Map<String, Integer> map : countRevenue) {
			legend += map.get("date") + ",";
			data += map.get("value") + ",";
		}
		AjaxUtil.create().put("statisticsTitle", "销售额统计").put("legend", legend.split("\\,"))
				.put("statisticsData", data.split("\\,")).write(response);
	}
}
