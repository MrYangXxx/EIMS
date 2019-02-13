package site.jim97.entity;

import com.baomidou.mybatisplus.annotation.TableName;

import lombok.Data;

@Data
@TableName("t_role_menu")
public class RoleMenu {
    private Integer id;

    private Integer menuId;

    private Integer roleId;

}