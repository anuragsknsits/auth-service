package com.codewithanurag.authentication.config;

import com.codewithanurag.authentication.util.JWTUtil;
import io.jsonwebtoken.ExpiredJwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Arrays;

@Component
public class JwtFilter extends OncePerRequestFilter {
    private final UserDetailsService userDetailService;
    private final JWTUtil jwtUtil;

    public JwtFilter(UserDetailsService userDetailService, JWTUtil jwtUtil) {
        this.userDetailService = userDetailService;
        this.jwtUtil = jwtUtil;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {
        final String authorizationHeader = request.getHeader(HttpHeaders.AUTHORIZATION);
        try {
            String jwt = authorizationHeader != null && authorizationHeader.startsWith("Bearer ") ?
                    authorizationHeader.substring(7) :
                    (request.getCookies() != null ?
                            Arrays.stream(request.getCookies())
                                    .filter(cookie -> "authToken".equals(cookie.getName()))
                                    .findFirst().map(Cookie::getValue).orElse(null) : null);

            String userName = null;
            if (jwt != null) {
                userName = jwtUtil.extractUserName(jwt);
            }

            if (userName != null && SecurityContextHolder.getContext().getAuthentication() == null) {
                UserDetails userDetails = userDetailService.loadUserByUsername(userName);
                if (jwtUtil.validateToken(jwt, userDetails)) {
                    UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
                            userDetails, null, userDetails.getAuthorities()
                    );
                    authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                    SecurityContextHolder.getContext().setAuthentication(authenticationToken);
                }
            }
            filterChain.doFilter(request, response);
        } catch (ExpiredJwtException ex) {
            response.setStatus(HttpStatus.UNAUTHORIZED.value());
            response.getWriter().write("Token expired");
            response.getWriter().flush();
        }
    }
}
