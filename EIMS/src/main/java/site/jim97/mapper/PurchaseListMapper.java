package site.jim97.mapper;

import java.util.List;
import java.util.Map;

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

	/**
	 * 用于统计从各供应商进货商品次数
	 */
	@Select("SELECT supplier_name 'name',COUNT(*) 'value' FROM t_purchase_list WHERE goods_id = #{goodsId} GROUP BY supplier_id")
	List<Map<String, Integer>> countPurchaseTime(int goodsId);
	
	/**
	 * 用于统计从各供应商进货商品数量
	 */
	@Select("SELECT supplier_name 'name',SUM(purchase_number) 'value' FROM t_purchase_list WHERE goods_id = #{goodsId} GROUP BY supplier_id")
	List<Map<String, Integer>> countPurchaseNumber(int goodsId);
}
