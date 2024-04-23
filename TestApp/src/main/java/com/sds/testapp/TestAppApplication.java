package com.sds.testapp;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

//아래의 @SpringBootApplication 이 많은 @어노테이션 설정을 포함하고 있기 때문에, 
//Spring Legacy MVC 에서 개발자가 일일이 등록해야 할 각종 설정들이 이제는 불필요..
// @Service, @Resposiry...모두 자동으로 올림..즉 이미 component-scan 기능이 포함.
@SpringBootApplication
public class TestAppApplication {

	public static void main(String[] args) {
		SpringApplication.run(TestAppApplication.class, args);
	}

}