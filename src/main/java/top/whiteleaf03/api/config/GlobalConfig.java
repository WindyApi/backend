package top.whiteleaf03.api.config;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.env.YamlPropertySourceLoader;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.io.support.EncodedResource;
import org.springframework.core.io.support.PropertySourceFactory;

import java.io.IOException;

/**
 * @author WhiteLeaf03
 */
@Configuration
@Data
@PropertySource(value = "classpath:config.yml", factory = PropertySourceFactoryImpl.class)
public class GlobalConfig {
    @Value("${key.salt}")
    private String salt;

    @Value("${gateway.host}")
    private String gatewayHost;
}

class PropertySourceFactoryImpl implements PropertySourceFactory {
    @Override
    public org.springframework.core.env.PropertySource<?> createPropertySource(String name, EncodedResource resource) throws IOException {
        return new YamlPropertySourceLoader().load(name, resource.getResource()).get(0);
    }
}