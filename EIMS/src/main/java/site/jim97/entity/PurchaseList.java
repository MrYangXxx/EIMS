package site.jim97.entity;

import java.io.Serializable;
import java.util.Date;

import com.baomidou.mybatisplus.annotation.TableName;

import lombok.Data;
import lombok.ToString;

@Data
@ToString
@TableName("t_purchase_list")
public class PurchaseList implements Serializable {

	private static final long serialVersionUID = 1L;
	private Integer id;
	// 进货单号,JH+时间
	private String purchaseCode;
	//进货数量
	private Integer purchaseNumber;
	// 实付金额
	private Double amountPaid;
	// 单价
	private Double unitPrice;
	private Date purchaseDate;
	private String goodsName;
	private Integer goodsId;
	private String remarks;
	private Integer state;
	private String supplierName;
	private Integer supplierId;
	private String userName;
	private Integer userId;

}
