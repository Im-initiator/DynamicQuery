package com.leminhtien.demoSpringJpaDynamic.controller;

import com.leminhtien.demoSpringJpaDynamic.dto.ResponseDTO;
import com.leminhtien.demoSpringJpaDynamic.dto.request.LoginRequest;
import com.leminhtien.demoSpringJpaDynamic.service.AuthenticationService;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequiredArgsConstructor
public class AuthenticationController {
    private final AuthenticationService authenticationService;


    @PostMapping("/login")
    public ResponseEntity<ResponseDTO> login(@RequestBody LoginRequest request){
        return ResponseEntity.ok( authenticationService.login(request));
    }

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody LoginRequest request){
        authenticationService.login(request);
        return new ResponseEntity<>("OK", HttpStatus.OK);
    }

    @PostMapping("/refresh-token")
    public ResponseEntity<ResponseDTO> refreshToken(@RequestBody Map<String,Object> request){
        return ResponseEntity.ok(authenticationService.getRefreshToken((String) request.get("refreshToken")));
    }


    @GetMapping("/authenticated")
    public ResponseEntity<String> authenticated(){
        return new ResponseEntity<>("OK", HttpStatus.OK);
    }
}
