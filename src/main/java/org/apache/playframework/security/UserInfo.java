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

	public UserInfo(String userId, String realName, String username, String password, boolean enabled, boolean accountNonExpired,
					boolean credentialsNonExpired, boolean accountNonLocked,
					Collection<? extends GrantedAuthority> authorities) {
		super(username, password, enabled, accountNonExpired, credentialsNonExpired, accountNonLocked, authorities);
		this.userId = userId;
		this.realName = realName;
	}

	public UserInfo(String userId, String realName, String merchantId, String username, String password, boolean enabled, boolean accountNonExpired,
					boolean credentialsNonExpired, boolean accountNonLocked,
					Collection<? extends GrantedAuthority> authorities) {
		super(username, password, enabled, accountNonExpired, credentialsNonExpired, accountNonLocked, authorities);
		this.userId = userId;
		this.realName = realName;
		this.merchantId = merchantId;
	}

	public UserInfo(String username, String password, boolean enabled, boolean accountNonExpired,
					boolean credentialsNonExpired, boolean accountNonLocked,
					Collection<? extends GrantedAuthority> authorities) {
		super(username, password, enabled, accountNonExpired, credentialsNonExpired, accountNonLocked, authorities);
	}

	private String userId;

	private String realName;

	private String merchantId;

	private String mobile;
	
	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getRealName() {
		return realName;
	}

	public void setRealName(String realName) {
		this.realName = realName;
	}

	public String getMerchantId() {
		return merchantId;
	}

	public void setMerchantId(String merchantId) {
		this.merchantId = merchantId;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}
}
