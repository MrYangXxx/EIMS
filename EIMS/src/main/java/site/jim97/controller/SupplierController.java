package site.jim97.controller;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import site.jim97.entity.Supplier;
import site.jim97.service.PurchaseListService;
import site.jim97.utils.AjaxUtil;

@RestController
@RequestMapping("/supplier")
public class SupplierController extends BaseController<Supplier> {
	@Autowired
	private PurchaseListService purchaseListService;

	/**
	 * 根据商品进货单统计从商品供应商进货数量的比例
	 * 
	 * @param id
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping("/count/{id}")
	public void count(@PathVariable("id") Integer id, HttpServletResponse response) throws Exception {
		List<Map<String, Integer>> countPurchaseNumber = purchaseListService.countPurchaseNumber(id);
		List<Map<String, Integer>> countPurchaseTime = purchaseListService.countPurchaseTime(id);
		String values = "";
		for (Map<String, Integer> map : countPurchaseNumber) {
			values += map.get("name") + ",";
		}
		AjaxUtil.create().put("statisticsNumberTitle", "商品进货来源数量比例").put("statisticsTimeTitle", "商品进货来源次数比例")
				.put("legend", values.split("\\,")).put("statisticsTime", countPurchaseTime)
				.put("statisticsNumber", countPurchaseNumber).write(response);
	}
}
