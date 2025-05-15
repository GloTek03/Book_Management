package com.ltfullstack.commonservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan({"com.ltfullstack.commonservice"})
public class CommonserviceApplication {

	public static void main(String[] args) {
		SpringApplication.run(CommonserviceApplication.class, args);
	}

}
