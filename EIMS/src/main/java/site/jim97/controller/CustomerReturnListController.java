package site.jim97.controller;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import site.jim97.entity.CustomerReturnList;
import site.jim97.entity.User;
import site.jim97.utils.AjaxUtil;
import site.jim97.utils.DateUtil;
import site.jim97.utils.StringUtil;

@RestController
@RequestMapping("/customerreturnlist")
public class CustomerReturnListController extends BaseController<CustomerReturnList> {

	@Override
	@PostMapping("/save")
	public void save(CustomerReturnList t, HttpServletResponse response, HttpSession session) throws Exception {
		User user = (User) session.getAttribute("userInfo");
		t.setUserId(user.getId());
		t.setUserName(user.getTrueName());
		if (StringUtil.isEmpty(t.getCustomerReturnCode())) {
			t.setCustomerReturnCode("XT" + DateUtil.getStringAllDateTime());
		}
		CustomerReturnList newT = service.save(t);
		if (newT != null) {
			AjaxUtil.success(newT, response);
		} else {
			AjaxUtil.fail(response);
		}
	}
}
