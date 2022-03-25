package com.perfree.service.impl;

import cn.hutool.core.io.FileUtil;
import cn.hutool.core.io.file.FileReader;
import cn.hutool.core.util.CharsetUtil;
import cn.hutool.setting.dialect.Props;
import com.alibaba.druid.pool.DruidDataSource;
import com.perfree.commons.DynamicDataSource;
import com.perfree.commons.GravatarUtil;
import com.perfree.commons.StringUtil;
import com.perfree.enums.OptionEnum;
import com.perfree.enums.RoleEnum;
import com.perfree.enums.SystemEnum;
import com.perfree.enums.UserEnum;
import com.perfree.form.InstallDataBaseForm;
import com.perfree.form.InstallUserForm;
import com.perfree.mapper.InstallMapper;
import com.perfree.model.Option;
import com.perfree.model.Role;
import com.perfree.service.InstallService;
import com.perfree.service.OptionService;
import com.perfree.service.RoleService;
import com.perfree.service.UserService;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.crypto.hash.Md5Hash;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.util.List;

@Service
public class InstallServiceImpl implements InstallService {
    private final static Logger LOGGER = LoggerFactory.getLogger(InstallServiceImpl.class);

    @Value("${version}")
    private String version;

    private final InstallMapper installMapper;
    private final OptionService optionService;
    private final UserService userService;
    private final RoleService roleService;

    public InstallServiceImpl(InstallMapper installMapper, OptionService optionService,
                              UserService userService, RoleService roleService) {
        this.installMapper = installMapper;
        this.optionService = optionService;
        this.userService = userService;
        this.roleService = roleService;
    }

    @Override
    public void install(InstallDataBaseForm installDataBaseForm) throws Exception {
        if (installDataBaseForm.getInstallType() == InstallDataBaseForm.INSTALL_SKIP) {
            installDone();
            return;
        }
        initData(installDataBaseForm);
    }

    @Override
    public boolean checkDataBase(InstallDataBaseForm installDataBaseForm) throws Exception {
        if (installDataBaseForm.getDataBaseType().equals(SystemEnum.DB_TYPE_MYSQL.getValue())) {
            initMysqlDataBase(installDataBaseForm);
            List<String> tables = installMapper.queryMysqlTables();
            return tables.size() > 0;
        } else if (installDataBaseForm.getDataBaseType().equals(SystemEnum.DB_TYPE_SQLITE.getValue())) {
            initSqliteDataBase();
            List<String> tables = installMapper.querySqliteTables();
            return tables.size() > 0;
        } else {
            LOGGER.error("不支持的数据库类型:{}", installDataBaseForm.getDataBaseType());
            throw new Exception("不支持的数据库类型:" + installDataBaseForm.getDataBaseType());
        }
    }

    @Override
    public void initUser(InstallUserForm installUserForm) {
        Role role = roleService.getByCode(RoleEnum.ADMIN_ROLE.getCode());
        installUserForm.setRoleId(role.getId());
        installUserForm.setStatus((Integer) UserEnum.USER_STATUS_ENABLE.getValue());
        installUserForm.setAvatar(GravatarUtil.getGravatar(installUserForm.getEmail()));
        installUserForm.setSalt(StringUtil.getUUID());
        installUserForm.setPassword(new Md5Hash(installUserForm.getPassword(), installUserForm.getSalt()).toString());
        userService.save(installUserForm);
        installDone();
    }

    /**
     * 初始化mysql数据库
     */
    private void initMysqlDataBase(InstallDataBaseForm installForm) throws Exception{
        DruidDataSource druidDataSource = DynamicDataSource.getDataSource();
        if (druidDataSource.isInited()){
            druidDataSource.close();
            druidDataSource = new DruidDataSource();
        }
        File file = new File(SystemEnum.DB_SETTING_PATH.getValue());
        Props setting = new Props(FileUtil.touch(file), CharsetUtil.CHARSET_UTF_8);
        String format = "jdbc:mysql://%s:%s/%s?createDatabaseIfNotExist=true&useUnicode=true&characterEncoding=UTF-8&serverTimezone=UTC&useSSL=false&allowPublicKeyRetrieval=true";
        if (StringUtils.isBlank(installForm.getDataBaseName())) {
            installForm.setDataBaseName("perfree");
        }
        String url = String.format(format, installForm.getDataBaseAddress(), installForm.getDataBasePort(), installForm.getDataBaseName());
        druidDataSource.setUrl(url);
        druidDataSource.setUsername(installForm.getDataBaseUserName());
        druidDataSource.setPassword(installForm.getDataBasePassword());
        druidDataSource.setDriverClassName("com.mysql.jdbc.Driver");
        DynamicDataSource.setDataSource(druidDataSource, SystemEnum.DB_TYPE_MYSQL.getValue());
        setting.setProperty("url",url);
        setting.setProperty("username",installForm.getDataBaseUserName());
        setting.setProperty("password",installForm.getDataBasePassword());
        setting.setProperty("driverClassName","com.mysql.jdbc.Driver");
        setting.setProperty("type",SystemEnum.DB_TYPE_MYSQL.getValue());
        setting.setProperty("dataVersion", version);
        setting.store(file.getAbsolutePath());
    }

