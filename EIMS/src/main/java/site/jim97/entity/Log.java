package site.jim97.entity;

import java.io.Serializable;
import java.util.Date;

import com.baomidou.mybatisplus.annotation.TableName;

import lombok.Data;

@Data
@TableName("t_log")
public class Log implements Serializable {
	private static final long serialVersionUID = 1L;
	private Integer id;
	private String content;
	private Date addTime;
	private Integer typeId; //1.查2.存3.删4.异
	private String typeName;
	private String trueName; // 操作人名称
	private String ip;
}
