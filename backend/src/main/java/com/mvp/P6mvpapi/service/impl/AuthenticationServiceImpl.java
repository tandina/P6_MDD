package com.mvp.P6mvpapi.service.impl;

import com.mvp.P6mvpapi.dto.JwtAuthenticationResponse;
import com.mvp.P6mvpapi.dto.SignUpRequest;
import com.mvp.P6mvpapi.dto.SigninRequest;
import com.mvp.P6mvpapi.models.Role;
import com.mvp.P6mvpapi.models.User;
import com.mvp.P6mvpapi.repository.UserRepository;
import com.mvp.P6mvpapi.service.AuthenticationService;
import com.mvp.P6mvpapi.service.JWTService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.util.HashMap;

@Service
@RequiredArgsConstructor

public class AuthenticationServiceImpl implements AuthenticationService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final JWTService jwtService;
    private static final Logger logger = LoggerFactory.getLogger(AuthenticationServiceImpl.class);

    public User signup (SignUpRequest signupRequest){
        User user = new User();

        user.setEmail(signupRequest.getEmail());
        user.setUsername(signupRequest.getUsername());
        user.setRole(Role.USER);
        user.setPassword(passwordEncoder.encode(signupRequest.getPassword()));

        return userRepository.save(user);
    }
    public JwtAuthenticationResponse signin(SigninRequest signinRequest){

User user = userRepository.findByUsernameOrEmail(signinRequest.getUsernameOrEmail())
        .orElseThrow(() -> new UsernameNotFoundException("User not found with username or email : " + signinRequest.getUsernameOrEmail()));

    logger.info("User found: {}", user);

    authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(user.getUsername(), signinRequest.getPassword()));

    var jwt = jwtService.generateToken(user);
    JwtAuthenticationResponse jwtAuthenticationResponse= new JwtAuthenticationResponse();

    jwtAuthenticationResponse.setToken(jwt);

    return jwtAuthenticationResponse;
        }
}