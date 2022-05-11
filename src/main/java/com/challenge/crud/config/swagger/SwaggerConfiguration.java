package com.challenge.crud.config.swagger;

import java.util.Arrays;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.challenge.crud.models.User;

import springfox.documentation.builders.ParameterBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.schema.ModelRef;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;

@Configuration
public class SwaggerConfiguration {
	
	/**
	 * Método estatico reponsavel por passar Lista com ResponseBuilder informando
	 * status possiveis e seus significados para todos os endpoints
	 * 
	 * @return Lista com Response
	 
	@SuppressWarnings("unused")
	private static List<Response> responseMessage() {
		return new ArrayList<Response>() {
			private static final long serialVersionUID = 1L;
			{
				add(new ResponseBuilder().code("200").description("Sucesso!").build());
				add(new ResponseBuilder().code("201").description("Criado!").build());
				add(new ResponseBuilder().code("400").description("Erro na requisição!").build());
				add(new ResponseBuilder().code("401").description("Não Autorizado!").build());
				add(new ResponseBuilder().code("403").description("Proibido!").build());
				add(new ResponseBuilder().code("404").description("Rota Não Encontrada!").build());
				add(new ResponseBuilder().code("500").description("Erro no Servidor!").build());
			}
		};
	}

	/**
	 * Método estático responsavel por passar dados de contato
	 * 
	 * @return Contact com dados
	 
	private static Contact contact() {
		return new Contact("", "https://github.com/",
				"lucaslauro@live.com");
	}

	/**
	 * Método responsavel por passar dados do projeto e receber dados do metodo
	 * contact()
	 * 
	 * @return ApiInfo com dados do projeto
	 
	public static ApiInfo metadata() {
		return new ApiInfoBuilder().title("API - ")
				.description("Projeto API Spring - ").version("1.0.0")
				.license("Apache License Version 2.0")
				.licenseUrl("https://github.com/").contact(contact())
				.build();
	}

	/**
	 * Método responsavel por receber dados do metodo metadata e fornecer o package
	 * para os controladores
	 * 
	 * @return Docket com api documentada
	 */
	@Bean
	public Docket api() {
		return new Docket(DocumentationType.SWAGGER_2)
				.select()
				.apis(RequestHandlerSelectors.basePackage("com.challenge.crud"))
				.paths(PathSelectors.any())
				.build()
				.ignoredParameterTypes(User.class)	
				.globalOperationParameters(Arrays.asList(
						new ParameterBuilder()
						.name("Authorization")
						.description("Header para token JWT")
						.modelRef(new ModelRef("string"))
						.parameterType("header")
						.required(false)
						.build()));
						
				
				/*.apiInfo(metadata()).useDefaultResponseMessages(false);*/
	}
}
