package com.perfree.form;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;

public class InstallDataBaseForm implements Serializable {
    private static final long serialVersionUID = 1L;
    // 安装类型,检查/跳过/覆盖/正常
    public static int INSTALL_SKIP = 1;
    public static int INSTALL_COVER = 2;
    public static int INSTALL_NORMAL = 3;

    @NotBlank(message = "数据库类型不允许为空")
    private String dataBaseType;
    private String dataBaseAddress;
    private String dataBasePort;
    private String dataBaseName;
    private String dataBaseUserName;
    private String dataBasePassword;
    private Integer installType;

    public String getDataBaseType() {
        return dataBaseType;
    }

    public void setDataBaseType(String dataBaseType) {
        this.dataBaseType = dataBaseType;
    }

    public String getDataBaseAddress() {
        return dataBaseAddress;
    }

    public void setDataBaseAddress(String dataBaseAddress) {
        this.dataBaseAddress = dataBaseAddress;
    }

    public String getDataBasePort() {
        return dataBasePort;
    }

    public void setDataBasePort(String dataBasePort) {
        this.dataBasePort = dataBasePort;
    }

    public String getDataBaseName() {
        return dataBaseName;
    }

    public void setDataBaseName(String dataBaseName) {
        this.dataBaseName = dataBaseName;
    }

    public String getDataBaseUserName() {
        return dataBaseUserName;
    }

    public void setDataBaseUserName(String dataBaseUserName) {
        this.dataBaseUserName = dataBaseUserName;
    }

    public String getDataBasePassword() {
        return dataBasePassword;
    }

    public void setDataBasePassword(String dataBasePassword) {
        this.dataBasePassword = dataBasePassword;
    }

    public Integer getInstallType() {
        return installType;
    }

    public void setInstallType(Integer installType) {
        this.installType = installType;
    }
}
