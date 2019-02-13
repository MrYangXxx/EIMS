package site.jim97.controller;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import site.jim97.entity.ReturnList;
import site.jim97.entity.User;
import site.jim97.utils.AjaxUtil;
import site.jim97.utils.DateUtil;
import site.jim97.utils.StringUtil;

@RestController
@RequestMapping("/returnlist")
public class ReturnListController extends BaseController<ReturnList> {
	
	@Override
	@PostMapping("/save")
	public void save(ReturnList t, HttpServletResponse response, HttpSession session) throws Exception {
		//退货处理人
		User user = (User) session.getAttribute("userInfo");
		t.setUserId(user.getId());
		t.setUserName(user.getTrueName());
		//自动生成退货单号
		if (StringUtil.isEmpty(t.getReturnCode())) {
			t.setReturnCode("TH" + DateUtil.getStringAllDateTime());
		}
		ReturnList newT = service.save(t);
		if (newT != null) {
			AjaxUtil.success(newT, response);
		} else {
			AjaxUtil.fail(response);
		}
	}
	
}
