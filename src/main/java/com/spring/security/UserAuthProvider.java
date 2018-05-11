package com.spring.security;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;

import com.spring.entity.UserEntity;
import com.spring.repository.UserRepository;
import com.spring.utils.PasswordUtil;
import com.spring.utils.StringUtil;

@Component
public class UserAuthProvider implements AuthenticationProvider {

	@Autowired
	UserRepository userRepository;

	@Override
	public Authentication authenticate(Authentication authentication) throws AuthenticationException {
		
		String email = authentication.getName();
		String password = authentication.getCredentials().toString();
		if(StringUtil.isEmpty(email) || StringUtil.isEmpty(password)) {
			throw new BadCredentialsException("Empty fields.");
		}
		UserEntity user = userRepository.findByEmail(authentication.getName());
		if (user == null) {
			throw new BadCredentialsException("User does not exist.");
		}

		if (!PasswordUtil.passwordCheck(password, user.getPassword())) {
			throw new BadCredentialsException("Incorrect password.");
		}
		List<GrantedAuthority> grantedAuths = new ArrayList<>();
		grantedAuths.add(new SimpleGrantedAuthority("ROLE_USER"));
		return new UsernamePasswordAuthenticationToken(authentication.getName(), authentication.getCredentials(), grantedAuths);
	}

	@Override
	public boolean supports(Class<?> authentication) {
		return authentication.equals(UsernamePasswordAuthenticationToken.class);
	}

}
