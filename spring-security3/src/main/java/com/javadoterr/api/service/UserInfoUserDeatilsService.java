package com.javadoterr.api.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import com.javadoterr.api.config.UserInfoUserDetails;
import com.javadoterr.api.entity.UserInfo;
import com.javadoterr.api.repository.UserInfoRepository;

public class UserInfoUserDeatilsService implements UserDetailsService {
	
	@Autowired
	private UserInfoRepository userInfoRepository;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		
		Optional<UserInfo> userInfo = this.userInfoRepository.findByName(username);
		
		return  userInfo.map(UserInfoUserDetails::new)
					.orElseThrow(() -> new UsernameNotFoundException("user not found "+username));
	}

}
