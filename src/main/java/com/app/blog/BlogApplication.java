package com.app.blog;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@SpringBootApplication
@EnableTransactionManagement
public class BlogApplication {
	public static void main(String[] args) {
		new SpringApplicationBuilder(BlogApplication.class)
				.properties("spring.config.use-legacy-processing=true")
				.run(args);
	}
}
