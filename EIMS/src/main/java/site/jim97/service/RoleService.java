package site.jim97.service;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import site.jim97.entity.Role;
import site.jim97.entity.RoleMenu;
import site.jim97.entity.UserRole;
import site.jim97.mapper.RoleMapper;

@Service
public class RoleService extends BaseService<Role>{
	@Autowired
	private RoleMapper mapper;
	@Autowired
	private RoleMenuService roleMenuService;
	@Autowired
	private UserRoleService userRoleService;
	@Resource
	private RedisTemplate<String, ?> redisTemplate;

	@Override
	@Transactional
	public String delete(String ids) {
		String[] idArr = ids.split("\\,");
		for (String id : idArr) {
			//如果该角色已经分配，不可删除
			UserRole userRole=new UserRole();
			userRole.setRoleId(Integer.valueOf(id));
			if(userRoleService.existed(userRole)){  
				return "该角色已分派给用户，推荐修改角色权限而非删除，你可以将拥有该角色的用户取消授权后删除!";
			}
			// 删除角色关联菜单数据
			RoleMenu roleMenu=new RoleMenu();
			roleMenu.setRoleId(Integer.valueOf(id));
			roleMenuService.delete(roleMenu,"roleId");
			// 删除角色数据
			mapper.deleteById(Integer.valueOf(id));
			if(redisTemplate.hasKey("roleMenuTree:"+id)){ //角色对应的菜单缓存树删除掉
				redisTemplate.delete("roleMenuTree:"+id);
			}
		}
		return "success";
	}
}
