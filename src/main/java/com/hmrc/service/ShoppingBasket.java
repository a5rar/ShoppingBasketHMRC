package com.hmrc.service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.stream.Collectors;

public class ShoppingBasket {

	private final ProductRepository productrepository;
	
	Map<String,Function<Integer,Integer>> offers;
	
	public ShoppingBasket(ProductRepository productrepository, Map<String,Function<Integer,Integer>> offers)
	{
		this.productrepository = productrepository;
		this.offers= offers;
		
	}
	
	
	public BigDecimal getTotal(List<String> items) {
		
		Map<String,Long> groupedItems =  items.stream().collect(
                Collectors.groupingBy(
                        Function.identity(), Collectors.counting()
                )
        );

		return getTotal(groupedItems);

	}
	
	private BigDecimal getTotal(Map<String,Long> items){
		BigDecimal discount = getDiscount(items);
		BigDecimal total = new BigDecimal("0.00");
		for(Entry<String,Long> entry : items.entrySet()){
			double subtotal = productrepository.getItemPrice(entry.getKey())*entry.getValue();
			total = total.add( new BigDecimal(subtotal));
		}
		return total.subtract(discount).divide(new BigDecimal("100")).setScale(2,RoundingMode.CEILING);
		
	}


	private BigDecimal getDiscount(Map<String, Long> items) {
		BigDecimal discount = new BigDecimal("0.00");
		
		for(Entry<String,Long> entry : items.entrySet()){
			
		
			Integer itemdiscount = 	offers.getOrDefault(entry.getKey(), (quantity) ->{return 0;})
					.apply(entry.getValue().intValue());//its safe to assume on this occasion that quantity is not over Int.max
			discount = discount.add( new BigDecimal(itemdiscount));
		}
		return discount;
	}
	
	
	
	

}
