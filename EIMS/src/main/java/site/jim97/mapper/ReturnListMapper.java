package site.jim97.mapper;

import java.util.List;
import java.util.Map;

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

	/**
	 * 用于统计从各客户退货商品次数
	 */
	@Select("SELECT supplier_name 'name',COUNT(return_number) 'value' FROM t_return_list WHERE goods_id = #{goodsId} GROUP BY supplier_id")
	List<Map<String, Integer>> countReturnTime(int goodsId);
	
	/**
	 * 用于统计从各客户退货商品数量
	 */
	@Select("SELECT supplier_name 'name',SUM(return_number) 'value' FROM t_return_list WHERE goods_id = #{goodsId} GROUP BY supplier_id")
	List<Map<String, Integer>> countReturnNumber(int goodsId);
}
