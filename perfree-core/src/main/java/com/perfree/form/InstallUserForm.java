package com.perfree.form;

import com.perfree.model.User;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import java.io.Serializable;

public class InstallUserForm extends User implements Serializable {
    private static final long serialVersionUID = 1L;

    @NotBlank(message = "账户不允许为空")
    @Pattern(regexp ="^[A-Za-z0-9]+$",message = "账户只能填写字母或数字")
    @Length(min = 3,max = 12,message = "账户长度要在3-12字符之间")
    private String account;

    @NotBlank(message = "用户名不允许为空")
    @Length(min = 3,max = 16,message = "用户名长度要在3-16字符之间")
    private String userName;

    @NotBlank(message = "密码不允许为空")
    @Length(min = 3,max = 16,message = "密码长度要在3-16字符之间")
    private String password;

    @NotBlank(message = "邮箱不允许为空")
    @Email(message = "请输入正确的邮箱")
    private String email;

    @Override
    public String getEmail() {
        return email;
    }

    @Override
    public void setEmail(String email) {
        this.email = email;
    }

    @Override
    public String getAccount() {
        return account;
    }

    @Override
    public void setAccount(String account) {
        this.account = account;
    }

    @Override
    public String getUserName() {
        return userName;
    }

    @Override
    public void setUserName(String userName) {
        this.userName = userName;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public void setPassword(String password) {
        this.password = password;
    }
}
