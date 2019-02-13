package site.jim97.entity;

import com.baomidou.mybatisplus.annotation.TableName;

import lombok.Data;

@Data
@TableName("t_role")
public class Role {
    private Integer id;

    private String name;

    private String remarks;

}