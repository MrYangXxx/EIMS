package site.jim97.entity;

import java.io.Serializable;

import com.baomidou.mybatisplus.annotation.TableName;

import lombok.Data;

@Data
@TableName("t_customer")
public class Customer implements Serializable {
	private static final long serialVersionUID = 1L;

	private Integer id;
	private String address;
	private String contact;
	private String name;
	private String phone;
	private String remarks;

}
