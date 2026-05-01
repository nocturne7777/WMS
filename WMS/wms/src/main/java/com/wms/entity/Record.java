package com.wms.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.util.Date;
import lombok.Data;

/**
 * @TableName record
 */
@TableName(value ="record")
@Data
public class Record implements Serializable {
    private Integer id;

    private Integer goods;

    private Integer userid;

    private Integer adminId;

    private Integer count;

    private Date createtime;

    private String remark;

    private static final long serialVersionUID = 1L;

    @TableField(exist = false)
    private Boolean action;
}