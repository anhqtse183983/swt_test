package com.koi.FA24_SE1854_SWP391_G6_KoiCareHome;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@EnableTransactionManagement
@SpringBootApplication(exclude = { SecurityAutoConfiguration.class })
@OpenAPIDefinition(info = @Info(title = "API"))
public class Fa24Se1854Swp391G6KoiCareHomeApplication {

	public static void main(String[] args) {SpringApplication.run(Fa24Se1854Swp391G6KoiCareHomeApplication.class, args);}

}
