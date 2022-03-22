package com.perfree.controller.admin;

import com.perfree.base.BaseController;
import com.perfree.commons.ResponseBean;
import com.perfree.form.InstallForm;
import com.perfree.service.InstallService;
import io.swagger.annotations.Api;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping("/install")
@Api(value = "安装",tags = "安装", hidden = true)
public class InstallController extends BaseController {
    private final static Logger LOGGER = LoggerFactory.getLogger(InstallController.class);

    private final InstallService installService;

    public InstallController(InstallService installService) {
        this.installService = installService;
    }

    @PostMapping
    public ResponseBean install(@RequestBody @Valid InstallForm installForm) {
        if (installForm.getDataBaseType().equals("mysql")) {
            if (StringUtils.isBlank(installForm.getDataBaseAddress()) || StringUtils.isBlank(installForm.getDataBaseUserName()) ||
                    StringUtils.isBlank(installForm.getDataBasePassword()) || StringUtils.isBlank(installForm.getDataBasePort())) {
                return ResponseBean.fail("请完整填写数据库信息", null);
            }
        }
        try {
            installService.install(installForm);
            return ResponseBean.success( "success", null);
        } catch (Exception e) {
            e.printStackTrace();
            LOGGER.error(e.getMessage());
            return ResponseBean.fail("数据库连接或创建数据库失败:" + e.getMessage(), null);
        }
    }
}
