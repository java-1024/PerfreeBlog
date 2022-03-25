package com.perfree.controller.admin;

import com.perfree.base.BaseController;
import com.perfree.commons.DynamicDataSource;
import com.perfree.commons.OptionCacheUtil;
import com.perfree.commons.ResponseBean;
import com.perfree.enums.OptionEnum;
import com.perfree.form.InstallDataBaseForm;
import com.perfree.form.InstallUserForm;
import com.perfree.model.User;
import com.perfree.service.InstallService;
import com.perfree.service.UserService;
import io.swagger.annotations.Api;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/install")
@Api(value = "安装",tags = "安装", hidden = true)
public class InstallController extends BaseController {
    private final static Logger LOGGER = LoggerFactory.getLogger(InstallController.class);

    private final InstallService installService;
    private final UserService userService;

    public InstallController(InstallService installService, UserService userService) {
        this.installService = installService;
        this.userService = userService;
    }

    /**
     * 检查数据库是否存在
     * @param installDataBaseForm installDataBaseForm
     * @return ResponseBean
     */
    @PostMapping("/checkDataBase")
    public ResponseBean checkDataBase(@RequestBody @Valid InstallDataBaseForm installDataBaseForm) {
        ResponseBean responseBean = installDataBaseValid(installDataBaseForm);
        if (responseBean != null) {
            return responseBean;
        }
        try {
            boolean result = installService.checkDataBase(installDataBaseForm);
            return ResponseBean.success( "success", result);
        } catch (Exception e) {
            e.printStackTrace();
            LOGGER.error(e.getMessage());
            return ResponseBean.fail("数据库连接失败,请检查数据库配置信息:" + e.getMessage(), null);
        }
    }

    /**
     * 初始化数据库
     * @param installDataBaseForm installDataBaseForm
     * @return ResponseBean
     */
    @PostMapping("/installDataBase")
    public ResponseBean installDataBase(@RequestBody @Valid InstallDataBaseForm installDataBaseForm) {
        ResponseBean responseBean = installDataBaseValid(installDataBaseForm);
        if (responseBean != null) {
            return responseBean;
        }
        try {
            installService.install(installDataBaseForm);
            return ResponseBean.success( "success", null);
        } catch (Exception e) {
            e.printStackTrace();
            LOGGER.error(e.getMessage());
            return ResponseBean.fail("数据库连接或初始化数据库失败:" + e.getMessage(), null);
        }
    }

    @PostMapping("/initUser")
    public ResponseBean initUser(@RequestBody @Valid InstallUserForm installUserForm) {
        try {
            User user = userService.getLoginInfo(installUserForm.getAccount());
            if (user != null) {
                return ResponseBean.fail("创建管理员信息失败:该账户已存在", null);
            }
            installService.initUser(installUserForm);
            return ResponseBean.success( "success", null);
        } catch (Exception e) {
            e.printStackTrace();
            LOGGER.error(e.getMessage());
            return ResponseBean.fail("创建管理员信息失败:" + e.getMessage(), null);
        }
    }

    /**
     * 获取安装状态
     * @return ResponseBean
     */
    @GetMapping("/installStatus")
    public ResponseBean installStatus() {
        if (DynamicDataSource.dataSourceIsInit && OptionCacheUtil.getBoolValue(OptionEnum.IS_INSTALLED.getValue())) {
            return ResponseBean.success("success", true);
        }
        return ResponseBean.success("success", false);
    }


    /**
     * 数据库相关校验
     * @param installDataBaseForm installDataBaseForm
     * @return ResponseBean
     */
    private ResponseBean installDataBaseValid(InstallDataBaseForm installDataBaseForm){
        if (DynamicDataSource.dataSourceIsInit && OptionCacheUtil.getBoolValue(OptionEnum.IS_INSTALLED.getValue())) {
            return ResponseBean.fail("检测到系统已安装过,请勿重复安装!", null);
        }
        if (installDataBaseForm.getDataBaseType().equals("mysql")) {
            if (StringUtils.isBlank(installDataBaseForm.getDataBaseAddress()) || StringUtils.isBlank(installDataBaseForm.getDataBaseUserName()) ||
                    StringUtils.isBlank(installDataBaseForm.getDataBasePassword()) || StringUtils.isBlank(installDataBaseForm.getDataBasePort())) {
                return ResponseBean.fail("请完整填写数据库信息", null);
            }
        }
        return null;
    }
}
