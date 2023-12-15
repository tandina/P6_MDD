package com.mvp.P6mvpapi.dto;

import lombok.Data;

@Data
public class SigninRequest {
    private String usernameOrEmail;
    private String password;
}
