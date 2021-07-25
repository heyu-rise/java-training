package org.heyu.spring;

import org.heyu.spring.model.Student;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * @author heyu
 * @date 2021/7/25
 */
public class SystemStart {

    public static void main(String[] args) {
        ApplicationContext context = new ClassPathXmlApplicationContext("applicationContext.xml");

        Student student1 = (Student) context.getBean("student2");
        System.out.println(student1.toString());

//        Student student2 = context.getBean(Student.class);
//        System.out.println(student2.toString());
    }

}
