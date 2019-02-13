package site.jim97.mapper;

import org.apache.ibatis.annotations.Select;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import site.jim97.entity.ReturnList;

public interface ReturnListMapper extends BaseMapper<ReturnList> {
	
	/**
	 * 使用COALESCE避免查找为空返回null
	 * 
	 * @param goodsId
	 * @return
	 */
	@Select("select COALESCE(sum(return_number),0) from t_return_list where goods_id = #{goodsId}")
	int returnNumberSum(int goodsId);

}
