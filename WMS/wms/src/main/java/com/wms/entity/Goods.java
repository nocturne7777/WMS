package com.wms.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import lombok.Data;

/**
 * @TableName goods
 */
@TableName(value ="goods")
@Data
public class Goods implements Serializable {
    private Integer id;

    private String name;

    private Integer storage;

    private Integer goodstype;

    private Integer count;

    private String remark;

    private static final long serialVersionUID = 1L;
}