package com.cdfive.mp3;

import lombok.extern.slf4j.Slf4j;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ImportResource;

/**
 * @author cdfive
 */
@Slf4j
@ImportResource("classpath:/config/applicationContext.xml")
@MapperScan("com.cdfive.mp3.mapper")
@SpringBootApplication(scanBasePackages = {"com.cdfive"})
public class Application {
    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
        log.info("cdfive-mp3 application started");
    }
}
