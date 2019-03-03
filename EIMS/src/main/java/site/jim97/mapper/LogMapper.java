package site.jim97.mapper;

import org.apache.ibatis.annotations.Delete;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import site.jim97.entity.Log;

public interface LogMapper extends BaseMapper<Log>{
	
	/**
	 * 查询类日志一周前的删除
	 */
	@Delete("DELETE FROM t_log WHERE type_id=1 and DATEDIFF(NOW(),add_time) >= 7")
	void deleteByWeek();
	
	/**
	 * 保存和删除类日志一月前的删除
	 */
	@Delete("DELETE FROM t_log WHERE type_id=2 or type_id=3 and DATEDIFF(NOW(),add_time) >= 30")
	void deleteByMonth();

}
