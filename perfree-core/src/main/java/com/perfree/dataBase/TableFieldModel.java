package com.perfree.dataBase;

public class TableFieldModel {
    private String name;
    private String type;
    private int length;
    private String defaultValue = "";
    private boolean isEmpty;
    private boolean isPrimary;
    private boolean autoIncrement;
    private String comment = "";


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public int getLength() {
        return length;
    }

    public void setLength(int length) {
        this.length = length;
    }

    public String getDefaultValue() {
        return defaultValue;
    }

    public void setDefaultValue(String defaultValue) {
        this.defaultValue = defaultValue;
    }

    public boolean isEmpty() {
        return isEmpty;
    }

    public void setEmpty(boolean empty) {
        isEmpty = empty;
    }

    public boolean isPrimary() {
        return isPrimary;
    }

    public void setPrimary(boolean primary) {
        isPrimary = primary;
    }

    public boolean isAutoIncrement() {
        return autoIncrement;
    }

    public void setAutoIncrement(boolean autoIncrement) {
        this.autoIncrement = autoIncrement;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    @Override
    public boolean equals(Object obj) {
        if(obj==null){
            return false;
        }
        if(this==obj){
            return true;
        }
        if(obj instanceof TableFieldModel){
            TableFieldModel compareTableFieldModel = (TableFieldModel)obj;
            return compareTableFieldModel.getName().equals(this.name) && compareTableFieldModel.getComment().equals(this.comment)
                    && compareTableFieldModel.getType().equals(this.type) && compareTableFieldModel.getLength() == this.length
                    && compareTableFieldModel.getDefaultValue().equals(this.defaultValue) && compareTableFieldModel.isPrimary() == this.isPrimary
                    && compareTableFieldModel.isEmpty() == this.isEmpty && compareTableFieldModel.isAutoIncrement() == this.autoIncrement;
        }
        return false;
    }
}
