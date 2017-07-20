package org.apache.playframework.security;

import java.util.Collection;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;

public class UserDetails extends User {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -8267189621309505598L;
	
	public UserDetails(String userId, String username, String password, boolean enabled, boolean accountNonExpired,
			boolean credentialsNonExpired, boolean accountNonLocked,
			Collection<? extends GrantedAuthority> authorities) {
		super(username, password, enabled, accountNonExpired, credentialsNonExpired, accountNonLocked, authorities);
		this.userId = userId;
	}
	
	public UserDetails(String username, String password, boolean enabled, boolean accountNonExpired,
			boolean credentialsNonExpired, boolean accountNonLocked,
			Collection<? extends GrantedAuthority> authorities) {
		super(username, password, enabled, accountNonExpired, credentialsNonExpired, accountNonLocked, authorities);
	}

	private String userId;
	
	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	/**
	 * 得到当前登录用户id
	 * @return
	 */
	public static String getCurrentUserId() {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		if (authentication != null) {
			Object principal = authentication.getPrincipal();
			if (principal instanceof UserDetails) {
				return ((UserDetails) principal).getUserId();
			}
		} 
		return null;
	}
	
	/**
	 * 得到当前登录用户名
	 * @return
	 */
	public static String getCurrentUserName() {
		Object principal = SecurityContextHolder.getContext().getAuthentication() .getPrincipal(); 
		if (principal instanceof UserDetails) {
			return ((UserDetails)principal).getUsername();
		} else {
			return principal.toString();
		}
	}

}
