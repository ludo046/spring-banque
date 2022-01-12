package fr.diginamic.tpjpa05.providers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;

import fr.diginamic.tpjpa05.services.UserService;

public class AppAuthProvider extends DaoAuthenticationProvider{
	
	@Autowired
	UserService userDetailService;
	
	@Override
	public Authentication authenticate(Authentication authentication) throws AuthenticationException {
		// TODO Auto-generated method stub
		UsernamePasswordAuthenticationToken auth = (UsernamePasswordAuthenticationToken)authentication;
		
		String name = auth.getName();
		String password = auth.getCredentials().toString();
		
		UserDetails user = userDetailService.loadUserByUsername(name);
		
		if(user == null) {
			throw new BadCredentialsException("Username/password does not match for" + auth.getPrincipal());
		}
		if(!user.getPassword().equals(password)) {
			throw new BadCredentialsException("Username/password does not match for" + auth.getPrincipal());
		}
		
		//retourne le user detail + ses roles
		return new UsernamePasswordAuthenticationToken(user, null, user.getAuthorities());
		
	}
	
	@Override
	public boolean supports(Class<?> authentication) {
		// TODO Auto-generated method stub
		return true;
	}

}
