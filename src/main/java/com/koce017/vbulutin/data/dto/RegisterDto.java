package com.koce017.vbulutin.data.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class RegisterDto {
    private String email;
    private String username;
    private String password;
}
