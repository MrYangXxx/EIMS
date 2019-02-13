package site.jim97.mapper;


import java.util.List;

import org.apache.ibatis.annotations.Select;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import site.jim97.entity.Menu;

public interface MenuMapper extends BaseMapper<Menu>{

	/**
	 * 根据用户id级联查询得出对应拥有菜单表
	 * sql可以进行拆分成通过简单查询得出上一级查询需要的条件，这里我是为了方便
	 * 此方法用于查询前台显示菜单
	 * @param userId
	 * @return
	 */
	@Select("SELECT t_menu.* FROM t_menu WHERE id IN(SELECT t_role_menu.`menu_id` FROM t_role_menu WHERE t_role_menu.`role_id` IN (SELECT t_user_role.`role_id` FROM t_user_role WHERE t_user_role.`user_id` =#{userId}))")
	List<Menu> listMenu(int userId);
	
	/**
	 * 根据角色id级联查询得出对应拥有菜单表
	 * sql可以进行拆分成通过简单查询得出上一级查询需要的条件，这里我是为了方便
	 * 此用于shiro的菜单授权
	 * @param roleId
	 * @return
	 */
	@Select("SELECT t_menu.* FROM t_menu WHERE id IN(SELECT t_role_menu.`menu_id` FROM t_role_menu WHERE t_role_menu.`role_id` =#{roleId})")
	List<Menu> listMenuByRoleId(int roleId);
}
