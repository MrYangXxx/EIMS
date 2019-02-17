package site.jim97.controller;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import site.jim97.entity.Customer;
import site.jim97.service.SaleListService;
import site.jim97.service.CustomerReturnListService;
import site.jim97.utils.AjaxUtil;

@RestController
@RequestMapping("/customer")
public class CustomerController extends BaseController<Customer> {
	@Autowired
	private SaleListService saleListService;
	@Autowired
	private CustomerReturnListService customerReturnListService;

	/**
	 * 根据售卖单统计商品售卖给客户的比例
	 * 
	 * @param id
	 * @param response
	 * @throws Exception
	 */
	@GetMapping("/salecount/{id}")
	public void countSale(@PathVariable("id") Integer id, HttpServletResponse response) throws Exception {
		List<Map<String, Integer>> countSaleNumber = saleListService.countSaleNumber(id);
		List<Map<String, Integer>> countSaleTime = saleListService.countSaleTime(id);
		String legend = "";
		for (Map<String, Integer> map : countSaleNumber) {
			legend += map.get("name") + ",";
		}
		AjaxUtil.create().put("statisticsNumberTitle", "售卖数量比例").put("statisticsTimeTitle", "售卖次数比例")
				.put("legend", legend.split("\\,")).put("statisticsTime", countSaleTime)
				.put("statisticsNumber", countSaleNumber).write(response);
	}
	

	/**
	 * 根据客户退货单统计客户退货的比例
	 * 
	 * @param id
	 * @param response
	 * @throws Exception
	 */
	@GetMapping("/returncount/{id}")
	public void countCustomerReturn(@PathVariable("id") Integer id, HttpServletResponse response) throws Exception {
		List<Map<String, Integer>> countCustomerReturnNumber = customerReturnListService.countCustomerReturnNumber(id);
		List<Map<String, Integer>> countCustomerReturnTime = customerReturnListService.countCustomerReturnTime(id);
		String legend = "";
		for (Map<String, Integer> map : countCustomerReturnNumber) {
			legend += map.get("name") + ",";
		}
		AjaxUtil.create().put("statisticsNumberTitle", "退货数量比例").put("statisticsTimeTitle", "退货次数比例")
				.put("legend", legend.split("\\,")).put("statisticsTime", countCustomerReturnTime)
				.put("statisticsNumber", countCustomerReturnNumber).write(response);
	}
}
