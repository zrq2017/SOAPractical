package edu.soa.customerservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.context.annotation.Bean;

import javax.sql.DataSource;

@SpringBootApplication(exclude= DataSourceAutoConfiguration.class)
@EnableEurekaClient
public class CustomerServiceApplication {
    @Bean(name = "CustomerDataSource")
    @ConfigurationProperties(prefix = "datasource.customer")
    public DataSource testDataSource() {
        return DataSourceBuilder.create().build();
    }
    public static void main(String[] args) {
        SpringApplication.run(CustomerServiceApplication.class, args);
    }

}

