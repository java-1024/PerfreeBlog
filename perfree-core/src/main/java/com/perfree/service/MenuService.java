package com.perfree.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.perfree.model.Menu;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author Perfree
 * @since 2022-03-19
 */
public interface MenuService extends IService<Menu> {
    /**
     * 根据类型获取菜单树
     * @param type type
     * @return List<Menu>
     */
    List<Menu> getMenusByType(int type);
}
