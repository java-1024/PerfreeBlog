package com.perfree.mapper;

import org.springframework.stereotype.Component;

import java.util.List;

@Component
public interface InstallMapper {
    List<String> queryMysqlTables();

    List<String> querySqliteTables();
}
