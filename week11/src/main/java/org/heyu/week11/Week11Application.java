package org.heyu.week11;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 * @author heyu
 * @date 2021/9/5
 */
@SpringBootApplication
@EnableTransactionManagement
public class Week11Application {

    public static void main(String[] args) {
        SpringApplication.run(Week11Application.class);
    }

}
