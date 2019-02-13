package site.jim97.mapper;

import org.apache.ibatis.annotations.Select;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import site.jim97.entity.DamageList;

public interface DamageListMapper extends BaseMapper<DamageList>{

	/**
	 * 使用COALESCE避免查找为空返回null
	 * @param goodsId
	 * @return
	 */
	@Select("select COALESCE(sum(damage_number),0) from t_damage_list where goods_id = #{goodsId}")
	int damageNumberSum(int goodsId);

}
