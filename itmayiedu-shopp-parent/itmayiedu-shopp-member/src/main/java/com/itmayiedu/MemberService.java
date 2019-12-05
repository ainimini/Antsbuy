package com.itmayiedu;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

/**
 * @ClassName dell
 * @Description TOOD
 * @Author X
 * @Data 2019/12/5
 * @Version 1.0
 **/
@SpringBootApplication
@EnableEurekaClient
public class MemberService {

    public static void main(String[] args) {
        SpringApplication.run(MemberService.class,args);
    }
}
