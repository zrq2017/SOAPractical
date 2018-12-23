package edu.soa.zrqservice;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("mapper")
public class ZrqServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(ZrqServiceApplication.class, args);
    }

}

