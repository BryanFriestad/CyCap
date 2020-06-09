package com.cycapservers;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

@Configuration
@ComponentScan
public class MvcConfiguration extends WebMvcConfigurerAdapter {
	@Bean
	public CustomMappingExceptionResolver createCustomMappingExceptionResolver() {
		return new CustomMappingExceptionResolver();
	}
}