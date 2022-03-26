package com.perfree.commons;

import com.perfree.enums.OptionEnum;
import org.apache.commons.lang3.StringUtils;

public class GravatarUtil {
    /**
     * 获取Gravatar头像地址
     * @param email 邮箱
     * @return String linkAddr
     */
    public static String getGravatar(String email) {
        return StringUtil.strToMd5(email);
    }

    /**
     * 替换Gravatar头像地址
     * @param avatar linkAddr
     */
    public static String replaceGravatar(String avatar) {
        if (StringUtils.isBlank(avatar) || avatar.startsWith("/static/avatar") || avatar.startsWith("/avatar")
                || avatar.startsWith("http")) {
            return avatar;
        }
        String gravatarUrl = OptionCacheUtil.getValue(OptionEnum.GRAVATAR_SOURCE.getValue());
        if (StringUtils.isBlank(gravatarUrl)) {
            gravatarUrl = "//gravatar.loli.net/avatar/";
        }
        if (StringUtils.isNotBlank(avatar) && avatar.contains("http://www.gravatar.com/avatar/")) {
            avatar = avatar.replace("http://www.gravatar.com/avatar/", "").replace("?s=32", "");
        }
        return gravatarUrl + avatar;
    }
}
