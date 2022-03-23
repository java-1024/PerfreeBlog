package com.perfree.commons;

public class GravatarUtil {
    /**
     * 获取Gravatar头像地址
     * @param email 邮箱
     * @return String linkAddr
     */
    public static String getGravatar(String email) {
        return StringUtil.strToMd5(email);
    }
}
