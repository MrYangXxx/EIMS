package site.jim97.entity;

import java.io.Serializable;
import java.util.Date;
import lombok.Data;
import com.baomidou.mybatisplus.annotation.TableName;

@Data
@TableName("t_return_list")
public class ReturnList implements Serializable {

	private static final long serialVersionUID = 1L;
	private Integer id;
	// 退货单号,TH+时间
	private String returnCode;
	// 退货数量
	private Integer returnNumber;
	// 实收金额
	private Double amountPaid;
	// 单价
	private Double unitPrice;
	private Date returnDate;
	private String remarks;
	private String goodsName;
	private Integer goodsId;
	private Integer state;
	private String supplierName;
	private Integer supplierId;
	private String userName;
	private Integer userId;

}
