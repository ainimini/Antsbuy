package com.itmayiedu.app;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

/**
 * @ClassName dell
 * @Description TOOD
 * @Author X
 * @Data 2019/12/5
 * @Version 1.0
 **/
@SpringBootApplication
@EnableEurekaServer
public class EurekaServer {

    public static void main(String[] args) {

        SpringApplication.run(EurekaServer.class,args);
    }
}
