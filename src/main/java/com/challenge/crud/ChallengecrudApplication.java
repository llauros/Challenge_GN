package com.challenge.crud;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.data.web.config.EnableSpringDataWebSupport;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import springfox.documentation.swagger2.annotations.EnableSwagger2;

@SpringBootApplication
@EnableSpringDataWebSupport
@EnableCaching
@EnableSwagger2
@RestController
@RequestMapping("/app")
public class ChallengecrudApplication {
	
	@GetMapping
	public ModelAndView swaggerUi() {
		return new ModelAndView("redirect:/app/swagger-ui.html");
	}

	public static void main(String[] args) {
		SpringApplication.run(ChallengecrudApplication.class, args);
	}

}
