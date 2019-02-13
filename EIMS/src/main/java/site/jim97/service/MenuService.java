package site.jim97.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import site.jim97.entity.Menu;
import site.jim97.entity.RoleMenu;
import site.jim97.mapper.MenuMapper;

@Service
public class MenuService extends BaseService<Menu>{

	@Autowired
	MenuMapper mapper;
	@Autowired
	RoleMenuService roleMenuService;
	
	public List<Menu> list(int userId){
		return mapper.listMenu(userId);
	}

	@Override
	@Transactional
	public String delete(String ids) {
		String[] idArr = ids.split("\\,");
		for (String id : idArr) {
			//删除角色关联菜单
			RoleMenu roleMenu=new RoleMenu();
			roleMenu.setMenuId(Integer.valueOf(id)); 
			roleMenuService.delete(roleMenu);
			//删除菜单
			mapper.deleteById(Integer.valueOf(id));
		}
		return "success";
	}
	
	
}
