package com.leminhtien.demoSpringJpaDynamic.auth.handler;

import com.leminhtien.demoSpringJpaDynamic.entity.TokenEntity;
import com.leminhtien.demoSpringJpaDynamic.repository.TokenRepository;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.LogoutHandler;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class CustomLogoutHandler implements LogoutHandler {

    private final TokenRepository tokenRepository;

    @Override
    public void logout(HttpServletRequest request, HttpServletResponse response, Authentication authentication) {
        final String auth = request.getHeader("Authorization");
        String jwt;
        if (auth==null || auth.startsWith("Bearer ")){
            return;
        }

        jwt = auth.substring(7);
        TokenEntity token = tokenRepository.findByAccessToken(jwt).orElse(null);
        if (token!=null){
            token.setExpired(true);
            token.setRevoked(true);
            tokenRepository.save(token);
        }
    }
}
