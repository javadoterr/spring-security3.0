package com.javadoterr.api.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.javadoterr.api.dto.AuthRequest;
import com.javadoterr.api.dto.Product;
import com.javadoterr.api.entity.UserInfo;
import com.javadoterr.api.service.JwtService;
import com.javadoterr.api.service.ProductService;

@RestController
@RequestMapping(path = "/products")
public class ProductController {
	
	@Autowired
	private ProductService service;
	
	@Autowired
	private JwtService jwtService;
	
	@Autowired
	private AuthenticationManager authenticationManager;
	
	
	@GetMapping(path = "/welcome")
	public String welcome() {
		return "Welcome this endpoint is not secure";
	}
	
	
	@PostMapping(path = "/new")
	public String addNewUser(@RequestBody UserInfo info) {
		return service.addUser(info);
	}
	
	
	@GetMapping(path = "/all")
	@PreAuthorize(value = "hasAuthority('ROLE_ADMIN')")
	public List<Product> getAllProducts(){
		return this.service.getProducts();
	}
	
	@GetMapping(path = "/{id}")
	@PreAuthorize(value = "hasAuthority('ROLE_USER')")
	public Product getProductById(@PathVariable("id") int id) {
		return this.service.getProduct(id);
	}
	
	//jwt end point to generate token
	@PostMapping(path = "/authenticate")
	public String authenticateAndGetToken(@RequestBody AuthRequest authRequest) {
		String token;
		Authentication authentication = authenticationManager.authenticate(
				new UsernamePasswordAuthenticationToken(authRequest.getUsername(), authRequest.getPassword()));
		
		if(authentication.isAuthenticated()) {			
			token = jwtService.generateToken(authRequest.getUsername());
		}else {
			throw new UsernameNotFoundException("invalid user request !");
		}
		
		
		return token;
	}

}
