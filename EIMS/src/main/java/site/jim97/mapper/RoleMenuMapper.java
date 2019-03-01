package site.jim97.mapper;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Param;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import site.jim97.entity.RoleMenu;

public interface RoleMenuMapper extends BaseMapper<RoleMenu>{

	//in内的参数使用$符号，用#号将当成字符串处理
	@Delete("delete from t_role_menu where role_id = #{roleId} and menu_id in (${menuIds})")
	void batchDelete(@Param("roleId")int roleId,@Param("menuIds")String menuIds);
}
