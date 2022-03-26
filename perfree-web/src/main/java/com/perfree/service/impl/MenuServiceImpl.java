package com.perfree.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.perfree.mapper.MenuMapper;
import com.perfree.model.Menu;
import com.perfree.service.MenuService;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author Perfree
 * @since 2022-03-19
 */
@Service
public class MenuServiceImpl extends ServiceImpl<MenuMapper, Menu> implements MenuService {

    private final MenuMapper menuMapper;

    public MenuServiceImpl(MenuMapper menuMapper) {
        this.menuMapper = menuMapper;
    }

    @Override
    public List<Menu> getMenusByType(int type) {
        return menuMapper.getMenusByType(type);
    }
}
