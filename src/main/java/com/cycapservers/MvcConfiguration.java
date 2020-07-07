package com.cycapservers;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import com.cycapservers.game.LobbyManager;

@Configuration
@ComponentScan
@EnableScheduling
public class MvcConfiguration extends WebMvcConfigurerAdapter {
	@Bean
	public CustomMappingExceptionResolver createCustomMappingExceptionResolver() {
		return new CustomMappingExceptionResolver();
	}
	
	@Bean
	public LobbyManager createLobbyManager(){
		return new LobbyManager();
	}
}