package site.jim97.entity;

import java.io.Serializable;
import java.util.Date;

import com.baomidou.mybatisplus.annotation.TableName;

import lombok.Data;

@Data
@TableName("t_damage_list")
public class DamageList implements Serializable {

	private static final long serialVersionUID = 1L;
	private Integer id;
	private String damageCode;
	private Integer damageNumber;
	private Date damageDate;
	// 1.报损 2.审核通过
	private Integer status;
	private String goodsName;
	private Integer goodsId;
	private String remarks;
	private String userName;
	private Integer userId;

}
