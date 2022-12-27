package com.project.devgram.oauth2.principal;


import com.project.devgram.entity.Users;
import com.project.devgram.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;


@Service
@Slf4j
@RequiredArgsConstructor
public class PrincipalDetailsService implements UserDetailsService{


	private final UserRepository userRepository;
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		log.info("username: {}",username);

			Optional<Users> optionalUser = userRepository.findByUsername(username);

		if(optionalUser.isPresent()) {

			Users users = optionalUser.get();
			log.info("users: {}",users);

			return new PrincipalDetails(users);
		}
		log.error("loadUserByUsername : x");
		return null;
	}

}
