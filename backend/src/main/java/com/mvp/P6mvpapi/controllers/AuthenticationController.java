package com.mvp.P6mvpapi.controllers;

import com.mvp.P6mvpapi.dto.JwtAuthenticationResponse;
import com.mvp.P6mvpapi.dto.SignUpRequest;
import com.mvp.P6mvpapi.dto.SigninRequest;
import com.mvp.P6mvpapi.models.User;
import com.mvp.P6mvpapi.service.AuthenticationService;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthenticationController {
    private final AuthenticationService authenticationService;

    @PostMapping("/signup")
    public ResponseEntity<?> signup(@RequestBody SignUpRequest signUpRequest){
        try {
            User user = authenticationService.signup(signUpRequest);
            return ResponseEntity.ok(user);
        } catch (DataIntegrityViolationException e) {
            return new ResponseEntity<>("Un compte avec cet email existe déjà.", HttpStatus.BAD_REQUEST);
        }
    }
    @PostMapping("/signing")
    public ResponseEntity<JwtAuthenticationResponse> signing(@RequestBody SigninRequest signinRequest){
        return ResponseEntity.ok(authenticationService.signin(signinRequest));
    }
}
