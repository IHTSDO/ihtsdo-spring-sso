package org.ihtsdo.sso.integration;

import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.preauth.PreAuthenticatedAuthenticationToken;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

public class RequestHeaderAuthenticationDecorator extends OncePerRequestFilter {

	private static final String USERNAME = "X-AUTH-username";
	private static final String ROLES = "X-AUTH-roles";
	private static final String TOKEN = "X-AUTH-token";

	@Override
	protected void doFilterInternal(final HttpServletRequest request, final HttpServletResponse response, final FilterChain filterChain) throws ServletException, IOException {
		Authentication authentication = SecurityUtil.getAuthentication();
		String authenticationToken = SecurityUtil.getAuthenticationToken();

		if (authenticationToken == null || !authenticationToken.equals(getToken(request))) {

			// Clear context and invalidate session (if available)
			SecurityContextHolder.clearContext();
			HttpSession session = request.getSession(false);
			if (session != null) {
				session.invalidate();
				request.getSession();
			}

			// Create new authentication with username, security token and authorities from request header
			final AbstractAuthenticationToken decoratedAuthentication = new PreAuthenticatedAuthenticationToken(
					getUsername(request),
					getToken(request),
					AuthorityUtils.commaSeparatedStringToAuthorityList(getRoles(request)));

			// Pass through existing authentication details
            assert authentication != null;
            decoratedAuthentication.setDetails(authentication.getDetails());

			SecurityContextHolder.getContext().setAuthentication(decoratedAuthentication);
		}

		filterChain.doFilter(request, response);
	}

	@Override
	protected boolean shouldNotFilter(final HttpServletRequest request) {
		Authentication authentication = SecurityUtil.getAuthentication();
		return authentication == null || !authentication.isAuthenticated() || getUsername(request) == null || getRoles(request) == null;
	}

	protected String getUsername(final HttpServletRequest request) {
		return request.getHeader(USERNAME);
	}

	protected String getRoles(final HttpServletRequest request) {
		return request.getHeader(ROLES);
	}

	protected String getToken(final HttpServletRequest request) {
		return request.getHeader(TOKEN);
	}
}
