package site.jim97.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import site.jim97.entity.RoleMenu;
import site.jim97.mapper.RoleMenuMapper;

@Service
public class RoleMenuService extends BaseService<RoleMenu> {
	@Autowired
	private RoleMenuMapper mapper;

	public void batchDelete(int roleId, String menuIds) {
		mapper.batchDelete(roleId, menuIds);
	}
}
