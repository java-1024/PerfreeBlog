package com.perfree.config;

import com.perfree.commons.ResponseBean;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.converter.HttpMessageConversionException;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.List;
import java.util.Objects;

/**
 * @author Perfree
 * 全局异常拦截处理/参数校验异常拦截处理
 */
@RestControllerAdvice
public class ValidExceptionHandler {
    private final static Logger LOGGER = LoggerFactory.getLogger(ValidExceptionHandler.class);

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseBean validationBodyException(MethodArgumentNotValidException exception) {
        exception.printStackTrace();
        BindingResult result = exception.getBindingResult();
        Objects.requireNonNull(result.getFieldError());
        LOGGER.error(result.getFieldError().getDefaultMessage());
        return ResponseBean.fail(result.getFieldError().getDefaultMessage());
    }

    @ExceptionHandler(HttpMessageConversionException.class)
    public ResponseBean parameterTypeException(HttpMessageConversionException exception) {
        exception.printStackTrace();
        LOGGER.error(exception.getMessage());
        return ResponseBean.fail("ClassCastException", exception.getMessage());

    }

    @ExceptionHandler(BindException.class)
    public ResponseBean handleBindException(Exception exception) {
        exception.printStackTrace();
        List<ObjectError> list = ((BindException) exception).getAllErrors();
        if (list.size() > 0) {
            LOGGER.error(list.get(0).getDefaultMessage());
            return ResponseBean.fail(list.get(0).getDefaultMessage());
        }
        return ResponseBean.fail("参数错误", null);
    }

    @ExceptionHandler({Exception.class})
    public ResponseBean exceptionHandler(Exception exception) {
        exception.printStackTrace();
        LOGGER.error(exception.getMessage(), exception);
        return ResponseBean.fail(exception.getMessage());
    }
}
