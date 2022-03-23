package com.perfree.config;

import cn.hutool.core.io.FileUtil;
import cn.hutool.core.util.CharsetUtil;
import cn.hutool.setting.dialect.Props;
import com.alibaba.druid.pool.DruidDataSource;
import com.perfree.commons.DynamicDataSource;
import com.perfree.dataBase.DataBaseUtils;
import com.perfree.dataBase.TableModel;
import com.perfree.enums.SystemEnum;
import com.perfree.service.InstallService;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import java.io.File;
import java.util.List;

@Component
public class PostAppRunner implements ApplicationRunner {
    private final static Logger LOGGER = LoggerFactory.getLogger(PostAppRunner.class);
    @Value("${server.port}")
    private int serverPort;

    private final InstallService installService;

    public PostAppRunner(InstallService installService) {
        this.installService = installService;
    }

    @Override
    public void run(ApplicationArguments args) throws Exception {
        if (!initDataSource()) {
            runSuccess();
            return;
        }
        loadDirective();
        runSuccess();
    }

    /**
     * 初始化数据库
     */
    private boolean initDataSource() throws Exception {
        File file = new File(SystemEnum.DB_SETTING_PATH.getValue());
        if (!file.exists()) {
            return false;
        }
        Props dbSetting = new Props(FileUtil.touch(file), CharsetUtil.CHARSET_UTF_8);
        String installStatus = dbSetting.getStr("installStatus");
        if (StringUtils.isBlank(installStatus)){
            return false;
        }
        DruidDataSource druidDataSource = DynamicDataSource.getDataSource();
        if (druidDataSource.isInited()){
            druidDataSource.close();
            druidDataSource = new DruidDataSource();
        }
        druidDataSource.setUrl(dbSetting.getStr("url"));
        if (dbSetting.getStr("type").equals("mysql")){
            druidDataSource.setUsername(dbSetting.getStr("username"));
            druidDataSource.setPassword(dbSetting.getStr("password"));
        }
        druidDataSource.setDriverClassName(dbSetting.getStr("driverClassName"));
        DynamicDataSource.setDataSource(druidDataSource,dbSetting.getStr("type"));
        dbSetting.autoLoad(true);
        if (dbSetting.getStr("type").equals(SystemEnum.DB_TYPE_MYSQL.getValue())){
            List<TableModel> tableModelList = installService.queryMysqlTables();
            DataBaseUtils.initOrUpdateMysqlDataBase(tableModelList);
            return true;
        } else if (dbSetting.getStr("type").equals(SystemEnum.DB_TYPE_SQLITE.getValue())){
            List<TableModel> tableModelList = installService.querySqliteTables();
            DataBaseUtils.initOrUpdateSqliteDataBase(tableModelList);
            return true;
        } else{
            return false;
        }
    }

    /**
     * 加载模板指令
     */
    private void loadDirective(){

    }

    private void runSuccess(){
        LOGGER.info("--------------------启动成功------------------------");
        LOGGER.info("--------------------访问端口{}---------------------", serverPort);
    }

}
