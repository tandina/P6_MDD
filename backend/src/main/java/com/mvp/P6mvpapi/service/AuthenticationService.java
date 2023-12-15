package com.mvp.P6mvpapi.service;

import com.mvp.P6mvpapi.dto.JwtAuthenticationResponse;
import com.mvp.P6mvpapi.dto.SignUpRequest;
import com.mvp.P6mvpapi.dto.SigninRequest;
import com.mvp.P6mvpapi.models.User;

public interface AuthenticationService {
    User signup(SignUpRequest signUpRequest);
    JwtAuthenticationResponse signin(SigninRequest signinRequest);

}
