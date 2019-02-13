package site.jim97.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import site.jim97.entity.Role;
import site.jim97.entity.RoleMenu;
import site.jim97.mapper.RoleMapper;

@Service
public class RoleService extends BaseService<Role>{
	@Autowired
	RoleMapper mapper;
	@Autowired
	RoleMenuService roleMenuService;

	@Override
	@Transactional
	public String delete(String ids) {
		String[] idArr = ids.split("\\,");
		for (String id : idArr) {
			// 删除角色关联菜单数据
			RoleMenu roleMenu=new RoleMenu();
			roleMenu.setRoleId(Integer.valueOf(id));
			roleMenuService.delete(roleMenu,"roleId");
			// 删除角色数据
			mapper.deleteById(Integer.valueOf(id));
		}
		return "success";
	}
}
