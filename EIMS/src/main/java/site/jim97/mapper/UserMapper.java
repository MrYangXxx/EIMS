package site.jim97.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import site.jim97.entity.User;

public interface UserMapper extends BaseMapper<User>{

	//方法已废弃，现已采用mybatis-plus的分页查询，方法仅供参考，用法跟mybatis的没有区别
	//List<User> list(@Param("user")User user,@Param("pageBean")PageBean pageBean);
}