    /**
     * 初始化sqlite数据库
     */
    private void initSqliteDataBase() throws Exception {
        DruidDataSource druidDataSource = DynamicDataSource.getDataSource();
        if (druidDataSource.isInited()){
            druidDataSource.close();
            druidDataSource = new DruidDataSource();
        }
        File file = new File(SystemEnum.DB_SETTING_PATH.getValue());
        String url = "jdbc:sqlite:resources/db/perfree.db?date_string_format=yyyy-MM-dd HH:mm:ss";
        druidDataSource.setUrl(url);
        druidDataSource.setDriverClassName("org.sqlite.JDBC");
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

        DynamicDataSource.setDataSource(druidDataSource, SystemEnum.DB_TYPE_SQLITE.getValue());
        Props setting = new Props(FileUtil.touch(file), CharsetUtil.CHARSET_UTF_8);
        setting.setProperty("url",url);
        setting.setProperty("driverClassName","org.sqlite.JDBC");
        setting.setProperty("type", SystemEnum.DB_TYPE_SQLITE.getValue());
        setting.setProperty("dataVersion", version);
        setting.store(file.getAbsolutePath());
    }

    /**
     * 初始化数据
     * @param installDataBaseForm installDataBaseForm
     * @throws Exception e
     */
    private void initData(InstallDataBaseForm installDataBaseForm) throws Exception {
        File sqlFile = new File(SystemEnum.MYSQL_PROD_SCRIPT.getValue());
        if (!sqlFile.exists()){
            sqlFile = FileUtil.file(SystemEnum.MYSQL_DEV_SCRIPT.getValue());
        }
        if (installDataBaseForm.getDataBaseType().equals(SystemEnum.DB_TYPE_MYSQL.getValue())) {
            initMysqlDataBase(installDataBaseForm);
        } else if (installDataBaseForm.getDataBaseType().equals(SystemEnum.DB_TYPE_SQLITE.getValue())) {
            initSqliteDataBase();
            sqlFile = new File(SystemEnum.SQLITE_PROD_SCRIPT.getValue());
            if (!sqlFile.exists()){
                sqlFile = FileUtil.file(SystemEnum.SQLITE_DEV_SCRIPT.getValue());
            }
        } else {
            LOGGER.error("不支持的数据库类型:{}", installDataBaseForm.getDataBaseType());
            throw new Exception("不支持的数据库类型:" + installDataBaseForm.getDataBaseType());
        }

        try(Connection connection = DynamicDataSource.getDataSource().getConnection()){
            FileReader fileReader = new FileReader(sqlFile);
            String createSql = fileReader.readString();
            String[] split = createSql.split(";");
            for (int i = 0; i < split.length - 1; i++){
                connection.prepareStatement(split[i]).execute();
            }
        }catch (Exception e) {
            e.printStackTrace();
            LOGGER.error("执行初始化数据库脚本失败:{}", e.getMessage());
            throw new Exception("执行初始化数据库脚本失败:" + e.getMessage());
        }
    }

    /**
     * 安装完成执行的事件
     */
    private void installDone() {
        File file = new File(SystemEnum.DB_SETTING_PATH.getValue());
        Props setting = new Props(file, CharsetUtil.CHARSET_UTF_8);
        setting.setProperty("installStatus", "dbSuccess");
        setting.store(file.getAbsolutePath());
        Option option = optionService.getByKey(OptionEnum.IS_INSTALLED.getValue());
        if (option == null) {
            optionService.save(new Option(OptionEnum.IS_INSTALLED.getValue(), "true"));
        }
        optionService.initOptionCache();
    }
}
