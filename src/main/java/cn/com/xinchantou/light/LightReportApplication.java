package cn.com.xinchantou.light;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@MapperScan("cn.com.xinchantou.light.mapper")
@SpringBootApplication
public class LightReportApplication {

    public static void main(String[] args) {
        SpringApplication.run(LightReportApplication.class, args);
    }

}