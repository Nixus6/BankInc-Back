package com.bankinc.credibanco.jwt;

import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.bankinc.credibanco.service.AuthService;

import org.springframework.util.StringUtils;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter{
	private static final Logger log=LoggerFactory.getLogger(JwtAuthenticationFilter.class);
	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {

		       
		        final String token = getTokenFromRequest(request);


		        if (token==null)
		        {
		            filterChain.doFilter(request, response);
		            return;
		        }
		        
		        filterChain.doFilter(request, response);
		    


		
	}
    private String getTokenFromRequest(HttpServletRequest request) {
    	
        String authHeader=request.getHeader(HttpHeaders.AUTHORIZATION);
        
        /*log.info("getTokenFromRequest "+authHeader);
        log.info("StringUtils.hasText(authHeader) "+StringUtils.hasText(authHeader));
        log.info("authHeader.startsWith(\"Bearer \") "+authHeader.startsWith("Bearer "));*/
        if(StringUtils.hasText(authHeader) && authHeader.startsWith("Bearer"))
        {
        	//log.info("authHeader.substring(7) "+authHeader.substring(7));
            return authHeader.substring(7);
        }
        return null;
    }
	

}
