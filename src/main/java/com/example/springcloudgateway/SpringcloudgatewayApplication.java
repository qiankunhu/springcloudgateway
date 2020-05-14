package com.example.springcloudgateway;

import com.example.springcloudgateway.resolver.UrlResolver;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.hystrix.EnableHystrix;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class SpringcloudgatewayApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringcloudgatewayApplication.class, args);
    }

    @Bean
    public UrlResolver urlResolver(){
        return new UrlResolver();
    }

}
