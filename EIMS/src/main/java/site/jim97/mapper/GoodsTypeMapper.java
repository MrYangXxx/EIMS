package site.jim97.mapper;

import org.apache.ibatis.annotations.Select;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import site.jim97.entity.GoodsType;

public interface GoodsTypeMapper extends BaseMapper<GoodsType>{

	@Select("select id from t_goods_type where p_id = #{id}")
	String[] getChildIds(int id);
}
