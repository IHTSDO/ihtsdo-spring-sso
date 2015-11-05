package org.ihtsdo.example.ims;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;

public class ControllerHelper {

	public static UserDetails getUserDetails() {
		final SecurityContext context = SecurityContextHolder.getContext();
		if (context != null) {
			final Authentication authentication = context.getAuthentication();
			if (authentication != null) {
				return (UserDetails) authentication.getPrincipal();
			}
		}
		return null;
	}

	public static String getUsername() {
		final UserDetails userDetails = getUserDetails();
		if (userDetails != null) {
			return userDetails.getUsername();
		}
		return null;
	}
}
