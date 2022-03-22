package com.perfree.mapper;

import com.perfree.dataBase.TableFieldModel;
import com.perfree.dataBase.TableModel;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public interface InstallMapper {
    List<TableModel> queryMysqlTables();

    List<TableModel> querySqliteTables();

    List<TableFieldModel> getSqliteFieldList(String sql);
}
