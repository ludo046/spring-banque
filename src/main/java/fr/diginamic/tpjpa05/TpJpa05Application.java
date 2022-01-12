package fr.diginamic.tpjpa05;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@SpringBootApplication
public class TpJpa05Application implements WebMvcConfigurer {

	public static void main(String[] args) {
		SpringApplication.run(TpJpa05Application.class, args);
	}
	
	/**
	* Configuration pour le chargement des
	* messages Intenationaux
	* messages.properties
	* @return
	*/
	@Bean
	public MessageSource messageSource() {
		ResourceBundleMessageSource messageSource =
		new ResourceBundleMessageSource();
		messageSource.setBasename("i18n/messages");
		messageSource.setDefaultEncoding("UTF-8");
		return messageSource;
	}

}
