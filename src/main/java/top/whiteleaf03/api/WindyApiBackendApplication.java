package top.whiteleaf03.api;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import top.whiteleaf03.api.config.BeanNameConfig;

/**
 * @author WhiteLeaf03
 */
@SpringBootApplication
@ComponentScan(nameGenerator = BeanNameConfig.class)
public class WindyApiBackendApplication {
    public static void main(String[] args) {
        SpringApplication.run(WindyApiBackendApplication.class, args);
    }
}