package com.perfree.interceptor;

import com.perfree.commons.DynamicDataSource;
import com.perfree.commons.OptionUtils;
import com.perfree.commons.SpringBeanUtils;
import com.perfree.enums.OptionEnum;
import com.perfree.service.OptionService;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class DataSourceInterceptor implements HandlerInterceptor {
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        OptionService optionService = SpringBeanUtils.getBean(OptionService.class);
        return DynamicDataSource.dataSourceIsInit && OptionUtils.valToBool(optionService.getByKey(OptionEnum.IS_INSTALLED.getValue()));
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
    }
}
