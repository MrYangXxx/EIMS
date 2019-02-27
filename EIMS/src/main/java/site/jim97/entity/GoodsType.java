package site.jim97.entity;

import java.io.Serializable;
import java.util.List;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;

import lombok.Data;
import lombok.ToString;

@Data
@ToString
@TableName("t_goods_type")
public class GoodsType implements Serializable {

	private static final long serialVersionUID = 1L;
	private Integer id;
	private String name;
	private Integer pId;
	private String pName;
	private Integer state;
	private String icon;

	@TableField(exist=false)
	private List<GoodsType> children;

}
