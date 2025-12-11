package org.ash;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("org.ash.Mapper")
class SettingApplication {
    public static void main(String[] args) {
        SpringApplication.run(SettingApplication.class, args);
    }
}
