package com.hmrc.service;

import static org.junit.Assert.assertEquals;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

import org.junit.Before;
import org.junit.Test;

public class ShoppingBasketTest {

	ProductRepository repository;

	Map<String, Function<Integer, Integer>> offers = new HashMap();

	ShoppingBasket basket;

	@Before
	public void setUp() throws Exception {

		Map<String, Integer> products = new HashMap<String, Integer>();

		products.put("apple", 60);
		products.put("orange", 25);
		repository = new ProductRepository(products);

		basket = new ShoppingBasket(repository, offers);

	}

	@Test
	public void testGetTotal() {

		BigDecimal total = basket.getTotal(Arrays.asList("apple", "apple",
				"orange", "apple"));

		assertEquals(new BigDecimal("2.05").setScale(2, RoundingMode.CEILING),
				total);
	}

	@Test
	public void testGetTotalCaseSensitive() {

		BigDecimal total = basket.getTotal(Arrays.asList("APPLE", "apple",
				"Orange", "apple"));

		assertEquals(new BigDecimal("2.05").setScale(2, RoundingMode.CEILING),
				total);
	}

	@Test
	public void testGetTotalNoItems() {

		BigDecimal total = basket.getTotal(Arrays.asList());

		assertEquals(new BigDecimal("0.00").setScale(2, RoundingMode.CEILING), total);
	}

	@Test
	public void testGetTotalAppleOffer() {
		addOffers();
		basket = new ShoppingBasket(repository, offers);

		BigDecimal total = basket.getTotal(Arrays.asList("apple", "apple"));

		assertEquals(new BigDecimal("0.60").setScale(2, RoundingMode.CEILING),
				total);
		
	}
	
	@Test
	public void testGetTotalOrangeOffer() {
		addOffers();
		basket = new ShoppingBasket(repository, offers);

		BigDecimal total = basket.getTotal(Arrays.asList("orange", "orange", "orange"));

		assertEquals(new BigDecimal("0.50").setScale(2, RoundingMode.CEILING),
				total);
		
	}
	
	@Test
	public void testGetTotalCombinationOffer() {
		addOffers();
		basket = new ShoppingBasket(repository, offers);

		BigDecimal total = basket.getTotal(Arrays.asList("apple","apple","orange", "orange", "orange"));

		assertEquals(new BigDecimal("1.10").setScale(2, RoundingMode.CEILING),
				total);
		
	}
	
	@Test
	public void testGetTotalCombinationOfferDoubleItems() {
		addOffers();
		basket = new ShoppingBasket(repository, offers);

		BigDecimal total = basket.getTotal(Arrays.asList("apple","apple","apple","apple","orange",
				"orange", "orange","orange", "orange", "orange"));

		assertEquals(new BigDecimal("2.20").setScale(2, RoundingMode.CEILING),
				total);
		
	}



	private void addOffers() {
		offers.put("apple", (quantity) -> {
			Integer price = repository.getItemPrice("apple");
			Integer discount = new Double(quantity / 2).intValue() * price;
			return discount;
		});
		
		offers.put("orange", (quantity) -> {
			Integer price = repository.getItemPrice("orange");
			Integer discount = new Double(quantity / 3).intValue() * price;
			return discount;
		});
	}

}
