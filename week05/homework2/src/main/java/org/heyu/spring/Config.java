package org.heyu.spring;

import org.heyu.spring.model.Student;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author heyu
 * @date 2021/7/25
 */
@Configuration
public class Config {

    @Bean(name = "student2")
    public Student student2() {
        Student student = new Student();
        student.setId(2);
        student.setName("2");
        return student;
    }

}
