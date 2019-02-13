package site.jim97.mapper;

import org.apache.ibatis.annotations.Select;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import site.jim97.entity.OverflowList;

public interface OverflowListMapper extends BaseMapper<OverflowList> {
	/**
	 * 使用COALESCE避免查找为空返回null
	 * 
	 * @param goodsId
	 * @return
	 */
	@Select("select COALESCE(sum(overflow_number),0) from t_overflow_list where goods_id = #{goodsId}")
	int overflowNumberSum(int goodsId);

}
