package com.hmrc.service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Currency;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Map.Entry;
import java.util.stream.Collectors;

public class ShoppingBasket {

	ProductRepository productrepository;
	
	public ShoppingBasket(ProductRepository productrepository)
	{
		this.productrepository = productrepository;
	}
	
	
	public BigDecimal getTotal(List<String> items) {
		

		Integer total = 0;
		for(String  item: items){
			
			total += productrepository.getItemPrice(item);
		}

		return new BigDecimal(total).divide(new BigDecimal(100)).setScale(2,RoundingMode.CEILING);
	}
	
	

	

}
