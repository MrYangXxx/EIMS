package site.jim97.realm;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.springframework.beans.factory.annotation.Autowired;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;

import site.jim97.entity.Menu;
import site.jim97.entity.Role;
import site.jim97.entity.User;
import site.jim97.mapper.MenuMapper;
import site.jim97.mapper.RoleMapper;
import site.jim97.mapper.UserMapper;

public class MyRealm extends AuthorizingRealm{
	
	@Autowired
	UserMapper userMapper;
	@Autowired
	RoleMapper roleMapper;
	@Autowired
	MenuMapper menuMapper;
	
	/**
	 * Authentication: 确认，认证
	 */
	@Override
	protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
		String userName=(String)token.getPrincipal();
		QueryWrapper<User> warp=new QueryWrapper<>();
		warp.lambda().eq(User::getUserName, userName);
		User user = userMapper.selectOne(warp);
		if(user!=null){
			AuthenticationInfo info=new SimpleAuthenticationInfo(user.getUserName(),user.getPassword(),"xxx");
			return info;
		}else{
			return null;
		}
	}

	/**
	 * Authorization：授权
	 */
	@Override
	protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
		//查询登录用户信息
		String userName = (String) SecurityUtils.getSubject().getPrincipal();
		QueryWrapper<User> warp=new QueryWrapper<>();
		warp.lambda().eq(User::getUserName, userName);
		User user = userMapper.selectOne(warp);
		//根据用户信息查询对应的角色信息
		SimpleAuthorizationInfo info=new SimpleAuthorizationInfo();
		QueryWrapper<Role> roleWarp=new QueryWrapper<>();
		roleWarp.eq("user_id", user.getId());
		List<Role> roles = roleMapper.selectList(roleWarp);
		//根据角色信息查询拥有的菜单信息，设置进shiro的授权信息中，在controller层通过调用@RequiresPermissions(value="菜单名")验证是否授权
		//多个角色判断：@RequiresPermissions(value={"菜单","菜单2"},logical=Logical.OR)，默认采用and逻辑，根据系统逻辑应该改为or
		//系统暂不使用权限限定
		Set<String> roleSet=new HashSet<>();
		for (Role role : roles) {
			roleSet.add(role.getName());
			List<Menu> menus = menuMapper.listMenuByRoleId(role.getId());
			for (Menu menu : menus) {
				info.addStringPermission(menu.getName());
			}
		}
		info.setRoles(roleSet);
		return info;
	}
	
	
	
}
