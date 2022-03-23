package com.perfree.service.impl;

import cn.hutool.core.io.FileUtil;
import cn.hutool.core.util.CharsetUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.setting.dialect.Props;
import com.alibaba.druid.pool.DruidDataSource;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.perfree.commons.DynamicDataSource;
import com.perfree.commons.GravatarUtil;
import com.perfree.commons.OptionUtils;
import com.perfree.commons.StringUtil;
import com.perfree.dataBase.DataBaseUtils;
import com.perfree.dataBase.TableFieldModel;
import com.perfree.dataBase.TableModel;
import com.perfree.enums.OptionEnum;
import com.perfree.enums.RoleEnum;
import com.perfree.enums.SystemEnum;
import com.perfree.form.InstallForm;
import com.perfree.mapper.*;
import com.perfree.model.Option;
import com.perfree.model.Role;
import com.perfree.model.User;
import com.perfree.service.*;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.crypto.hash.Md5Hash;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.File;
import java.io.IOException;
import java.util.List;

@Service
public class InstallServiceImpl implements InstallService {
    private final static Logger LOGGER = LoggerFactory.getLogger(InstallServiceImpl.class);

    private final InstallMapper installMapper;
    private final OptionService optionService;
    private final UserService userService;
    private final MenuService menuService;
    private final RoleService roleService;
    private final ArticleService articleService;
    private final CommentService commentService;

    public InstallServiceImpl(InstallMapper installMapper, OptionService optionService,
                              UserService userService, MenuService menuService,RoleService roleService,
                              ArticleService articleService, CommentService commentService) {
        this.installMapper = installMapper;
        this.optionService = optionService;
        this.userService = userService;
        this.menuService = menuService;
        this.roleService = roleService;
        this.articleService = articleService;
        this.commentService = commentService;
    }

    @Override
    public void install(InstallForm installForm) throws Exception {
        if (installForm.getDataBaseType().equals(SystemEnum.DB_TYPE_MYSQL.getValue())) {
            initMysqlDataBase(installForm);
            List<TableModel> tableModelList = queryMysqlTables();
            DataBaseUtils.initOrUpdateMysqlDataBase(tableModelList);
        } else if (installForm.getDataBaseType().equals(SystemEnum.DB_TYPE_SQLITE.getValue())) {
            initSqliteDataBase();
            List<TableModel> tableModelList = querySqliteTables();
            DataBaseUtils.initOrUpdateSqliteDataBase(tableModelList);
        } else {
            LOGGER.error("不支持的数据库类型:{}", installForm.getDataBaseType());
            throw new Exception("不支持的数据库类型:" + installForm.getDataBaseType());
        }
        installInitOperate(installForm);
    }

    @Override
    @Transactional
    public List<TableModel> queryMysqlTables() {
        return installMapper.queryMysqlTables();
    }

    @Override
    public List<TableModel> querySqliteTables() {
        List<TableModel> tableModels = installMapper.querySqliteTables();
        for (TableModel tableModel : tableModels) {
            List<TableFieldModel> sqliteFieldList = installMapper.getSqliteFieldList(StrUtil.format("PRAGMA table_info(`{}`)",tableModel.getName()));
            for (TableFieldModel tableFieldModel : sqliteFieldList) {
                tableFieldModel.setAutoIncrement(tableFieldModel.isPrimary() && tableFieldModel.getType().equals("integer"));
                tableFieldModel.setEmpty(!tableFieldModel.isEmpty());
                tableFieldModel.setComment("");
                tableFieldModel.setLength(0);
            }
            tableModel.setFieldList(sqliteFieldList);
        }
        return tableModels;
    }

    /**
     * 初始化mysql数据库
     */
    private void initMysqlDataBase(InstallForm installForm) throws Exception{
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
        setting.setProperty("type","mysql");
        setting.setProperty("installStatus","dbSuccess");
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
        setting.setProperty("type","sqlite");
        setting.setProperty("installStatus","dbSuccess");
        setting.store(file.getAbsolutePath());
    }

    /**
     * 安装后的初始化操作
     */
    private void installInitOperate(InstallForm installForm) throws Exception {
        Option option = optionService.getByKey(OptionEnum.IS_INSTALLED.getValue());
        if (OptionUtils.valToBool(option)) {
            initUser(installForm);
            return;
        }
        initData(installForm);
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

    private void initData(InstallForm installForm) {
        Role adminRole = new Role(RoleEnum.ADMIN_ROLE.getName(),RoleEnum.ADMIN_ROLE.getDesc(), RoleEnum.ADMIN_ROLE.getCode());
        Role userRole = new Role(RoleEnum.USER_ROLE.getName(), RoleEnum.USER_ROLE.getDesc(),RoleEnum.USER_ROLE.getCode());
        Role editorRole = new Role(RoleEnum.EDITOR_ROLE.getName(),RoleEnum.EDITOR_ROLE.getDesc(),RoleEnum.EDITOR_ROLE.getCode());
        Role contributeRole = new Role(RoleEnum.CONTRIBUTE_ROLE.getName(), RoleEnum.CONTRIBUTE_ROLE.getDesc(),RoleEnum.CONTRIBUTE_ROLE.getCode());
        roleService.save(adminRole);
        roleService.save(userRole);
        roleService.save(editorRole);
        roleService.save(contributeRole);

        Option installOption = new Option(OptionEnum.IS_INSTALLED.getValue(), "true");
        optionService.save(installOption);
/*

        Option themeOption = new Option("WEB_THEME", "default");
        Option registerOption = new Option("WEB_IS_REGISTER", "1");
        Option reviewOption = new Option("WEB_COMMENT_IS_REVIEW", "0");
*/
        initUser(installForm);
    }

    private void initUser(InstallForm installForm) {
        Role role = roleService.getByCode(RoleEnum.ADMIN_ROLE.getCode());
        installForm.setRoleId(role.getId());
        installForm.setAvatar(GravatarUtil.getGravatar(installForm.getEmail()));
        installForm.setSalt(StringUtil.getUUID());
        installForm.setPassword(new Md5Hash(installForm.getPassword(), installForm.getSalt()).toString());
        userService.save(installForm);
    }
}
