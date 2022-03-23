package com.perfree.commons;

import cn.hutool.core.util.StrUtil;
import com.perfree.model.Option;

public class OptionUtils {

    /**
     * to bool
     */
    public static boolean valToBool(Option option){
        if (option == null || StrUtil.isBlank(option.getValue())) {
            return false;
        }
        return Boolean.parseBoolean(option.getValue());
    }
}
