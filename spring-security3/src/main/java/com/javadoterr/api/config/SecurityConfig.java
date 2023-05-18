package com.javadoterr.api.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

import com.javadoterr.api.service.UserInfoUserDeatilsService;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class SecurityConfig {
	
	
	//authentication
	@Bean
	public UserDetailsService userDetailsService() {
//		UserDetails admin = User.withUsername("Basant")
//								.password(encoder.encode("Pwd1"))
//								.roles("ADMIN")
//								.build();
//		
//		UserDetails user = User.withUsername("John")
//				.password(encoder.encode("Pwd2"))
//				.roles("USER")
//				.build();
//		
//		return new InMemoryUserDetailsManager(admin, user);
		
		return new UserInfoUserDeatilsService();
	}
	
	@Bean
	public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
		
		return http.csrf().disable()
				.authorizeHttpRequests()
				.requestMatchers("/products/welcome", "/products/new").permitAll()
				.and()
				.authorizeHttpRequests()
				.requestMatchers("/products/**").authenticated()
				.and()
				.formLogin()
				.and()
				.build();
	}
	
	@Bean
	public PasswordEncoder encoder() {
		
		return new BCryptPasswordEncoder();
	}
	
	@Bean
	public AuthenticationProvider authenticationProvider() {
		DaoAuthenticationProvider daoAuthenticationProvider = new DaoAuthenticationProvider();
		daoAuthenticationProvider.setUserDetailsService(userDetailsService());
		daoAuthenticationProvider.setPasswordEncoder(encoder());
		
		return daoAuthenticationProvider;
	}

}
