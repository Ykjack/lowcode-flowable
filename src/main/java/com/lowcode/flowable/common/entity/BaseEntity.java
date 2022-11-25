package com.lowcode.flowable.common.entity;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

import java.io.Serializable;

/**
 * @Author: mayike
 * @Date: 2022/11/21 14:58
 */
@Data
public class BaseEntity implements Serializable {
    /**
     * 主键自增
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * 新增操作自动填充created_at
     */
    @TableField(value = "created_at", fill = FieldFill.INSERT)
    private Integer createdAt;

    /**
     * 保存操作自动填充updated_at
     */
    @TableField(value = "updated_at", fill = FieldFill.INSERT_UPDATE)
    private Integer updatedAt;
}
