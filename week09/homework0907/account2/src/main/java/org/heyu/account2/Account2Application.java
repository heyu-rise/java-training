package org.heyu.account2;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ImportResource;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 * @author heyu
 */
@EnableTransactionManagement
@SpringBootApplication
@ImportResource({"classpath:spring-dubbo.xml"})
public class Account2Application {

	public static void main(String[] args) {
		SpringApplication.run(Account2Application.class, args);
	}

}
