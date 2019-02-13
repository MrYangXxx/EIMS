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
	private Date time;
	private String type;
	private Integer trueName; // 操作人名称

}
