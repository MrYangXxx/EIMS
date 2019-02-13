package site.jim97.mapper;

import org.apache.ibatis.annotations.Select;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import site.jim97.entity.CustomerReturnList;

public interface CustomerReturnListMapper extends BaseMapper<CustomerReturnList>{
	/**
	 * 使用COALESCE避免查找为空返回null
	 * @param goodsId
	 * @return
	 */
	@Select("select COALESCE(sum(customer_return_number),0) from t_customer_return_list where goods_id = #{goodsId}")
	int customerReturnNumberSum(int goodsId);
}
