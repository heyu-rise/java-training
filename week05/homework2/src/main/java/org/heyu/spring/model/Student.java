package org.heyu.spring.model;

import lombok.Data;
import org.springframework.stereotype.Component;

/**
 * @author heyu
 * @date 2021/7/25
 */
@Data
@Component
public class Student {

    private int id;

    private String name;

}
