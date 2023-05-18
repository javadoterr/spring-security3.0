package com.javadoterr.api.service;

import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.javadoterr.api.dto.Product;
import com.javadoterr.api.entity.UserInfo;
import com.javadoterr.api.repository.UserInfoRepository;

import jakarta.annotation.PostConstruct;

@Service
public class ProductService {
	
	@Autowired
	private UserInfoRepository infoRepository;
	
	@Autowired
	private PasswordEncoder encoder;
	
	List<Product> productList = null;
	
	@PostConstruct
	public void loadProductsFromDB() {
		 productList=IntStream.rangeClosed(1, 100)
				.mapToObj(i -> Product.builder()
						.productId(i)
						.name("product : "+i)
						.qty(new Random().nextInt(10))
						.price(new Random().nextInt(5000)).build()
						).collect(Collectors.toList());
	}
	
	public List<Product> getProducts(){
		return productList;
	}
	
	public Product getProduct(int id) {
		return productList.stream()
						.filter(product -> product.getProductId() == id)
						.findAny()
						.orElseThrow(() -> new RuntimeException("product : "+ id + " not found"));
	}
	
	
	 public String addUser(UserInfo info) {
		 info.setPassword(encoder.encode(info.getPassword()));
		 this.infoRepository.save(info);
		 
		 return "user added to system";
	 }

}
