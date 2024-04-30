package com.sds.movieapp;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import kr.or.kobis.kobisopenapi.consumer.rest.KobisOpenAPIRestService;

/*
 *  Spring 3.0부터 스프링 xml을 대체할 어노테이션을 지원함.
 *  @Configuration : 스프링 빈 설정 xml 대체
 *  
 *  <bean id="key" class="java.lang.String">
		<constructor-arg value="0173122db3da4fb149dade4d437aa368"/>
	</bean>		
	
	<bean id="kobisOpenAPIRestService" class="kr.or.kobis.kobisopenapi.consumer.rest.KobisOpenAPIRestService">
		<constructor-arg ref="key"/> ---> 위빙
	</bean>
 */

@Configuration
public class AppConfig { //root-context.xml 대신함
	
	@Bean
	public String key() {
		return "0173122db3da4fb149dade4d437aa368";
	}
	
	@Bean
	public KobisOpenAPIRestService kobisOpenAPIRestService(String key) {
		return new KobisOpenAPIRestService(key);
	}
}