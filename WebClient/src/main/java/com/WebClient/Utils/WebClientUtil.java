package com.WebClient.Utils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;

import com.WebClient.configuration.ServerIPProperties;

@Configuration
public class WebClientUtil {
	
	@Autowired
	ServerIPProperties serverProperties;
	
	@Bean
	public WebClient getWebClient(WebClient.Builder webClientBuilder){
		return webClientBuilder.baseUrl(serverProperties.getUrls()).build();
	}
}
