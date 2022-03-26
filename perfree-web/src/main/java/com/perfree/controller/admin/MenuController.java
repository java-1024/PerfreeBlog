package com.perfree.controller.admin;

import com.perfree.commons.ResponseBean;
import com.perfree.enums.MenuEnum;
import com.perfree.model.Menu;
import com.perfree.service.MenuService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/admin/menu")
@Api(value = "菜单相关",tags = "菜单相关")
public class MenuController {

    private final MenuService menuService;

    public MenuController(MenuService menuService) {
        this.menuService = menuService;
    }


    @GetMapping("/menuTree")
    @ApiOperation(value = "获取后台菜单树", notes = "获取后台菜单树")
    public ResponseBean getAdminMenuList() {
        List<Menu> menus = menuService.getMenusByType((Integer) MenuEnum.MENU_TYPE_ADMIN.getValue());
        return ResponseBean.success("success", menus);
    }
}
