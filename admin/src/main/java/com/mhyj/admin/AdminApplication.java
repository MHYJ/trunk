package com.mhyj.admin;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Profile;
import org.springframework.core.env.Environment;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

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
            log.info("=============== Active Profiles {} ===============",
                    Arrays.asList(environment.getActiveProfiles()).toString());
            log.info("Swagger2 Docs Address：http://{}:{}/swagger-ui.html",
                    InetAddress.getLocalHost().getHostAddress(), environment.getProperty("server.port"));
            log.info("=========================================================");
        };
    }

}
