package com.leminhtien.demoSpringJpaDynamic.auth.filter;

import com.leminhtien.demoSpringJpaDynamic.auth.details.CustomUserDetails;
import com.leminhtien.demoSpringJpaDynamic.auth.details.CustomUserDetailsService;
import com.leminhtien.demoSpringJpaDynamic.exception.CustomRuntimeException;
import com.leminhtien.demoSpringJpaDynamic.repository.TokenRepository;
import com.leminhtien.demoSpringJpaDynamic.service.JwtService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.NonNull;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    @Autowired
    private  CustomUserDetailsService userDetailsService;
    @Autowired
    private  JwtService jwtService;
    @Autowired
    private  TokenRepository tokenRepository;


    @Override
    protected void doFilterInternal(@NonNull HttpServletRequest request,@NonNull HttpServletResponse response,@NonNull FilterChain filterChain) throws ServletException, IOException {
        final String header = request.getHeader("Authorization");
        String token;
        String username;
        if (header==null || !header.startsWith("Bearer ")){
            filterChain.doFilter(request,response);
            return;
        }
        token = header.substring(7);
        try {
            username = jwtService.extractUsername(token);
        }catch (Exception e){
            throw new CustomRuntimeException("Access token is correct");
        }
        if (username!=null&& SecurityContextHolder.getContext().getAuthentication()==null){
            CustomUserDetails userDetails = (CustomUserDetails) userDetailsService.loadUserByUsername(username);
            boolean isValidToken = tokenRepository.findByAccessToken(token)
                    .map(t -> !t.isExpired() && !t.isRevoked() )
                    .orElse(false);
            if (isValidToken&& jwtService.isAccessTokenValid(token,userDetails)){
                UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(userDetails,null,userDetails.getAuthorities());
                authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                SecurityContextHolder.getContext().setAuthentication(authenticationToken);
            }
        }
        filterChain.doFilter(request,response);
    }
}
