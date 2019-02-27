package site.jim97.entity;

import java.io.Serializable;

import com.baomidou.mybatisplus.annotation.TableName;

import lombok.Data;
import lombok.ToString;

@Data
@ToString
@TableName("t_supplier")
public class Supplier implements Serializable {

	private static final long serialVersionUID = 1L;
	private Integer id;
	private String address;
	private String contact;
	private String name;
	private String phone;
	private String remarks;

}
