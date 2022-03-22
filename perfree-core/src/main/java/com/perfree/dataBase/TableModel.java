package com.perfree.dataBase;

import java.util.List;

public class TableModel {
    private String name;

    private List<String> indexList;

    private List<String> uniqueConstraintsList;

    private List<TableFieldModel> fieldList;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<TableFieldModel> getFieldList() {
        return fieldList;
    }

    public void setFieldList(List<TableFieldModel> fieldList) {
        this.fieldList = fieldList;
    }

    public List<String> getIndexList() {
        return indexList;
    }

    public void setIndexList(List<String> indexList) {
        this.indexList = indexList;
    }

    public List<String> getUniqueConstraintsList() {
        return uniqueConstraintsList;
    }

    public void setUniqueConstraintsList(List<String> uniqueConstraintsList) {
        this.uniqueConstraintsList = uniqueConstraintsList;
    }
}
