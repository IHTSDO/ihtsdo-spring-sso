package org.ihtsdo.sso.integration;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;

public class SecurityUtil {

	public static String getUsername() {
		final Authentication authentication = getAuthentication();
		if (authentication != null) {
			Object obj = authentication.getPrincipal();
			if (obj instanceof User) {
				((User)obj).getUsername();
			} else {
				return (String) obj ;
			}
		}
		return null;
	}

	public static String getAuthenticationToken() {
		final Authentication authentication = getAuthentication();
		if (authentication != null) {
			return (String) authentication.getCredentials();
		}
		return null;
	}

	private static Authentication getAuthentication() {
		final SecurityContext context = SecurityContextHolder.getContext();
		if (context != null) {
			return context.getAuthentication();
		}
		return null;
	}

}
