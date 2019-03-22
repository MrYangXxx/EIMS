package site.jim97.service;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import site.jim97.entity.User;
import site.jim97.entity.UserRole;
import site.jim97.mapper.UserMapper;
import site.jim97.utils.CryptographyUtil;

@Service
public class UserService extends BaseService<User> {
	@Autowired
	private UserMapper mapper;
	@Autowired
	private UserRoleService userRoleService;
	@Resource
	private RedisTemplate<String, ?> redisTemplate;

	@Override
	public User save(User user) {
		if (user.getId() != null) { // 有id，修改
			User oldUser = mapper.selectById(user.getId());
			if (user.getPassword() != null && !oldUser.getPassword().equals(user.getPassword())) { // 密码经过修改，重新加密
				user.setPassword(CryptographyUtil.md5(user.getPassword()));
			}
			mapper.updateById(user);
		} else {
			user.setPassword(CryptographyUtil.md5(user.getPassword())); // 新增密码肯定要加密
			mapper.insert(user);
		}
		return user;
	}

	@Override
	@Transactional
	public String delete(String ids) {
		String[] idArr = ids.split("\\,");
		for (String id : idArr) {
			// 删除用户关联角色数据
			UserRole userRole = new UserRole();
			userRole.setUserId(Integer.valueOf(id));
			userRoleService.delete(userRole, "userId");
			// 删除用户数据
			mapper.deleteById(Integer.valueOf(id));
			if(redisTemplate.hasKey("userMenuTree:"+id)){
				redisTemplate.delete("userMenuTree:"+id);
			}
		}
		return "success";
	}

}
