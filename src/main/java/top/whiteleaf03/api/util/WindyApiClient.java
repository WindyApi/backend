package top.whiteleaf03.api.util;

import cn.hutool.core.map.MapUtil;
import cn.hutool.crypto.digest.DigestUtil;
import cn.hutool.http.HttpRequest;
import cn.hutool.json.JSONUtil;
import top.whiteleaf03.api.modal.dto.AccessKeyAndSecretKeyDTO;

import java.util.HashMap;
import java.util.Map;

/**
 * @author WhiteLeaf03
 */
public class WindyApiClient {
    private final String gatewayHost;
    private final String accessKey;
    private final String secretKey;

    public WindyApiClient(String gatewayHost, String accessKey, String secretKey) {
        this.gatewayHost = gatewayHost;
        this.accessKey = accessKey;
        this.secretKey = secretKey;
    }

    public WindyApiClient(String gatewayHost, AccessKeyAndSecretKeyDTO accessKeyAndSecretKeyDTO) {
        this.gatewayHost = gatewayHost;
        this.accessKey = accessKeyAndSecretKeyDTO.getAccessKey();
        this.secretKey = accessKeyAndSecretKeyDTO.getSecretKey();
    }

    private String genSign(String params, String timestamp) {
        String sourceStr = timestamp + "." + params + "." + secretKey;
        return DigestUtil.sha256Hex(sourceStr);
    }

    public String invokeApiByGet(HashMap<String, Object> params, String url) {
        //时间戳
        String timestamp = String.valueOf(System.currentTimeMillis());

        //签名
        String sign = genSign(JSONUtil.toJsonStr(params), timestamp);

        //请求头
        Map<String, String> headers = new HashMap<String, String>(3) {{
            put("accessKey", accessKey);
            put("timestamp", timestamp);
            put("sign", sign);
            put("params", JSONUtil.toJsonStr(params));
        }};
        String paramStr = MapUtil.join(params, "&", "=");
        String result = HttpRequest.get(gatewayHost + url + paramStr)
                .addHeaders(headers)
                .execute().body();
        return result;
    }

    public String invokeApiByPost(HashMap<String, Object> body, String url) {
        //时间戳
        String timestamp = String.valueOf(System.currentTimeMillis());

        //请求体
        String bodyJson = JSONUtil.toJsonStr(body);

        //签名
        String sign = genSign(bodyJson, timestamp);

        //请求头
        Map<String, String> headers = new HashMap<String, String>(3) {{
            put("accessKey", accessKey);
            put("timestamp", timestamp);
            put("sign", sign);
            put("body", bodyJson);
        }};

        String result = HttpRequest.post(gatewayHost + url)
                .addHeaders(headers)
                .body(bodyJson)
                .execute().body();
        return result;
    }
}
