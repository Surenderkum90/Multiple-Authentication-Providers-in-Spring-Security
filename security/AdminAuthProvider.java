package com.customers.security;

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

import com.customers.entity.AdminEntity;
import com.customers.repository.AdminRepository;
import com.customers.utils.PasswordUtil;
import com.customers.utils.StringUtil;

@Component
public class AdminAuthProvider implements AuthenticationProvider{
	
	@Autowired
	AdminRepository adminRepository;
	
	@Override
	public Authentication authenticate(Authentication authentication) throws AuthenticationException {
		
		String email = authentication.getName();
		String password = authentication.getCredentials().toString();
		if(StringUtil.isEmpty(email) || StringUtil.isEmpty(password)) {
			throw new BadCredentialsException("Empty fields.");
		}
		AdminEntity admin = adminRepository.findByEmail(authentication.getName());
		if (admin == null) {
			throw new BadCredentialsException("Admin does not exist.");
		}

		if (!PasswordUtil.passwordCheck(password, admin.getPassword())) {
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
