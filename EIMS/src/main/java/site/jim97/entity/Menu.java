package site.jim97.entity;

import java.io.Serializable;
import java.util.List;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;

import lombok.Data;

@Data
@TableName("t_menu")
public class Menu implements Serializable {

	private static final long serialVersionUID = 1L;

	private Integer id; // 编号

	private String name; // 菜单名称

	private String url; // 菜单地址

	private String icon; // 图标

	private Integer pId; // 父菜单Id

	private String pName;

	private String remarks;
	
	//用于权限设置菜单
	@TableField(exist=false)
	private List<Menu> children;
	@TableField(exist=false)
	private Boolean checked;
}
