package site.jim97.entity;

import java.io.Serializable;
import java.util.Date;

import com.baomidou.mybatisplus.annotation.TableName;

import lombok.Data;
import lombok.ToString;

@Data
@ToString
@TableName("t_overflow_list")
public class OverflowList implements Serializable {

	private static final long serialVersionUID = 1L;
	private Integer id;
	private String overflowCode;
	private Integer overflowNumber;
	private Date overflowDate;
	private Integer goodsId;
	private String goodsName;
	// 1.报溢 2.审核通过
	private Integer status;
	private String remarks;
	private String userName;
	private Integer userId;

}
