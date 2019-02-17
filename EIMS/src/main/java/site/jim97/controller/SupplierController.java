package site.jim97.controller;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import site.jim97.entity.Supplier;
import site.jim97.service.PurchaseListService;
import site.jim97.service.ReturnListService;
import site.jim97.utils.AjaxUtil;

@RestController
@RequestMapping("/supplier")
public class SupplierController extends BaseController<Supplier> {
	@Autowired
	private PurchaseListService purchaseListService;
	@Autowired
	private ReturnListService returnListService;

	/**
	 * 根据商品进货单统计从商品供应商进货的比例
	 * 
	 * @param id
	 * @param response
	 * @throws Exception
	 */
	@GetMapping("/purchasecount/{id}")
	public void countPurchase(@PathVariable("id") Integer id, HttpServletResponse response) throws Exception {
		List<Map<String, Integer>> countPurchaseNumber = purchaseListService.countPurchaseNumber(id);
		List<Map<String, Integer>> countPurchaseTime = purchaseListService.countPurchaseTime(id);
		String legend = "";
		for (Map<String, Integer> map : countPurchaseNumber) {
			legend += map.get("name") + ",";
		}
		AjaxUtil.create().put("statisticsNumberTitle", "进货数量比例").put("statisticsTimeTitle", "进货次数比例")
				.put("legend", legend.split("\\,")).put("statisticsTime", countPurchaseTime)
				.put("statisticsNumber", countPurchaseNumber).write(response);
	}
	

	/**
	 * 根据商品退货单统计向供应商退货的比例
	 * 
	 * @param id
	 * @param response
	 * @throws Exception
	 */
	@GetMapping("/returncount/{id}")
	public void countReturn(@PathVariable("id") Integer id, HttpServletResponse response) throws Exception {
		List<Map<String, Integer>> countReturnNumber = returnListService.countReturnNumber(id);
		List<Map<String, Integer>> countReturnTime = returnListService.countReturnTime(id);
		String legend = "";
		for (Map<String, Integer> map : countReturnNumber) {
			legend += map.get("name") + ",";
		}
		AjaxUtil.create().put("statisticsNumberTitle", "退货数量比例").put("statisticsTimeTitle", "退货次数比例")
				.put("legend", legend.split("\\,")).put("statisticsTime", countReturnTime)
				.put("statisticsNumber", countReturnNumber).write(response);
	}
}
