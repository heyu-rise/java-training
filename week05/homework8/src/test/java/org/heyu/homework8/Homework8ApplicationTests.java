package org.heyu.homework8;

import lombok.extern.slf4j.Slf4j;
import org.heyu.beanstart.model.Klass;
import org.heyu.beanstart.model.School;
import org.heyu.beanstart.model.Student;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@Slf4j
@SpringBootTest
class Homework8ApplicationTests {

    @Autowired
    private Student student;

    @Autowired
    private Klass klass;

    @Autowired
    private School school;

    @Test
    void contextLoads() {
        log.info(student.toString());
        log.info(klass.toString());
        log.info(school.toString());
    }

}
