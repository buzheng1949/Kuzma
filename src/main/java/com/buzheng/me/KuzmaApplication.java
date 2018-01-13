package com.buzheng.me;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan(value = "com.buzheng.me.mapper")
public class KuzmaApplication {

	public static void main(String[] args) {
		SpringApplication.run(KuzmaApplication.class, args);
	}
}
