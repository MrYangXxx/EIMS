package site.jim97.controller;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import site.jim97.entity.PurchaseList;
import site.jim97.entity.User;
import site.jim97.utils.AjaxUtil;
import site.jim97.utils.DateUtil;
import site.jim97.utils.StringUtil;

@RestController
@RequestMapping("/purchaselist")
public class PurchaseListController extends BaseController<PurchaseList>{
	
	@Override
	@PostMapping("/save")
	public void save(PurchaseList t, HttpServletResponse response,HttpSession session) throws Exception {
		User user = (User) session.getAttribute("userInfo"); 
		t.setUserId(user.getId());
		t.setUserName(user.getTrueName());
		if(StringUtil.isEmpty(t.getPurchaseCode())){
			t.setPurchaseCode("JH"+DateUtil.getStringAllDateTime());
		}
		PurchaseList newT = service.save(t);
		if(newT != null){
			AjaxUtil.success(newT,response);
		}else{
			AjaxUtil.fail(response);
		}
	}
	
}
