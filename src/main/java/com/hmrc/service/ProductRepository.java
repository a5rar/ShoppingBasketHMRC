package com.hmrc.service;

import java.util.HashMap;
import java.util.Map;

public class ProductRepository {
	
	private  Map<String,Integer> productRepo = new HashMap<String, Integer>();

	ProductRepository(Map<String,Integer> productRepo){
		
		this.productRepo =  productRepo;

	}

	public Integer getItemPrice(String productName) {
		return productRepo.get(productName.toLowerCase());
		
	}
	
	
	
	
	

}
