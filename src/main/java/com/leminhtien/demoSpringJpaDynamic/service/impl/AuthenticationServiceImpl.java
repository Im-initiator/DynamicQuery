package com.leminhtien.demoSpringJpaDynamic.service.impl;

import com.leminhtien.demoSpringJpaDynamic.auth.details.CustomUserDetails;
import com.leminhtien.demoSpringJpaDynamic.auth.details.CustomUserDetailsService;
import com.leminhtien.demoSpringJpaDynamic.dto.ResponseDTO;
import com.leminhtien.demoSpringJpaDynamic.dto.request.CreateAccountByUserRequest;
import com.leminhtien.demoSpringJpaDynamic.dto.request.LoginRequest;
import com.leminhtien.demoSpringJpaDynamic.dto.response.LoginResponse;
import com.leminhtien.demoSpringJpaDynamic.entity.AccountEntity;
import com.leminhtien.demoSpringJpaDynamic.entity.RoleEntity;
import com.leminhtien.demoSpringJpaDynamic.entity.TokenEntity;
import com.leminhtien.demoSpringJpaDynamic.enums.RoleEnum;
import com.leminhtien.demoSpringJpaDynamic.enums.UserStatus;
import com.leminhtien.demoSpringJpaDynamic.repository.AccountRepository;
import com.leminhtien.demoSpringJpaDynamic.repository.RoleRepository;
import com.leminhtien.demoSpringJpaDynamic.repository.TokenRepository;
import com.leminhtien.demoSpringJpaDynamic.service.AuthenticationService;
import com.leminhtien.demoSpringJpaDynamic.service.JwtService;
import jakarta.transaction.Transactional;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.*;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.Set;

@RequiredArgsConstructor
@Service
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
@Slf4j
public class AuthenticationServiceImpl implements AuthenticationService {

    AuthenticationManager authenticationManager;
    AccountRepository accountRepository;
    PasswordEncoder passwordEncoder;
    RoleRepository roleRepository;
    JwtService jwtService;
    TokenRepository tokenRepository;
    CustomUserDetailsService userDetailsService;


    @Override
    @Transactional(rollbackOn = Exception.class)
    public ResponseDTO login(LoginRequest request) {
        if (request.getUsername() == null || request.getUsername().isBlank()) {
            return ResponseDTO.failure("User name not allow null");
        }
        if (request.getPassword() == null || request.getPassword().isBlank()) {
            return ResponseDTO.failure("Password not allow null");
        }
        UsernamePasswordAuthenticationToken authenticationToken =
                new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword());
        try {
            authenticationToken =(UsernamePasswordAuthenticationToken) authenticationManager.authenticate(authenticationToken);
        }catch (BadCredentialsException e){
            return ResponseDTO.failure("Username or password is correct");
        }catch (LockedException e){
            return ResponseDTO.failure("User is locked",403);
        }catch (DisabledException e){
            return ResponseDTO.failure("User is disable",403);
        }
        if (authenticationToken.isAuthenticated()){
            CustomUserDetails userDetails =(CustomUserDetails) authenticationToken.getPrincipal();
            String accessToken = jwtService.generateAccessToken(userDetails);
            String refreshToken = jwtService.generateRefreshToken(userDetails);
            TokenEntity token = new TokenEntity();
            token.setAccessToken(accessToken);
            token.setRefreshToken(refreshToken);
            token.setAccount(userDetails.getAccount());
            tokenRepository.save(token);
            LoginResponse response = LoginResponse.builder()
                    .accessToken(accessToken)
                    .refreshToken(refreshToken)
                    .build();
            return ResponseDTO.success("Login success",response);
        }
        return ResponseDTO.failure("Username or password is correct");
    }

    @Override
    public ResponseDTO register(CreateAccountByUserRequest request) {
//        if (request.getUsername() == null || request.getUsername().isBlank()) {
//            return ResponseDTO.failure("User name not allow null");
//        }
//        if (request.getPassword() == null || request.getPassword().isBlank()) {
//            return ResponseDTO.failure("Password not allow null");
//        }
//        RoleEntity roleEntity = roleRepository.findByName(RoleEnum.USER);
//        AccountEntity account = new AccountEntity();
//        account.setUsername(request.getUsername());
//        account.setPassword(passwordEncoder.encode(request.getPassword()));
//        account.setStatus(UserStatus.ACTIVE);
//        account.setRoles(Set.of(roleEntity));
//        accountRepository.save(account);
        return null;
    }

    @Override
    @Transactional
    public ResponseDTO getRefreshToken(String refreshToken) {
        if (refreshToken==null||refreshToken.isBlank()){
            return ResponseDTO.failure("Refresh token is correct");
        }
        String username = jwtService.extractUsername(refreshToken);
        if (username==null){
            return ResponseDTO.failure("Refresh token is correct");
        }
        CustomUserDetails userDetails =(CustomUserDetails) userDetailsService.loadUserByUsername(username);
        Optional<TokenEntity> oldToken = tokenRepository.findByRefreshToken(refreshToken);
        boolean isTokenValid = oldToken.map(token -> !token.isRevoked()&& !token.isExpired())
                .orElse(false);
        if (isTokenValid&& jwtService.isRefreshTokenValid(refreshToken,userDetails)){
            String accessToken = jwtService.generateAccessToken(userDetails);
            String refresh = jwtService.generateRefreshToken(userDetails);
            TokenEntity token = new TokenEntity();
            token.setAccessToken(accessToken);
            token.setRefreshToken(refreshToken);
            token.setAccount(userDetails.getAccount());
            tokenRepository.save(token);
            TokenEntity oldTokenEntity = oldToken.get();
            oldTokenEntity.setRevoked(true);
            oldTokenEntity.setExpired(true);
            tokenRepository.save(oldTokenEntity);
            LoginResponse response = LoginResponse.builder()
                    .accessToken(accessToken)
                    .refreshToken(refreshToken)
                    .build();
            return ResponseDTO.success("Get token success",response);
        }
        return ResponseDTO.failure("Refresh token is correct");
    }
}
