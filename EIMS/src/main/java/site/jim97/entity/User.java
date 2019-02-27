package site.jim97.entity;

import java.io.Serializable;

import com.baomidou.mybatisplus.annotation.TableName;

import lombok.Data;
import lombok.ToString;

@Data
@ToString(exclude="password")
@TableName(value="t_user")
public class User implements Serializable{
	private static final long serialVersionUID = 1L;

    private Integer id;

    private String bz;

    private String password;

    private String trueName;

    private String userName;

    private String remarks;

    private String phone;
    
    private String email;
}