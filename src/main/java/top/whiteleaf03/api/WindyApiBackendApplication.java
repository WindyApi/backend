package top.whiteleaf03.api;

import org.apache.dubbo.config.spring.context.annotation.EnableDubbo;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

/**
 * @author WhiteLeaf03
 */
@EnableDubbo
@EnableScheduling
@SpringBootApplication
public class WindyApiBackendApplication {
    public static void main(String[] args) {
        SpringApplication.run(WindyApiBackendApplication.class, args);
    }
}