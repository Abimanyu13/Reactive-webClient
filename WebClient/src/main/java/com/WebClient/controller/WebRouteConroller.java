package com.WebClient.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerResponse;

import com.WebClient.Service.ItemHandler;

import io.swagger.v3.oas.annotations.tags.Tag;

@Configuration
@Tag(name = "Items list route APIs", description = "Items list APIs for demo purpose")
@RequestMapping("/shopkeeper")
public class WebRouteConroller {
	
	@Autowired
	private ItemHandler itemHandler;
	
	@Bean
    public RouterFunction<ServerResponse> routerFunction(){
        return RouterFunctions.route()
                .GET("/router/{id}",itemHandler::getItem)
                .build();

    }
}
