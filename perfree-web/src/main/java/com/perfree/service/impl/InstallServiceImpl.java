package com.perfree.service.impl;

import cn.hutool.core.io.FileUtil;
import cn.hutool.core.util.CharsetUtil;
import cn.hutool.setting.dialect.Props;
import com.perfree.commons.DynamicDataSource;
import com.perfree.dataBase.DataBaseUtils;
import com.perfree.dataBase.TableModel;
import com.perfree.enums.SystemEnum;
import com.perfree.form.InstallForm;
import com.perfree.mapper.InstallMapper;
import com.perfree.service.InstallService;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.stereotype.Service;

import javax.sql.DataSource;
import java.io.File;
import java.io.IOException;
import java.util.List;

@Service
public class InstallServiceImpl implements InstallService {
    private final static Logger LOGGER = LoggerFactory.getLogger(InstallServiceImpl.class);

    private final InstallMapper installMapper;

    public InstallServiceImpl(InstallMapper installMapper) {
        this.installMapper = installMapper;
    }

    @Override
    public void install(InstallForm installForm) throws Exception {
        if (installForm.getDataBaseType().equals("mysql")) {
            initMysqlDataBase(installForm);
            List<TableModel> tableModelList = queryMysqlTables();
            DataBaseUtils.initOrUpdateMysqlDataBase(tableModelList);
        }
        if (installForm.getDataBaseType().equals("sqlite")) {
            initSqliteDataBase();
            // DataBaseUtils.initSqliteDataBase();
        }
        installInitOperate();
    }

    @Override
    public List<TableModel> queryMysqlTables() {
        return installMapper.queryMysqlTables();
    }

    /**
     * 初始化mysql数据库
     */
    private void initMysqlDataBase(InstallForm installForm) throws Exception{
        DataSourceBuilder<?> dataSourceBuilder = DataSourceBuilder.create();
        File file = new File(SystemEnum.DB_SETTING_PATH.getValue());
        Props setting = new Props(FileUtil.touch(file), CharsetUtil.CHARSET_UTF_8);
        String format = "jdbc:mysql://%s:%s/%s?createDatabaseIfNotExist=true&useUnicode=true&characterEncoding=UTF-8&serverTimezone=UTC&useSSL=false&allowPublicKeyRetrieval=true";
        if (StringUtils.isBlank(installForm.getDataBaseName())) {
            installForm.setDataBaseName("perfree");
        }
        String url = String.format(format, installForm.getDataBaseAddress(), installForm.getDataBasePort(), installForm.getDataBaseName());
        dataSourceBuilder.url(url);
        dataSourceBuilder.username(installForm.getDataBaseUserName());
        dataSourceBuilder.password(installForm.getDataBasePassword());
        dataSourceBuilder.driverClassName("com.mysql.jdbc.Driver");
        DataSource dataSource = dataSourceBuilder.build();
        DynamicDataSource.setDataSource(dataSource, setting.getStr("type"));
        setting.setProperty("url",url);
        setting.setProperty("username",installForm.getDataBaseUserName());
        setting.setProperty("password",installForm.getDataBasePassword());
        setting.setProperty("driverClassName","com.mysql.jdbc.Driver");
        setting.setProperty("type","mysql");
        setting.setProperty("installStatus","dbSuccess");
        setting.store(file.getAbsolutePath());
    }

    /**
     * 初始化sqlite数据库
     */
    private void initSqliteDataBase() throws Exception {
        DataSourceBuilder<?> dataSourceBuilder = DataSourceBuilder.create();
        File file = new File(SystemEnum.DB_SETTING_PATH.getValue());
        String url = "jdbc:sqlite:resources/db/perfree.db?date_string_format=yyyy-MM-dd HH:mm:ss";
        dataSourceBuilder.url(url);
        dataSourceBuilder.driverClassName("org.sqlite.JDBC");

        File dbFile = new File("resources/db/perfree.db");
        File dir = dbFile.getParentFile();
        if(!dir.exists()){
            if (!dir.mkdirs()) {
                throw new Exception("数据库目录创建失败");
            }
        }
        if(!dbFile.exists()){
            try {
                if (!dbFile.createNewFile()) {
                    throw new Exception("数据库文件创建失败");
                }
            } catch (IOException e) {
                throw new Exception("数据库文件创建失败" + e.getMessage());
            }
        }

        DataSource dataSource = dataSourceBuilder.build();
        DynamicDataSource.setDataSource(dataSource, "sqlite");

        Props setting = new Props(FileUtil.touch(file), CharsetUtil.CHARSET_UTF_8);
        setting.setProperty("url",url);
        setting.setProperty("driverClassName","org.sqlite.JDBC");
        setting.setProperty("type","sqlite");
        setting.setProperty("installStatus","dbSuccess");
        setting.store(file.getAbsolutePath());
    }

    /**
     * 安装后的初始化操作
     */
    private void installInitOperate() throws Exception {
        // TODO optionService.initOptionCache();
      /*  List<AdminMenuGroup> adminMenuGroups = MenuManager.initSystemMenu();
        menuService.initSystemMenu(admnMenuGroups);
        try{
            List<Plugin> plugins = piluginService.getAll();
            pluginManagerService.initPlugins(plugins);
        }catch (Exception e) {
            e.printStackTrace();
        }*/
    }
}
