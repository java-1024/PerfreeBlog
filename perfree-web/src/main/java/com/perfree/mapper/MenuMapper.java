package com.perfree.mapper;

import com.perfree.model.Menu;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author Perfree
 * @since 2022-03-19
 */
@Component
public interface MenuMapper extends BaseMapper<Menu> {

    List<Menu> getMenusByType(int type);
}
