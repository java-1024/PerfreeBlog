package com.perfree.dataBase;

import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.ReflectUtil;
import cn.hutool.core.util.StrUtil;
import com.perfree.commons.DynamicDataSource;
import com.perfree.commons.SpringBeanUtils;
import com.perfree.enums.SystemEnum;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Field;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class DataBaseUtils {

    private final static Logger LOGGER = LoggerFactory.getLogger(DataBaseUtils.class);

    /**
     * 获取扫描到的实体类对应的表及字段
     * @return List<TableModel>
     */
    public static List<TableModel> getTables(String type) {
        List<TableModel> tableModelList = new ArrayList<>();
        Map<String, Object> beans = SpringBeanUtils.getApplicationContext().getBeansWithAnnotation(DataTable.class);
        for (Map.Entry<String, Object> entry : beans.entrySet()) {
            Object bean = entry.getValue();
            DataTable table = bean.getClass().getAnnotation(DataTable.class);
            if (table == null) {
                continue;
            }
            TableModel tableModel = new TableModel();
            tableModel.setName(table.value());
            // 联合索引
            List<String> uniqueConstraintsList = new ArrayList<>();
            for (UniqueConstraints uniqueConstraints : table.uniqueConstraints()) {
                uniqueConstraintsList.add(Arrays.stream(uniqueConstraints.value()).map(uni -> "`"+uni+"`").map(String::valueOf).collect(Collectors.joining(",")));
            }
            tableModel.setUniqueConstraintsList(uniqueConstraintsList);

            // 唯一索引
            List<String> indexList = new ArrayList<>();
            for (Index index : table.index()) {
                indexList.add(index.value());
            }
            tableModel.setIndexList(indexList);

            Field[] fields = ReflectUtil.getFields(bean.getClass());
            List<TableFieldModel> tableFieldModelList = new ArrayList<>();
            for (Field field : fields) {
                DataTableField tableField = field.getAnnotation(DataTableField.class);
                if (tableField != null) {
                    tableFieldModelList.add(genFields(tableField, type));
                }
            }
            tableModel.setFieldList(tableFieldModelList);
            tableModelList.add(tableModel);
        }
        return tableModelList;
    }

    private static TableFieldModel genFields(DataTableField tableField, String type) {
        TableFieldModel tableFieldModel = new TableFieldModel();
        tableFieldModel.setAutoIncrement(tableField.autoIncrement());
        tableFieldModel.setDefaultValue(tableField.defaultValue());
        tableFieldModel.setEmpty(tableField.isEmpty());
        tableFieldModel.setName(tableField.name());
        tableFieldModel.setPrimary(tableField.isPrimary());
        if (type.equals(SystemEnum.DB_TYPE_SQLITE.getValue())) {
            tableFieldModel.setType(tableField.type());
            if (tableFieldModel.getType().equals("varchar") || tableFieldModel.getType().equals("longtext") ){
                tableFieldModel.setType("text");
            }
            if (tableFieldModel.getType().equals("int") || tableFieldModel.getType().equals("bigint") ){
                tableFieldModel.setType("integer");
            }
            tableFieldModel.setType(StrUtil.format("{}{}", tableFieldModel.getType(), tableField.length() > 0 ? StrUtil.format("({})", tableField.length()) : ""));
            tableFieldModel.setComment("");
            tableFieldModel.setLength(0);
        } else {
            tableFieldModel.setType(tableField.type());
            tableFieldModel.setLength(tableField.length());
            tableFieldModel.setComment(tableField.comment());
        }
        return tableFieldModel;
    }

    /**
     * 初始化mysql数据库
     */
    public static void initOrUpdateMysqlDataBase(List<TableModel> tableModelList) throws Exception {
        List<TableModel> tables = getTables(SystemEnum.DB_TYPE_MYSQL.getValue());
        // 要新增的表
        List<TableModel> tablesListCollect = tables.stream().filter(item -> !tableModelList.stream().map(TableModel::getName)
                .collect(Collectors.toList()).contains(item.getName())).collect(Collectors.toList());
        createMysqlTables(tablesListCollect);
        tables.removeAll(tablesListCollect);

        // 字段校对
        for (TableModel table : tables) {
            TableModel collect = tableModelList.stream().filter(item -> item.getName().equals(table.getName())).collect(Collectors.toList()).get(0);
            // 要新增的字段
            List<TableFieldModel> addFields = table.getFieldList().stream().filter(item -> !collect.getFieldList().stream().map(TableFieldModel::getName)
                    .collect(Collectors.toList()).contains(item.getName())).collect(Collectors.toList());

            table.getFieldList().removeAll(addFields);
            // 要更新的字段
            List<TableFieldModel> updateFields = table.getFieldList().stream().filter(item ->
                            !ObjectUtil.equal(collect.getFieldList().stream().filter(collectItem -> collectItem.getName().equals(item.getName())).collect(Collectors.toList()).get(0), item)
                        ).collect(Collectors.toList());
            // 执行更新操作
            updateMysqlTable(table.getName(),addFields, true);
            updateMysqlTable(table.getName(),updateFields, false);
        }
        LOGGER.info("----------数据库初始化完成------------------");
    }


    public static void initOrUpdateSqliteDataBase(List<TableModel> tableModelList) throws Exception {
        List<TableModel> tables = getTables(SystemEnum.DB_TYPE_SQLITE.getValue());
        // 要新增的表
        List<TableModel> tablesListCollect = tables.stream().filter(item -> !tableModelList.stream().map(TableModel::getName)
                .collect(Collectors.toList()).contains(item.getName())).collect(Collectors.toList());
        createSqliteTables(tablesListCollect);
        tables.removeAll(tablesListCollect);
        // 字段校对
        for (TableModel table : tables) {
            TableModel collect = tableModelList.stream().filter(item -> item.getName().equals(table.getName())).collect(Collectors.toList()).get(0);
            // 要新增的字段
            List<TableFieldModel> addFields = table.getFieldList().stream().filter(item -> !collect.getFieldList().stream().map(TableFieldModel::getName)
                    .collect(Collectors.toList()).contains(item.getName())).collect(Collectors.toList());
            updateSqliteTable(table.getName(), addFields);
            // sqlite更新字段暂不处理
        }
        LOGGER.info("----------数据库初始化完成------------------");
    }

    /**
     * 更新sqlite表
     */
    public static void updateSqliteTable(String tableName, List<TableFieldModel> fields) throws Exception{
        try (Connection connection = DynamicDataSource.getDataSource().getConnection()) {
            for (TableFieldModel field : fields) {
                StringBuilder addSql = new StringBuilder();
                addSql.append(StrUtil.format("ALTER TABLE `{}` ADD COLUMN `{}` ", tableName, field.getName()));
                addSql.append(StrUtil.format("{}{} ", field.getType(), field.getLength() > 0 ? StrUtil.format("({})", field.getLength()) : ""));
                addSql.append(field.isEmpty() ? "NULL " : "NOT NULL ");
                addSql.append(StrUtil.isEmpty(field.getDefaultValue()) ? "" : StrUtil.format("DEFAULT {} ", field.getDefaultValue()));
                LOGGER.info("数据库操作:{}", addSql);
                connection.prepareStatement(addSql.toString()).execute();
            }
        }catch (Exception e) {
            e.printStackTrace();
            LOGGER.error("数据库操作-更新{}表出错:{}", tableName, e.getMessage());
            throw new Exception(e.getMessage());
        }
    }

    /**
     * 更新mysql表
     */
    public static void updateMysqlTable(String tableName, List<TableFieldModel> fields, boolean isAdd) throws Exception{
        try (Connection connection = DynamicDataSource.getDataSource().getConnection()) {
            for (TableFieldModel field : fields) {
                StringBuilder addSql = new StringBuilder();
                addSql.append(StrUtil.format("ALTER TABLE `{}` {} COLUMN `{}` ", tableName,(isAdd ? "ADD" : "MODIFY"), field.getName()));
                addSql.append(StrUtil.format("{}{} ", field.getType(), field.getLength() > 0 ? StrUtil.format("({})", field.getLength()) : ""));
                addSql.append(field.isEmpty() ? "NULL " : "NOT NULL ");
                addSql.append(StrUtil.isEmpty(field.getDefaultValue()) ? "" : StrUtil.format("DEFAULT {} ", field.getDefaultValue()));
                addSql.append(StrUtil.isEmpty(field.getComment()) ? "" : StrUtil.format("COMMENT '{}'", field.getComment()));
                LOGGER.info("数据库操作:{}", addSql);
                connection.prepareStatement(addSql.toString()).execute();
            }
        }catch (Exception e) {
            e.printStackTrace();
            LOGGER.error("数据库操作-更新{}表出错:{}", tableName, e.getMessage());
            throw new Exception(e.getMessage());
        }
    }

    /**
     * 创建mysql表
     */
    public static void createMysqlTables(List<TableModel> tables) throws Exception {
        try (Connection connection = DynamicDataSource.getDataSource().getConnection()) {
            for (TableModel table : tables) {
                String createTableSqlTpl = "CREATE TABLE `{}`  ({}) ENGINE = InnoDB CHARACTER SET = utf8mb4 ROW_FORMAT = Dynamic;";
                StringBuilder fieldSql = new StringBuilder();
                StringBuilder primarySql = new StringBuilder();
                for (TableFieldModel tableFieldModel : table.getFieldList()) {
                    // 字段名
                    fieldSql.append(StrUtil.format("`{}` ", tableFieldModel.getName()));
                    // 字段类型/长度
                    fieldSql.append(StrUtil.format("{}{} ", tableFieldModel.getType(),
                            tableFieldModel.getLength() > 0 ? StrUtil.format("({})", tableFieldModel.getLength()) : ""));
                    // 字段是否允许为空
                    fieldSql.append(tableFieldModel.isEmpty() ? "NULL " : "NOT NULL ");
                    // 字段默认值
                    fieldSql.append(StrUtil.isEmpty(tableFieldModel.getDefaultValue()) ? "" : StrUtil.format("DEFAULT {} ", tableFieldModel.getDefaultValue()));
                    // 是否自增
                    if (tableFieldModel.isAutoIncrement()) {
                        fieldSql.append("AUTO_INCREMENT");
                    }
                    // 字段注释
                    fieldSql.append(StrUtil.isEmpty(tableFieldModel.getComment()) ? "" : StrUtil.format("COMMENT '{}'", tableFieldModel.getComment()));
                    // 字段是否为主键
                    if (tableFieldModel.isPrimary()) {
                        primarySql.append(StrUtil.format("PRIMARY KEY (`{}`) USING BTREE,", tableFieldModel.getName()));
                    }
                    fieldSql.append(",");
                }
                fieldSql.append(primarySql);
                String createTableSql = StrUtil.format(createTableSqlTpl, table.getName(), StrUtil.removeSuffix(fieldSql.toString(), ","));
                LOGGER.info("数据库操作: {}", createTableSql);
                connection.prepareStatement(createTableSql).execute();

                for (String index : table.getIndexList()) {
                    String sql = StrUtil.format("ALTER TABLE `{}` ADD INDEX `{}`(`{}`)", table.getName(), index, index);
                    LOGGER.info("数据库操作: {}", sql);
                    connection.prepareStatement(sql).execute();
                }

                for (String uniqueConstraints : table.getUniqueConstraintsList()) {
                    String sql = StrUtil.format("ALTER TABLE `{}` ADD INDEX `{}`({})", table.getName(),
                            uniqueConstraints.replaceAll(",", "_").replaceAll("`", ""), uniqueConstraints);
                    LOGGER.info("数据库操作: {}", sql);
                    connection.prepareStatement(sql).execute();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            LOGGER.error("数据库操作: 创建数据库表出错:{}", e.getMessage());
            throw new Exception(e.getMessage());
        }

    }

    /**
     * 初始化sqlite数据库
     */
    public static void createSqliteTables(List<TableModel> tables) throws Exception {
        try (Connection connection = DynamicDataSource.getDataSource().getConnection()) {
            for (TableModel table : tables) {
                String createTableSqlTpl = "CREATE TABLE `{}` ({});";
                StringBuilder fieldSql = new StringBuilder();
                for (TableFieldModel tableFieldModel : table.getFieldList()) {
                    // 字段名
                    fieldSql.append(StrUtil.format("`{}` ", tableFieldModel.getName()));
                    // 字段类型/长度
                    fieldSql.append(tableFieldModel.getType());
                    fieldSql.append(" ");
                    // 字段是否允许为空
                    fieldSql.append(tableFieldModel.isEmpty() ? "": "NOT NULL ");
                    // 默认值
                    fieldSql.append(StrUtil.isEmpty(tableFieldModel.getDefaultValue()) ? "" : StrUtil.format("DEFAULT {} ", tableFieldModel.getDefaultValue()));
                    // 是否为主键
                    if (tableFieldModel.isPrimary()) {
                        fieldSql.append("PRIMARY KEY ");
                    }
                    // 是否自增
                    if (tableFieldModel.isAutoIncrement()) {
                        fieldSql.append("AUTOINCREMENT");
                    }
                    fieldSql.append(",");
                }
                String createTableSql = StrUtil.format(createTableSqlTpl, table.getName(),  StrUtil.removeSuffix(fieldSql.toString(), ","));
                LOGGER.info("数据库操作: {}", createTableSql);
                connection.prepareStatement(createTableSql).execute();

                for (String index : table.getIndexList()) {
                    String sql = StrUtil.format("CREATE INDEX {} ON {} ({})", index, table.getName(), index);
                    LOGGER.info("数据库操作: {}", sql);
                    connection.prepareStatement(sql).execute();
                }

                for (String uniqueConstraints : table.getUniqueConstraintsList()) {
                    String sql = StrUtil.format("CREATE INDEX {} ON {} ({})", uniqueConstraints.replaceAll(",", "_").replaceAll("`", ""),
                            table.getName(), uniqueConstraints);
                    LOGGER.info("数据库操作: {}", sql);
                    connection.prepareStatement(sql).execute();
                }
            }
        }catch (Exception e) {
            e.printStackTrace();
            LOGGER.error("数据库操作: 创建数据库表出错:{}", e.getMessage());
            throw new Exception(e.getMessage());
        }
    }
}
