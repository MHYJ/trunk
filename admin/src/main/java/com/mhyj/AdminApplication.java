package com.mhyj;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.core.env.Environment;

import javax.annotation.Resource;
import java.net.InetAddress;
import java.util.Arrays;

@Slf4j
@SpringBootApplication
@ComponentScan(basePackages = {"com.mhyj"})
public class AdminApplication {

    @Resource
    private Environment environment;

    public static void main(String[] args) {
        SpringApplication.run(AdminApplication.class, args);
    }

    @Bean
    CommandLineRunner init() {
        return (args) -> {
            log.info("=========================================================");
            log.info("Active profiles: {}", Arrays.asList(environment.getActiveProfiles()).toString());
            log.info("Swagger2 docs：http://{}:{}/docs.html",
                    InetAddress.getLocalHost().getHostAddress(), environment.getProperty("server.port"));
            log.info("Druid：http://{}:{}/druid",
                    InetAddress.getLocalHost().getHostAddress(), environment.getProperty("server.port"));
            log.info("=========================================================");
        };
    }

}
