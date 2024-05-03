package com.sds.movieapp;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.core.MongoClientSettingsFactoryBean;
import org.springframework.data.mongodb.core.MongoTemplate;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;

//스프링의 빈설정xml을 대신하는 자바 클래스
//몽고 DB에서는 spring-mybatis처럼 CRUD 수행하는 객체인 ~Template객체를 지원함
//따라서 MongoTemplate 객체를 빈으로 등록해 우리 DAO에서 주입받아 사용하기
//<bean id="mongoClient" class="mongoClient"></bean> : 몽고 DB 접속 객체
//<bean id="mongoTemplate" class="mongoTemplate"></bean> : CRUD 수행객체

@Configuration
public class MongoConfig {
	
	@Bean
	public MongoClient mongoClient() {
		
		return MongoClients.create("mongodb://test:1234@localhost:27017"); //접속 문자열생성
	}
	
	@Bean
	public MongoTemplate mongoTemplate() {
		
		return new MongoTemplate(mongoClient(), "movie"); //(클라이름, db이름);
	}
}
