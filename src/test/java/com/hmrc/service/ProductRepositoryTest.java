package com.hmrc.service;

import static org.junit.Assert.*;

import java.util.HashMap;
import java.util.Map;

import org.junit.Before;
import org.junit.Test;

public class ProductRepositoryTest {

	ProductRepository repository;
	@Before
	public void setUp() throws Exception {
		
		Map<String,Integer> products = new HashMap<String,Integer>();
		
		products.put("apple", 60);
		products.put("orange", 25);
		repository = new ProductRepository(products);

		
	}
	
	@Test
	public void getItemPrice() {
		int price = repository.getItemPrice("apple");
		
		assertEquals(price,60);
	}
	
	@Test
	public void getItemPriceCaseInsensitive() {
		int price = repository.getItemPrice("APPLE");
		
		assertEquals(price,60);
	}
	
	

}
