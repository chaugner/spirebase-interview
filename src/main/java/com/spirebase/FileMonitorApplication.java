package com.spirebase;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;


@ServletComponentScan
@SpringBootApplication
public class FileMonitorApplication {
	
    public static void main(String[] args) {
        SpringApplication.run(FileMonitorApplication.class, args);
    }
}
