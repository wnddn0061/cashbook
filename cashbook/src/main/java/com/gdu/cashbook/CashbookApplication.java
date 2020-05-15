package com.gdu.cashbook;

import java.util.Properties;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.PropertySource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;

@SpringBootApplication
//@SpringBootApplication = @Configuration+@EnableAutoConfiguration+@ComponentScan
/*
 * Configuration->application.properties, 빈 생성
 * componentScan -> 에노테이션 서치
 */

@PropertySource("classpath:google.properties")
public class CashbookApplication {
	@Value("${google.username}")
	public String username;
	@Value("${google.password}")
	public String password;

	public static void main(String[] args) {
		SpringApplication.run(CashbookApplication.class, args);
	}
	//빈 생성
	@Bean
	public JavaMailSender getJavaMailSender() {//메소드의 리턴 값이 JavaMailSender의 객체값
		//옵션값 설정
		JavaMailSenderImpl javaMailSender = new JavaMailSenderImpl();
		javaMailSender.setHost("smtp.gmail.com");//메일 서버의 이름
		javaMailSender.setPort(587);//포트 번호
		javaMailSender.setUsername(username);//메일 주소
		javaMailSender.setPassword(password);//비밀번호
		
		//옵션항목
		Properties prop = new Properties(); //properties == HashMap<String,String>
		prop.setProperty("mail.smtp.auth","true"); //인증기능 사용
		prop.setProperty("mail.smtp.starttls.enable","true");
		javaMailSender.setJavaMailProperties(prop);
		return javaMailSender;
		
	}

}
