package com.hmrc.service;

import static org.junit.Assert.assertEquals;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import org.junit.Before;
import org.junit.Test;

public class ShoppingBasketTest {

	ProductRepository repository;
	
	ShoppingBasket basket;
	@Before
	public void setUp() throws Exception {
		
		Map<String,Integer> products = new HashMap<String,Integer>();
		
		products.put("apple", 60);
		products.put("orange", 25);
		repository = new ProductRepository(products);

		basket = new ShoppingBasket(repository);
		
	}	

	@Test
	public void testGetTotal() {
	
		BigDecimal total = basket.getTotal(Arrays.asList("apple","apple","orange","apple"));
		
		assertEquals(new BigDecimal(2.05).setScale(2,RoundingMode.CEILING),total);
	}
	
	
	@Test
	public void testGetTotalCaseSensitive(){

			BigDecimal total = basket.getTotal(Arrays.asList("APPLE","apple","Orange","apple"));
			
			assertEquals(new BigDecimal(2.05).setScale(2,RoundingMode.CEILING),total);
	}
	
	@Test
	public void testGetTotalNoItems() {
	
		BigDecimal total = basket.getTotal(Arrays.asList());
		
		assertEquals(new BigDecimal(0).setScale(2,RoundingMode.CEILING),total);
	}
	

}
