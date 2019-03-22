package site.jim97.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import site.jim97.entity.Menu;
import site.jim97.entity.RoleMenu;
import site.jim97.mapper.MenuMapper;

@Service
public class MenuService extends BaseService<Menu> {

	@Autowired
	MenuMapper mapper;
	@Autowired
	RoleMenuService roleMenuService;
	@Resource
	private RedisTemplate<String, List<Menu>> redisTemplate;

	/**
	 * 返回用户拥有访问权限的菜单树
	 * @param userId
	 * @return
	 */
	public List<Menu> menuTree(int userId) {
		ValueOperations<String, List<Menu>> opsForValue = redisTemplate.opsForValue();
		List<Menu> fatherMenu = new ArrayList<>();
		if(redisTemplate.hasKey("userMenuTree:"+userId)){ //通过menuTree:userId标识缓存名，缓存每个用户可访问菜单,冒号分割可便于redis分层
			fatherMenu=opsForValue.get("userMenuTree:"+userId);
		}else{
			List<Menu> listMenu = mapper.listMenu(userId); //该用户可以访问的所有菜单
			fatherMenu= listMenu.stream().filter(x -> x.getPId()==0).collect(Collectors.toList());//筛选出所有父菜单
			fatherMenu.forEach(f->{  //遍历父菜单
				List<Menu> childrenMenu = new ArrayList<>();
				listMenu.forEach(c->{ //遍历所有菜单，菜单的pid等于父菜单id则加入到childrenMenu
					if(f.getId() == c.getPId()){
						childrenMenu.add(c);
					}
				});
				f.setChildren(childrenMenu); //当前父菜单add查询到的子菜单列表
			});
			opsForValue.set("userMenuTree:"+userId, fatherMenu); //添加缓存
		}
		return fatherMenu;
	}
	
	/**
	 * 返回角色菜单授权的菜单树: 
	 * 1.所有菜单
	 * 2.已授权的菜单设置checked为true
	 * @param userId
	 * @return
	 */
	public List<Menu> roleMenuTree(int roleId) {
		ValueOperations<String, List<Menu>> opsForValue = redisTemplate.opsForValue();
		List<Menu> fathers = new ArrayList<>();
		if(redisTemplate.hasKey("roleMenuTree:"+roleId)){ //通过roleMenuTree:roleId标识缓存名，缓存每个角色菜单,冒号分割可便于redis分层
			fathers=opsForValue.get("roleMenuTree:"+roleId);
		}else{
			// 设置父菜单寻找条件并查找父菜单
			Menu menu = new Menu();
			menu.setPId(0);
			fathers = this.list(menu, "name", "pName", "eq_pId", "orderBy_priority");
			// 遍历父菜单，根据父菜单查找对应子菜单
			for (Menu father : fathers) {
				Menu menu2 = new Menu();
				menu2.setPId(father.getId());
				List<Menu> children = this.list(menu2, "eq_pId", "orderBy_priority");
				// 遍历子菜单，查找是否存在与角色菜单关联表中，存在则角色拥有此菜单，设置checked属性为true
				for (Menu child : children) {
					RoleMenu roleMenu = new RoleMenu();
					roleMenu.setRoleId(roleId);
					roleMenu.setMenuId(child.getId());
					boolean existed = roleMenuService.existed(roleMenu);
					child.setChecked(existed);
				}
				father.setChildren(children);
				opsForValue.set("roleMenuTree:"+roleId, fathers);
			}
		}
		return fathers;
	}

	@Override
	@Transactional
	public String delete(String ids) {
		String[] idArr = ids.split("\\,");
		for (String id : idArr) {
			// 删除角色关联菜单
			RoleMenu roleMenu = new RoleMenu();
			roleMenu.setMenuId(Integer.valueOf(id));
			roleMenuService.delete(roleMenu);
			// 删除菜单
			mapper.deleteById(Integer.valueOf(id));
		}
		Set<String> keys = redisTemplate.keys("userMenuTree:"+"*"); //菜单删除后菜单树要重新缓存
		Set<String> keys2 = redisTemplate.keys("roleMenuTree:"+"*"); //菜单删除后角色菜单树要重新缓存
		redisTemplate.delete(keys);
		redisTemplate.delete(keys2);
		return "success";
	}

}
