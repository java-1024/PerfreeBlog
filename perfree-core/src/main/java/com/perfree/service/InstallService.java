package com.perfree.service;

import com.perfree.dataBase.TableModel;
import com.perfree.form.InstallForm;

import java.util.List;

public interface InstallService {
    void install(InstallForm installForm) throws Exception;
    List<TableModel> queryMysqlTables();
    List<TableModel> querySqliteTables();
}
