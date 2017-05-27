package controllers;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.google.common.base.Predicate;
import com.google.common.base.Predicates;

import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import static com.google.common.base.Predicates.or;
import static springfox.documentation.builders.PathSelectors.regex;

@Configuration
@EnableSwagger2
public class SwaggerConfig {                                    
	
	@Bean
	public Docket api() {                
    		return new Docket(DocumentationType.SWAGGER_2)          
    				.select()                                  
    	          	.paths(Predicates.not(PathSelectors.regex("/error")))
    	          	.build()
    	          	
      			.apiInfo(apiInfo());
	}
	
	private final String NAME = "Rafzz";
	private final String URL = "https://github.com/rafzz";
	private final String EMAIL = "rafzz@interia.eu";
	
	private Contact contact = new Contact(NAME, URL, EMAIL);
	
	private ApiInfo apiInfo() {
    		ApiInfo apiInfo = new ApiInfo(
      			"Rafał Szmigiel REST API",
      			"",
      			"API TOS",
      			"Terms of service",
      			contact,
      			"License of API",
      			"API license URL");
    		return apiInfo;
	}
	
	
}