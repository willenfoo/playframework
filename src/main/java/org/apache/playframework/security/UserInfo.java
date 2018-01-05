package org.apache.playframework.security;

import java.util.Collection;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;

public class UserInfo extends User {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -8267189621309505598L;
	
	public UserInfo(String userId, String username, String password, boolean enabled, boolean accountNonExpired,
					boolean credentialsNonExpired, boolean accountNonLocked,
					Collection<? extends GrantedAuthority> authorities) {
		super(username, password, enabled, accountNonExpired, credentialsNonExpired, accountNonLocked, authorities);
		this.userId = userId;
	}
	
	public UserInfo(String username, String password, boolean enabled, boolean accountNonExpired,
					boolean credentialsNonExpired, boolean accountNonLocked,
					Collection<? extends GrantedAuthority> authorities) {
		super(username, password, enabled, accountNonExpired, credentialsNonExpired, accountNonLocked, authorities);
	}

	private String userId;
	
	public String getUserId() {
		return userId;
	}



}
