package site.jim97.controller;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import site.jim97.entity.PurchaseList;
import site.jim97.entity.User;
import site.jim97.service.PurchaseListService;
import site.jim97.utils.AjaxUtil;
import site.jim97.utils.DateUtil;
import site.jim97.utils.StringUtil;

@RestController
@RequestMapping("/purchaselist")
public class PurchaseListController extends BaseController<PurchaseList> {
	@Autowired
	private PurchaseListService service;

	@Override
	@PostMapping("/save")
	public void save(PurchaseList t, HttpServletResponse response, HttpSession session) throws Exception {
		User user = (User) session.getAttribute("userInfo");
		t.setUserId(user.getId());
		t.setUserName(user.getTrueName());
		if (StringUtil.isEmpty(t.getPurchaseCode())) {
			t.setPurchaseCode("JH" + DateUtil.getStringAllDateTime());
		}
		boolean save = service.save(t);
		if (save) {
			AjaxUtil.success(t, response);
		} else {
			AjaxUtil.fail(response);
		}
	}

	/**
	 * 统计商品历史进货价格
	 * @param id
	 * @param response
	 * @throws Exception
	 */
	@GetMapping("/count/{id}")
	public void count(@PathVariable("id") Integer id, HttpServletResponse response) throws Exception {
		List<Map<String, Integer>> countPurchasePrice = service.countPurchasePrice(id);
		String legend = "";
		String data = "";
		for (Map<String, Integer> map : countPurchasePrice) {
			legend += map.get("date") + ",";
			data += map.get("price") + ",";
		}
		AjaxUtil.create().put("statisticsTitle", "历史进货价格").put("legend", legend.split("\\,"))
				.put("statisticsData", data.split("\\,")).write(response);
	}
}
