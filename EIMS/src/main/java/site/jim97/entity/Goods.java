package site.jim97.entity;

import java.io.Serializable;

import com.baomidou.mybatisplus.annotation.TableName;

import lombok.Data;
import lombok.ToString;

@Data
@ToString
@TableName("t_goods")
public class Goods implements Serializable {

	private static final long serialVersionUID = 1L;
	private Integer id;
	private String code; //商品自身代码/编号/标识符
	private Integer inventoryQuantity;
	private Integer minNum; //库存下限
	private String spec; //商品规格
	private String name;
	private int supplierId; //默认/常备供应商Id
	private String supplierName; //默认/常备供应商
	private Double purchasingPrice; 
	private String remarks;
	private Double sellingPrice;
	private Integer typeId;
	private String typeName;
	private Integer state;
	private Double lastPurchasingPrice; //上次采购价格

}
