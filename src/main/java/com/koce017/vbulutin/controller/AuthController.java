package com.koce017.vbulutin.controller;

import com.koce017.vbulutin.data.dto.LoginDto;
import com.koce017.vbulutin.data.dto.RegisterDto;
import com.koce017.vbulutin.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping("/login")
    public String login(@RequestBody LoginDto loginDTO) {
        return authService.login(loginDTO);
    }

    @PostMapping("/register")
    @ResponseStatus(HttpStatus.CREATED)
    public void register(@RequestBody RegisterDto registerDTO) {
        authService.register(registerDTO);
    }

}
