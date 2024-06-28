package com.leminhtien.demoSpringJpaDynamic;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan("com.leminhtien")
public class DemoSpringJpaDynamicApplication {

	public static void main(String[] args) {
		SpringApplication.run(DemoSpringJpaDynamicApplication.class, args);
	}

}
