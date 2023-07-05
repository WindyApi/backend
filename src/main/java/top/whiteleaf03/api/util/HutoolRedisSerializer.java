package top.whiteleaf03.api.util;

import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONUtil;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

/**
 * @author WhiteLeaf03
 */
public class HutoolRedisSerializer implements RedisSerializer<Object> {
    private final StringRedisSerializer stringRedisSerializer = new StringRedisSerializer();

    @Override
    public byte[] serialize(Object o) {
        if (ObjectUtil.isEmpty(o)) {
            return new byte[0];
        }

        if (o instanceof String) {
            return stringRedisSerializer.serialize((String) o);
        }

        return JSONUtil.toJsonStr(o).getBytes();
    }

    @Override
    public Object deserialize(byte[] bytes) {
        if (bytes == null || bytes.length == 0) {
            return null;
        }

        String str = stringRedisSerializer.deserialize(bytes);
        if (StrUtil.isBlank(str)) {
            return null;
        }

        return JSONUtil.parse(str);
    }
}
