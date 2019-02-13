package site.jim97.mapper;

import org.apache.ibatis.annotations.Select;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import site.jim97.entity.SaleList;

public interface SaleListMapper extends BaseMapper<SaleList>{
	
	/**
	 * 使用COALESCE避免查找为空返回null
	 * 
	 * @param goodsId
	 * @return
	 */
	@Select("select COALESCE(sum(sale_number),0) from t_sale_list where goods_id = #{goodsId}")
	int saleNumberSum(int goodsId);

}
