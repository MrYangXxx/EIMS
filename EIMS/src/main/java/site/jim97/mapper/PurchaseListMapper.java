package site.jim97.mapper;

import org.apache.ibatis.annotations.Select;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import site.jim97.entity.PurchaseList;

public interface PurchaseListMapper extends BaseMapper<PurchaseList>{
	
	/**
	 * 使用COALESCE避免查找为空返回null
	 * @param goodsId
	 * @return
	 */
	@Select("select COALESCE(sum(purchase_number),0) from t_purchase_list where goods_id = #{goodsId}")
	int purchaseNumberSum(int goodsId);

}
