package site.jim97.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import site.jim97.entity.Role;
import site.jim97.entity.RoleMenu;
import site.jim97.service.RoleMenuService;
import site.jim97.utils.AjaxUtil;
import site.jim97.utils.HttpServletRequestUtil;
import site.jim97.utils.StringUtil;

@RestController
@RequestMapping("/role")
public class RoleController extends BaseController<Role> {
	@Autowired
	private RoleMenuService roleMenuServie;

	@Override
	@PostMapping("/save")
	public void save(Role role, HttpServletResponse response, HttpSession session) throws Exception {
		if (role.getId() == null) {
			boolean existed = service.existed(role, "name");
			if (existed) {
				AjaxUtil.fail(response, "角色已存在");
				return;
			}
		}
		Role bean = service.save(role);
		if (bean != null) {
			AjaxUtil.success(bean, response);
		} else {
			AjaxUtil.fail(response);
		}
	}

	@PostMapping("/auth")
	public void auth(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String addRoleMenuId = HttpServletRequestUtil.getString(request, "addRoleMenuId");
		String deleteRoleMenuId = HttpServletRequestUtil.getString(request, "deleteRoleMenuId");
		int roleId = HttpServletRequestUtil.getInt(request, "roleId");
		if (StringUtil.isEmpty(deleteRoleMenuId)) {
			deleteRoleMenuId = "0";
		}
		//保存角色与菜单关系
		if(StringUtil.isNotEmpty(addRoleMenuId)){
			String[] split = addRoleMenuId.split("\\,");
			for (String menuId : split) {
				RoleMenu roleMenu = new RoleMenu();
				roleMenu.setMenuId(Integer.valueOf(menuId));
				roleMenu.setRoleId(roleId);
				roleMenuServie.save(roleMenu);
			}
		}
		//删除角色与菜单关系
		roleMenuServie.batchDelete(roleId, deleteRoleMenuId);
		AjaxUtil.success(response);
	}
}
