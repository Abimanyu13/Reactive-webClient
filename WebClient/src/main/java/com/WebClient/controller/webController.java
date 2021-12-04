package com.WebClient.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;

import com.WebClient.Service.ItemsService;
import com.WebClient.dto.Item;

import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/shopkeeper")
@Slf4j
@Tag(name = "Items list APIs", description = "Items list APIs for demo purpose")
public class webController {
	
	@Autowired
	ItemsService service;
	
	@GetMapping(value ="/itemDetails",produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public Flux<Item> getAllCustomersStream() {
        return service.getAllItems();
    }
	
	/*@GetMapping(value ="/item/{id}")
    public Mono<ServerResponse> getUserItem(ServerRequest request) {
		log.debug("webController :: getUserItem - " + request.pathVariable("id"));
       return ServerResponse.ok().contentType(MediaType.APPLICATION_JSON)
        .body(BodyInserters.fromValue(service.getItemsByUId(request.pathVariable("id"))));
    }*/
	
	@GetMapping(value ="/{id}",produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public Flux<Item> getCustomersItem(@PathVariable String id) {
        return service.getItemsByUId(id);
    }
	
	
}
