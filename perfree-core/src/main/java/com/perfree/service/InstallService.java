package com.perfree.service;

import com.perfree.form.InstallDataBaseForm;
import com.perfree.form.InstallUserForm;

public interface InstallService {
    void install(InstallDataBaseForm installDataBaseForm) throws Exception;

    /**
     * 检测数据库是否存在
     * @param installDataBaseForm installDataBaseForm
     * @return boolean
     * @throws Exception e
     */
    boolean checkDataBase(InstallDataBaseForm installDataBaseForm) throws Exception;

    /**
     * 初始化用户信息
     * @param installUserForm installUserForm
     */
    void initUser(InstallUserForm installUserForm);
}
