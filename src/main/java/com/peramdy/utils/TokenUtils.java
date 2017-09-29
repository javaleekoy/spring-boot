package com.peramdy.utils;

import java.util.Date;
import java.util.UUID;

/**
 * Created by peramdy on 2017/9/28.
 */
public class TokenUtils {

    public static String createLoginToken(Integer userId, String password, Date time) {
        Long second = time.getTime() / 1000;
        SaltUtils saltUtils = new SaltUtils(second, "MD5");
        String token = saltUtils.encode(userId + password);
        return token;
    }


    public static String createUUID() {
        String uuid = UUID.randomUUID().toString();
        return uuid;
    }

}
