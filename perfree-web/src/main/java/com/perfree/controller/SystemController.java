package com.perfree.controller;

import com.perfree.commons.ResponseBean;
import io.swagger.annotations.Api;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@CrossOrigin
@RestController
@Api(value = "系统相关",tags = "系统相关")
public class SystemController{
    private final static Logger LOGGER = LoggerFactory.getLogger(SystemController.class);

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
}
