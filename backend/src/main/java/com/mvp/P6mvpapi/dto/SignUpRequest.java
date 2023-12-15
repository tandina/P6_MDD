package com.mvp.P6mvpapi.dto;

import lombok.Data;

@Data
public class SignUpRequest {
    private String email;
    private String username;
    private String password;
}
