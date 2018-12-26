package edu.soa.zrqservice;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@SpringBootApplication
@MapperScan("mapper")
@EnableEurekaClient
public class ZrqServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(ZrqServiceApplication.class, args);
    }

}

