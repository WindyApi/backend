package top.whiteleaf03.api.config;

import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author WhiteLeaf03
 */
@Configuration
public class RabbitConfig {
    @Bean
    public Queue queue() {
        return new Queue("email");
    }
}
