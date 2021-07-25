package org.heyu.beanstart;

import org.heyu.beanstart.model.Klass;
import org.heyu.beanstart.model.School;
import org.heyu.beanstart.model.Student;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.ArrayList;
import java.util.List;

/**
 * @author heyu
 * @date 2021/7/25
 */
@Configuration
public class BeanStartAutoConfiguration {

    @Bean(name = "student")
    public Student student() {
        Student student = new Student();
        student.setId(2);
        student.setName("2");
        return student;
    }

    @Bean(name = "klass")
    public Klass klass() {
        Klass klass = new Klass();
        List<Student> students = new ArrayList<>(5);
        Student student1 = new Student();
        student1.setId(1);
        student1.setName("1");
        students.add(student1);
        Student student2 = new Student();
        student2.setId(2);
        student2.setName("2");
        students.add(student2);
        klass.setStudents(students);
        return klass;
    }

    @Bean(name = "school")
    public School school() {
        School school = new School();
        Student student = new Student();
        student.setId(2);
        student.setName("2");
        school.setStudent(student);

        Klass klass = new Klass();
        List<Student> students = new ArrayList<>(5);
        Student student1 = new Student();
        student1.setId(1);
        student1.setName("1");
        students.add(student1);
        Student student2 = new Student();
        student2.setId(2);
        student2.setName("2");
        students.add(student2);
        klass.setStudents(students);
        school.setClass1(klass);
        return school;
    }

}
