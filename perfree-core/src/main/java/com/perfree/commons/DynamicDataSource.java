package com.perfree.commons;

import com.alibaba.druid.pool.DruidDataSource;
import com.perfree.enums.SystemEnum;
import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;

import javax.sql.DataSource;

public class DynamicDataSource extends AbstractRoutingDataSource {
    /** 当前数据源 */
    public static DruidDataSource dataSource = new DruidDataSource();

    /** 当前数据源类型sqlite/mysql */
    public static String dataSourceType = SystemEnum.DB_TYPE_MYSQL.getValue();

    public static boolean dataSourceIsInit = false;

    /**
     * 设置数据源
     * @param dataSource 数据源
     */
    public static void setDataSource(DruidDataSource dataSource,String dataSourceType) {
        DynamicDataSource.dataSource = dataSource;
        DynamicDataSource.dataSourceType = dataSourceType;
        DynamicDataSource.dataSourceIsInit = true;
    }

    /**
     * 获取数据源
     * @return DataSource
     */
    public static DruidDataSource getDataSource() {
        return dataSource;
    }

    @Override
    protected Object determineCurrentLookupKey() {
        return null;
    }

    @Override
    protected DataSource determineTargetDataSource() {
        return getDataSource();
    }
}
