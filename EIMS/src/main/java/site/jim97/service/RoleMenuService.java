package site.jim97.service;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import site.jim97.entity.RoleMenu;
import site.jim97.mapper.RoleMenuMapper;

@Service
public class RoleMenuService extends BaseService<RoleMenu> {
	@Autowired
	private RoleMenuMapper mapper;
	@Resource
	private RedisTemplate<String, ?> redisTemplate;

	public void batchDelete(int roleId, String menuIds) {
		if(redisTemplate.hasKey("roleMenuTree:"+roleId)){ //删除缓存
			redisTemplate.delete("roleMenuTree:"+roleId);
		}
		mapper.batchDelete(roleId, menuIds);
	}
}
