package site.jim97.mapper;

import java.util.List;
import java.util.Map;

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
	
	/**
	 * 用于统计客户退货次数
	 */
	@Select("SELECT customer_name 'name',COUNT(*) 'value' FROM t_customer_return_list WHERE goods_id = #{goodsId} GROUP BY customer_id")
	List<Map<String, Integer>> countCustomerReturnTime(int goodsId);
	
	/**
	 * 用于统计客户退货数量
	 */
	@Select("SELECT customer_name 'name',SUM(customer_return_number) 'value' FROM t_customer_return_list WHERE goods_id = #{goodsId} GROUP BY customer_id")
	List<Map<String, Integer>> countCustomerReturnNumber(int goodsId);
}
