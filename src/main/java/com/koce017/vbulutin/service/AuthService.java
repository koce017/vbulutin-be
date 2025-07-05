package com.koce017.vbulutin.service;

import com.koce017.vbulutin.data.dto.LoginDto;
import com.koce017.vbulutin.data.dto.RegisterDto;

public interface AuthService {
    String login(LoginDto loginDTO);
    void register(RegisterDto registerDTO);
}
