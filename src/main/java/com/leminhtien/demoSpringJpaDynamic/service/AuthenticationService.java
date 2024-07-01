package com.leminhtien.demoSpringJpaDynamic.service;

import com.leminhtien.demoSpringJpaDynamic.dto.ResponseDTO;
import com.leminhtien.demoSpringJpaDynamic.dto.request.CreateAccountByUserRequest;
import com.leminhtien.demoSpringJpaDynamic.dto.request.LoginRequest;

public interface AuthenticationService {
    ResponseDTO login(LoginRequest request);
    ResponseDTO register(CreateAccountByUserRequest request);
    ResponseDTO getRefreshToken(String refreshToken);
}
