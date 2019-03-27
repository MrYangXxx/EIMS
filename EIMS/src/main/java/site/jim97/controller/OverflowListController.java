package site.jim97.controller;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import site.jim97.entity.OverflowList;
import site.jim97.entity.User;
import site.jim97.utils.AjaxUtil;
import site.jim97.utils.DateUtil;
import site.jim97.utils.StringUtil;

@RestController
@RequestMapping("/overflowlist")
public class OverflowListController extends BaseController<OverflowList> {
	@Override
	@PostMapping("/save")
	public void save(OverflowList t, HttpServletResponse response, HttpSession session) throws Exception {
		User user = (User) session.getAttribute("userInfo");
		t.setUserId(user.getId());
		t.setUserName(user.getTrueName());
		if (StringUtil.isEmpty(t.getOverflowCode())) {
			t.setOverflowCode("BY" + DateUtil.getStringAllDateTime());
		}
		boolean save = service.save(t);
		if (save) {
			AjaxUtil.success(t, response);
		} else {
			AjaxUtil.fail(response);
		}
	}
}
