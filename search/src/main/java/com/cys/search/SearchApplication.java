package com.cys.search;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableEurekaClient
@EnableFeignClients(basePackages = "com.cys.search.feign")
public class SearchApplication {

	public static void main(String[] args) {
		//解决netty冲突
		System.setProperty("es.set.netty.runtime.available.processors", "false");
		SpringApplication.run(SearchApplication.class, args);
	}

}
