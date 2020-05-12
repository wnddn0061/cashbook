package com.gdu.cashbook;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
//@SpringBootApplication = @Configuration+@EnableAutoConfiguration+@ComponentScan
/*
 * Configuration->application.properties
 * componentScan -> 에노테이션 서치
 */


public class CashbookApplication {

	public static void main(String[] args) {
		SpringApplication.run(CashbookApplication.class, args);
	}

}
