package org.heyu.homework8;

import lombok.extern.slf4j.Slf4j;
import org.heyu.beanstart.model.Student;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @author heyu
 * @date 2021/7/25
 */
@Slf4j
@SpringBootApplication
public class Homework8Application {

    public static void main(String[] args) {
        SpringApplication.run(Homework8Application.class, args);
        Student student = new Student();
        log.info(student.toString());
    }



}
