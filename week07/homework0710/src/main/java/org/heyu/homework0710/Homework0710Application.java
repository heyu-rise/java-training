package org.heyu.homework0710;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 * @author heyu
 */
@SpringBootApplication
@EnableTransactionManagement
public class Homework0710Application {

	public static void main(String[] args) {
		SpringApplication.run(Homework0710Application.class, args);
	}

}
