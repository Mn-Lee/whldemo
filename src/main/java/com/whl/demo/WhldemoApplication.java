package com.whl.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;

@SpringBootApplication
@ServletComponentScan
public class WhldemoApplication {

    public static void main(String[] args) {
        SpringApplication.run(WhldemoApplication.class, args);
    }

}
