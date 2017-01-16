package org.ihtsdo.example.ims;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;

public class ControllerHelper {

	public static String getUsername() {
		final Authentication authentication = getAuthentication();
		if (authentication != null) {
			return (String) authentication.getPrincipal();
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
