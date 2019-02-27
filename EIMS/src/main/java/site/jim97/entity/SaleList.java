package site.jim97.entity;

import java.io.Serializable;
import java.util.Date;

import com.baomidou.mybatisplus.annotation.TableName;

import lombok.Data;
import lombok.ToString;

@Data
@ToString
@TableName("t_sale_list")
public class SaleList implements Serializable {

	private static final long serialVersionUID = 1L;
	private Integer id;
	// 售卖编号,XS+时间
	private String saleCode;
	// 实收款
	private Double amountPaid;
	// 单价
	private Double unitPrice;
	private String remarks;
	private String goodsName;
	private Integer goodsId;
	// 售卖日期
	private Date saleDate;
	// 售卖数量
	private Integer saleNumber;
	private Integer state;
	// 负责人
	private String userName;
	private Integer userId;
	// 客户
	private String customerName;
	private Integer customerId;

}
