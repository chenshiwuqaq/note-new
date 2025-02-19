package org.ash;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@EnableDiscoveryClient//开启服务发现功能
@SpringBootApplication
public class noteApplication {
    public static void main(String[] args) {
        SpringApplication.run(noteApplication.class, args);
    }
}
