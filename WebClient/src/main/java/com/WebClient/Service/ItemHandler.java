package com.WebClient.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.BodyInserter;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;

import com.WebClient.dto.Item;

import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
@Slf4j
public class ItemHandler {
	
	@Autowired
	private ItemsService service;
	
	public Mono<ServerResponse> getItem(ServerRequest request){
        Flux<Item> itemList = service.getItemsByUIdRes(request.pathVariable("id"));
        return itemList.next().flatMap( i -> ServerResponse.ok().contentType(MediaType.TEXT_EVENT_STREAM).body(itemList, Item.class))
        		.switchIfEmpty(ServerResponse.status(HttpStatus.NOT_FOUND).build());
        
    }

}
