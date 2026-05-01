package com.wms.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.wms.entity.Menu;
import org.apache.ibatis.annotations.Mapper;

/**
 * @author Adachi
 * @description 针对表【menu】的数据库操作Mapper
 * @createDate 2025-06-17 15:29:25
 * @Entity menu.entity.Menu
 */

@Mapper
public interface MenuMapper extends BaseMapper<Menu> {

}
