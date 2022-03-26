package com.perfree.controller;

import cn.hutool.core.codec.Base64;
import com.perfree.commons.JwtToken;
import com.perfree.commons.JwtUtil;
import com.perfree.commons.ResponseBean;
import com.perfree.model.User;
import com.perfree.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.crypto.hash.Md5Hash;
import org.apache.shiro.subject.Subject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.NotBlank;
import java.util.HashMap;

@CrossOrigin
@RestController
@Api(value = "系统相关",tags = "系统相关")
public class SystemController{
    private final static Logger LOGGER = LoggerFactory.getLogger(SystemController.class);

    @Autowired
    private UserService userService;

    @GetMapping(path = "/401/{message}")
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    @ResponseBody
    public ResponseBean unauthorized(@PathVariable String message) {
        LOGGER.error("401:{}", message);
        if (StringUtils.isNotBlank(message)){
            return ResponseBean.fail(401, message, null);
        }
        return ResponseBean.fail(401, "Unauthorized", null);
    }

    @PostMapping("/login")
    @ApiOperation(value = "登录", notes = "登录")
    public ResponseBean login(@NotBlank(message = "账户不允许为空") @RequestParam("account") String account,
                              @NotBlank(message = "密码不允许为空") @RequestParam("password") String password) {
        User user = userService.getLoginInfo(account);
        if (user == null) {
            return ResponseBean.fail("账户不存在", null);
        }
        password = new Md5Hash(password, user.getSalt()).toString();
        if (!user.getPassword().equals(password)) {
            return ResponseBean.fail("密码错误", null);
        }
        String token = JwtUtil.sign(user.getAccount(), Base64.encode(password));
        Subject subject = SecurityUtils.getSubject();
        subject.login(new JwtToken(token));
        user.setPassword(null);
        user.setSalt(null);
        HashMap<String, Object> result = new HashMap<>();
        result.put("user", user);
        result.put("token", token);
        return ResponseBean.success("success", result);
    }
}
