package com.WebClient.Service;

import java.io.Console;
import java.time.Duration;

import org.reactivestreams.Publisher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.retry.annotation.Recover;
import org.springframework.retry.annotation.Retryable;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpStatusCodeException;
import org.springframework.web.reactive.function.client.ClientResponse;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.server.ServerResponse;

import com.WebClient.custom.exception.BusinessException;
import com.WebClient.dao.ItemDao;
import com.WebClient.dto.Item;
import com.WebClient.dto.User;
import com.fasterxml.jackson.databind.ObjectMapper;


import lombok.extern.slf4j.Slf4j;
import reactor.core.Disposable;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
@Slf4j
public class ItemsService {

	@Autowired
	private ItemDao itemDao;
	
	@Autowired
	public WebClient webClient;
	
	ObjectMapper mapper = new ObjectMapper();
	
	public Flux<Item> getAllItems(){
		log.info(" ItemsService :: getAllItems :: Entry");
		/*Mono<ClientResponse> response = webClient.get()
				.uri("/1")
				.accept(MediaType.TEXT_EVENT_STREAM)
				.retrieve().bodyToMono(ClientResponse.class).log();
		
		System.out.println("-------response------" + response);
		*/
		
		/*user1.subscribe(
				  value -> System.out.println("11" + value), 
				  error -> error.printStackTrace()
				  () -> System.out.println("completed");
				);
		*/
		
		//response.flatMap(r -> mapper.convertValue(r.body(User.class), User.class));
		
				//Mono<User> user = response.flatMap( e -> e.bodyToMono(User.class));
				//System.out.println("-------User------" + user);
		
		/*Mono<Integer> user = webClient.get()
				.uri("/1")
				.accept(MediaType.TEXT_EVENT_STREAM)
				.retrieve().bodyToMono(User.class)
				.onErrorReturn(new User(0, null))
				.map(User::getId);*/
		//System.out.println("-------User------" + user.single());
		
		//Flux.just(itemDao.getItemsByID(user.block()));
		
		//itemDao.getItems().log().subscribe(System.out::println);
		//Integer id = user.subscribe();
		
		//throw new BusinessException("404", "not found");
		return itemDao.getItems();
	}
	
	@Retryable(value = HttpStatusCodeException.class, maxAttempts = 3)
	public Flux<Item> getItemsByUId(String id){
		log.info(" ItemsService :: getItemsByUId :: Entry - " + id );
		Flux<Item> user = (Flux<Item>) webClient.get()
				.uri("/"+id)
				.accept(MediaType.TEXT_EVENT_STREAM)
				.retrieve()
				.onStatus(httpStatus -> HttpStatus.INTERNAL_SERVER_ERROR.equals(httpStatus),
		                clientResponse -> Mono.error(new BusinessException(clientResponse.statusCode().toString(), "Error in Item")))
				.bodyToFlux(User.class).flatMap(i -> itemDao.getItemsByID(i.getId()))
				.switchIfEmpty(Flux.error(new BusinessException("404", "Item not found")));
		log.info(" ItemsService :: getItemsByUId :: Exit - " + id );
		return user;
	}
	
	
	
	public Flux<Item> getItemsByUIdRes(String id){
		log.info(" ItemsService :: getItemsByUId :: Entry - " + id );
		Flux<Item> user = (Flux<Item>) webClient.get()
				.uri("/"+id)
				.accept(MediaType.TEXT_EVENT_STREAM)
				.retrieve()
				.onStatus(httpStatus -> HttpStatus.INTERNAL_SERVER_ERROR.equals(httpStatus),
		                clientResponse -> Mono.error(new BusinessException(clientResponse.statusCode().toString(), "Error in Item")))
				.bodyToFlux(User.class).flatMap(i -> itemDao.getItemsByID(i.getId()))
				.switchIfEmpty(Flux.error(new BusinessException("404", "Item not found")));
		log.info(" ItemsService :: getItemsByUId :: Exit - " + id );
		return user;
	}
	
	/*private  Flux<ItemDto> getItemsResponse(String id){
		System.out.println("------as--------");
		//Flux<ItemDto> item = user.flatMap(i -> itemDao.getItems().filter(j -> j.getOrderById().equals(i.getId())));
		Flux<ItemDto> item = itemDao.getItems().filter(i -> i.getOrderUserId().equals(user.subscribe(
				s -> s.getId())));
		//System.out.println("------item--------" + id.doOnNext(i -> System.out.println("count in stream flow : " + i)));
		//item.block(System.out::println);
		return item;
	}*/
	
	@Recover
    public String recover(HttpStatusCodeException exception) {
        return "Please try after some time!!";
    }
	
}
