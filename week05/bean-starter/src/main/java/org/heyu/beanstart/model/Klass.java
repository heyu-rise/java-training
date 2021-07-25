package org.heyu.beanstart.model;

import lombok.Data;

import java.util.List;
/**
 * @author heyu
 * @date 2021/7/25
 */
@Data
public class Klass { 
    
    List<Student> students;
    
    public void dong(){
        System.out.println(this.getStudents());
    }
    
}
