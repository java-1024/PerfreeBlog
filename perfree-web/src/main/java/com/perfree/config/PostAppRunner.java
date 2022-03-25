package com.perfree.config;

import cn.hutool.core.io.file.FileReader;
import cn.hutool.core.util.CharsetUtil;
import cn.hutool.setting.dialect.Props;
import com.alibaba.druid.pool.DruidDataSource;
import com.perfree.commons.DynamicDataSource;
import com.perfree.commons.EhCacheUtil;
import com.perfree.commons.FileUtil;
import com.perfree.commons.StringUtil;
import com.perfree.enums.EhCacheEnum;
import com.perfree.enums.SystemEnum;
import com.perfree.service.OptionService;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import java.io.File;
import java.sql.Connection;

@Component
public class PostAppRunner implements ApplicationRunner {
    private final static Logger LOGGER = LoggerFactory.getLogger(PostAppRunner.class);
    @Value("${server.port}")
    private int serverPort;

    @Value("${version}")
    private String version;

    private final OptionService optionService;

    public PostAppRunner(OptionService optionService) {
        this.optionService = optionService;
    }

    @Override
    public void run(ApplicationArguments args) throws Exception {
        EhCacheUtil.removeAll(EhCacheEnum.EHCACHE_KEY_OPTION_DATA.getValue());
        if (!initDataSource()) {
            runSuccess();
            return;
        }
        updateSql();
        optionService.initOptionCache();
        loadDirective();
        runSuccess();
    }


    /**
     * 初始化数据库
     */
    private boolean initDataSource(){
        File file = new File(SystemEnum.DB_SETTING_PATH.getValue());
        if (!file.exists()) {
            return false;
        }
        Props dbSetting = new Props(file, CharsetUtil.CHARSET_UTF_8);
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
        return true;
    }

    /**
     * 更新数据库
     */
    private void updateSql() {
        try(Connection connection = DynamicDataSource.getDataSource().getConnection()){
            File file = new File(SystemEnum.DB_SETTING_PATH.getValue());
            Props dbSetting = new Props(file, CharsetUtil.CHARSET_UTF_8);
            File sqlFile;
            if (DynamicDataSource.dataSourceType.equals("mysql")) {
                sqlFile = new File("resources/update.sql");
                if(!sqlFile.exists()){
                    sqlFile = FileUtil.getClassPathFile("classpath:update.sql");
                }
            } else {
                sqlFile = new File("resources/update-sqlite.sql");
                if(!sqlFile.exists()){
                    sqlFile = FileUtil.getClassPathFile("classpath:update-sqlite.sql");
                }
            }
            if (sqlFile != null && sqlFile.exists()) {
                FileReader fileReader = new FileReader(sqlFile);
                String updateFileStr = fileReader.readString();
                String[] updateStrSplit = updateFileStr.split("--PerfreeBlog");
                String dbVersion = StringUtils.isBlank(dbSetting.getStr("dataVersion")) ? "v1.0.0" : dbSetting.getStr("dataVersion");
                for (int i = 1; i < updateStrSplit.length; i++) {
                    String[] split = updateStrSplit[i].split(";");
                    long currUpdateVersion = StringUtil.versionToLong(split[0]);
                    // 1. 更新sql版本等于最新项目版本
                    // 2. 更新sql版本 大于 dbVersion
                    // 3. 更新sql版本 小于等于最新项目版本
                    if ((currUpdateVersion == StringUtil.versionToLong(version) || currUpdateVersion > StringUtil.versionToLong(dbVersion)) &&
                            currUpdateVersion <= StringUtil.versionToLong(version)) {
                        for (int j = 1; j < split.length; j++){
                            try{
                                if(StringUtils.isNotBlank(split[j])){
                                    connection.prepareStatement(split[j]).execute();
                                    LOGGER.info("update: {}", split[j]);
                                }
                            }catch (Exception e) {
                                e.printStackTrace();
                                LOGGER.info("执行update sql出错，SQL语句: {}，错误信息：{}", split[j],e.getMessage());
                            }
                        }
                    }
                }

                dbSetting.setProperty("dataVersion", version);
                dbSetting.store(file.getAbsolutePath());
            }
        }catch (Exception e) {
            e.printStackTrace();
            LOGGER.info("更新数据库出错,请手动执行update.sql中相应版本的sql语句, 错误信息: {}", e.getMessage());
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
