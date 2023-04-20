package com.netty;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient
public class SpringCloudNettyApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringCloudNettyApplication.class, args);
    }

}
