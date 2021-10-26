package com.example.spinrgmvcbasic;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;

@ServletComponentScan
@SpringBootApplication
public class SpinrgMvcBasicApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpinrgMvcBasicApplication.class, args);
    }

}
