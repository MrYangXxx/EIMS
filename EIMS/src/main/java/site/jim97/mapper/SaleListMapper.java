package site.jim97.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import site.jim97.entity.SaleList;

public interface SaleListMapper extends BaseMapper<SaleList> {

	/**
	 * 使用COALESCE避免查找为空返回null
	 * 
	 * @param goodsId
	 * @return
	 */
	@Select("select COALESCE(sum(sale_number),0) from t_sale_list where goods_id = #{goodsId}")
	int saleNumberSum(int goodsId);

	/**
	 * 统计历史进货价格
	 */
	@Select("SELECT price,date FROM t_sale_price WHERE goods_id = #{goodsId}")
	List<Map<String, Integer>> countSalePrice(Integer goodsId);

	/**
	 * 用于统计商品售卖给客户次数
	 */
	@Select("SELECT customer_name 'name',COUNT(*) 'value' FROM t_sale_list WHERE goods_id = #{goodsId} GROUP BY customer_id")
	List<Map<String, Integer>> countSaleTime(int goodsId);

	/**
	 * 用于统计商品售卖给客户数量
	 */
	@Select("SELECT customer_name 'name',SUM(sale_number) 'value' FROM t_sale_list WHERE goods_id = #{goodsId} GROUP BY customer_id")
	List<Map<String, Integer>> countSaleNumber(int goodsId);

	/**
	 * 用于统计商品日销售额
	 */
	
	@Select({ "<script>", "SELECT LEFT(sale_date,10) 'date',SUM(amount_paid) 'value' FROM t_sale_list where 1=1",
			"<when test='goodsId != null'>", "and goods_id = #{goodsId}", "</when>",
			"GROUP BY LEFT(sale_date,10)", "</script>" })
	List<Map<String, Integer>> countDayRevenue(@Param("goodsId")Integer goodsId);

	/**
	 * 用于统计商品月销售额
	 */
	@Select({ "<script>", "SELECT LEFT(sale_date,7) 'date',SUM(amount_paid) 'value' FROM t_sale_list where 1=1",
			"<when test='goodsId != null'>", "and goods_id = #{goodsId}", "</when>",
			" GROUP BY DATE_FORMAT(sale_date,'%m')", "</script>" })
	List<Map<String, Integer>> countMonthRevenue(@Param("goodsId")Integer goodsId);

}
