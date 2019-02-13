package site.jim97.entity;

import com.baomidou.mybatisplus.annotation.TableName;

import lombok.Data;

@Data
@TableName("t_user_role")
public class UserRole {
    private Integer id;

    private Integer roleId;

    private Integer userId;

}