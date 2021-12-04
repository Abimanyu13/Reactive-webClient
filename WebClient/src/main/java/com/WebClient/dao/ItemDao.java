package com.WebClient.dao;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;

import com.WebClient.dto.Item;

import reactor.core.publisher.Flux;

@Component
public class ItemDao {

	public Flux<Item> getItems() {

		List<Item> productsList = new ArrayList<Item>();
		productsList.add(new Item(2001, "car", 2));
		productsList.add(new Item(2002, "cookies", 8));
		productsList.add(new Item(2003, "cooker", 1));
		productsList.add(new Item(2004, "car", 3));
		productsList.add(new Item(2005, "candy", 5));
		productsList.add(new Item(2006, "Cheez-It", 4));
		productsList.add(new Item(2007, "Nuts", 3));
		productsList.add(new Item(2008, "Sugar", 4));
		productsList.add(new Item(2009, "Pulses", 5));
		productsList.add(new Item(2010, "Cereals", 6));
		productsList.add(new Item(2011, "salsa", 7));

		return Flux.fromIterable(productsList);

	}
	
	public Flux<Item> getItemsByID(Integer id) {
		return getItems().filter(p -> p.getOrderUserId().equals(id));
	}
	

}
