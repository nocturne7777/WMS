package com.wms.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import lombok.Data;

/**
 * @TableName storage
 */
@TableName(value ="storage")
@Data
public class Storage implements Serializable {
    @TableId(value = "id", type = IdType.AUTO)

    private Integer id;

    private String name;

    private String remark;

    private static final long serialVersionUID = 1L;
}