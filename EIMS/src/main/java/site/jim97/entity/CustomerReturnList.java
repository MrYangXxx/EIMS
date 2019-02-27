package site.jim97.entity;

import java.io.Serializable;
import java.util.Date;

import com.baomidou.mybatisplus.annotation.TableName;

import lombok.Data;
import lombok.ToString;

@Data
@ToString
@TableName("t_customer_return_list")
public class CustomerReturnList implements Serializable {

	private static final long serialVersionUID = 1L;
	private Integer id;
	// 退货编码,XT+时间
	private String customerReturnCode;
	// 退货数量
	private Integer customerReturnNumber;
	// 实际付款
	private Double amountPaid;
	// 单价
	private Double unitPrice;
	private Date customerReturnDate;
	private String remarks;
	private String goodsName;
	private Integer goodsId;
	private Integer state;
	private String userName;
	private Integer userId;
	private String customerName;
	private Integer customerId;

}
