package top.whiteleaf03.api.util;

import cn.hutool.jwt.JWTUtil;

import java.util.HashMap;

/**
 * @author WhiteLeaf03
 */
public class TokenUtils {
    private final static byte[] SECRET_KEY = "09FCBD296B8872550636A92A512C39CB".getBytes();

    public static String createToken(String uid) {
        HashMap<String, Object> payload = new HashMap<>(1);
        payload.put("uid", uid);
        return JWTUtil.createToken(payload, SECRET_KEY);
    }

    public static String parseToken(String token) {
        return JWTUtil.parseToken(token).getPayload("uid").toString();
    }
}
