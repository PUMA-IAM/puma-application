package puma.application.webapp.users;

import java.util.LinkedList;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;

public class CustomUserDetails extends User {
	
	public CustomUserDetails(String username, String password) {
		super(username, password, true, true, true, true, new LinkedList<GrantedAuthority>());
	}

}
