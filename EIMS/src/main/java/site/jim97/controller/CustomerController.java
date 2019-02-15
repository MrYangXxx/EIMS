package site.jim97.controller;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import site.jim97.entity.Customer;
import site.jim97.service.ReturnListService;
import site.jim97.utils.AjaxUtil;

@RestController
@RequestMapping("/customer")
public class CustomerController extends BaseController<Customer> {
	@Autowired
	private ReturnListService returnListService;

	/**
	 * 根据商品退货单统计商品顾客退货数量的比例
	 * 
	 * @param id
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping("/count/{id}")
	public void count(@PathVariable("id") Integer id, HttpServletResponse response) throws Exception {
		List<Map<String, Integer>> countReturnNumber = returnListService.countReturnNumber(id);
		List<Map<String, Integer>> countReturnTime = returnListService.countReturnTime(id);
		String values = "";
		for (Map<String, Integer> map : countReturnNumber) {
			values += map.get("name") + ",";
		}
		AjaxUtil.create().put("statisticsNumberTitle", "商品退货来源数量比例").put("statisticsTimeTitle", "商品退货来源次数比例")
				.put("legend", values.split("\\,")).put("statisticsTime", countReturnTime)
				.put("statisticsNumber", countReturnNumber).write(response);
	}
}
