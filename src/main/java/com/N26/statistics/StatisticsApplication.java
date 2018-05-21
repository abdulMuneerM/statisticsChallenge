package com.N26.statistics;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@ComponentScan({"com.N26.statistics"})
@EnableScheduling
public class StatisticsApplication {

    public static void main(String args[]) {
        SpringApplication.run(StatisticsApplication.class, args);
    }
}
