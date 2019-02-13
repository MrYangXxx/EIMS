package site.jim97.controller;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import site.jim97.entity.Role;
import site.jim97.utils.AjaxUtil;

@RestController
@RequestMapping("/role")
public class RoleController extends BaseController<Role>{

	@Override
	@PostMapping("/save")
	public void save(Role role,HttpServletResponse response,HttpSession session) throws Exception{
		if(role.getId() == null){
			boolean existed = service.existed(role, "name");
			if(existed){
				AjaxUtil.fail(response, "角色已存在");
				return;
			}
		}
		Role bean = service.save(role);
		if(bean != null){
			AjaxUtil.success(bean,response);
		}else{
			AjaxUtil.fail(response);
		}
	}
}
