package com.aaroo.security.config;

import java.util.HashSet;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.aaroo.security.model.LoginDetail;
import com.aaroo.security.model.UserDetail;
import com.aaroo.security.repository.LoginRepository;

@Service
public class JwtUserDetailsService implements UserDetailsService {
	@Autowired
	LoginRepository loginRepo;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

		UserDetail UserDetails = null;
		LoginDetail loginDetail = loginRepo.findByEmail(username);
		if (loginDetail == null) {
			throw new UsernameNotFoundException("Invalid username or password.");
		} else {
			return new org.springframework.security.core.userdetails.User(loginDetail.getEmail(),
					loginDetail.getPassword(), getAuthority(UserDetails));
		}
	}

	private Set<SimpleGrantedAuthority> getAuthority(UserDetail user) {
		Set<SimpleGrantedAuthority> authorities = new HashSet<>();
		authorities.add(new SimpleGrantedAuthority("ROLE_" + 1));
		return authorities;
	}
}