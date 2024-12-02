package com.lgcns.hrm.cv;

import com.lgcns.hrm.cv.annotation.EnableJwt;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;

@SpringBootApplication
@EnableAsync
@EnableJwt
public class HrmCvApiApplication {

    public static void main(String[] args) {

        SpringApplication.run(HrmCvApiApplication.class, args);

    }


}
