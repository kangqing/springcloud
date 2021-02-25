package com.kangqing.kqoauth2authenticationserver.user.domain;

import com.baomidou.mybatisplus.annotation.*;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * <p>
 * 菜单权限表
 * </p>
 *
 * @author yunqing
 * @since 2021-01-01
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@ApiModel(value="MenuResource对象", description="菜单权限表")
public class MenuResource extends Model<MenuResource> {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    @ApiModelProperty(value = "父节点id")
    private Long parentId;

    @ApiModelProperty(value = "节点名称")
    private String name;

    @ApiModelProperty(value = "方法（get、post等）")
    private String method;

    @ApiModelProperty(value = "访问地址")
    private String url;

    @ApiModelProperty(value = "图标样式")
    private String icon;

    @ApiModelProperty(value = "状态：0-禁用，1-启用")
    private Integer status;

    @ApiModelProperty(value = "类型：01=菜单， 02=按钮")
    private Integer type;

    @ApiModelProperty(value = "层级")
    private Integer grades;

    @ApiModelProperty(value = "排序号")
    private Integer seq;

    @ApiModelProperty(value = "逻辑删除标识符， 是否删除0否1是")
    @TableField(fill = FieldFill.INSERT)
    private Integer delCode;

    @ApiModelProperty(value = "乐观锁版本号")
    @Version
    @TableField(fill = FieldFill.INSERT)
    private Long version;

    @ApiModelProperty(value = "创建时间")
    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime createTime;

    @ApiModelProperty(value = "修改时间")
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private LocalDateTime updateTime;


    @Override
    protected Serializable pkVal() {
        return this.id;
    }

}
