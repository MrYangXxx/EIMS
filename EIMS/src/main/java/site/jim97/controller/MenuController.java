package site.jim97.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import site.jim97.entity.Menu;
import site.jim97.entity.RoleMenu;
import site.jim97.service.RoleMenuService;
import site.jim97.utils.AjaxUtil;
import site.jim97.utils.HttpServletRequestUtil;

@RestController
@RequestMapping("/menu")
public class MenuController extends BaseController<Menu> {
	@Autowired
	RoleMenuService roleMenuService;

	/**
	 * 菜单层级关系，菜单分为父菜单和子菜单
	 * 用于菜单编辑页面选择父菜单下拉列表
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	@GetMapping("/root")
	public void root(HttpServletRequest request, HttpServletResponse response) throws Exception {
		Menu menu = new Menu();
		menu.setPId(0);
		List<Menu> list = service.list(menu, "name", "pName", "eq_pId", "orderBy_priority");
		AjaxUtil.create().put("list", list).write(response); // code是前台layui要使用来判断是否接收到数据的标记值
	}

	/**
	 * 角色界面的菜单授权，需要在后台将父子节点定义好
	 * 
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	@PostMapping("/tree")
	public void tree(HttpServletRequest request, HttpServletResponse response) throws Exception {
		int roleId = HttpServletRequestUtil.getInt(request, "roleId");
		// 设置父菜单寻找条件并查找父菜单
		Menu menu = new Menu();
		menu.setPId(0);
		List<Menu> fathers = service.list(menu, "name", "pName", "eq_pId", "orderBy_priority");
		// 遍历父菜单，根据父菜单查找对应子菜单
		for (Menu father : fathers) {
			Menu menu2 = new Menu();
			menu2.setPId(father.getId());
			List<Menu> children = service.list(menu2, "eq_pId", "orderBy_priority");
			// 遍历子菜单，查找是否存在与角色菜单关联表中，存在则角色拥有此菜单，设置checked属性为true
			for (Menu child : children) {
				RoleMenu roleMenu = new RoleMenu();
				roleMenu.setRoleId(roleId);
				roleMenu.setMenuId(child.getId());
				boolean existed = roleMenuService.existed(roleMenu);
				child.setChecked(existed);
			}
			father.setChildren(children);
		}
		AjaxUtil.create().put("code", 0).put("list", fathers).write(response);
	}

}
