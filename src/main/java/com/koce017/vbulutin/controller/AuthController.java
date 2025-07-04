package com.koce017.vbulutin.controller;

import com.koce017.vbulutin.data.dto.LoginDto;
import com.koce017.vbulutin.data.dto.RegisterDto;
import com.koce017.vbulutin.error.ResponseException;
import com.koce017.vbulutin.service.AuthService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping("/login")
    public String login(@RequestBody @Valid LoginDto loginDTO) {
        return authService.login(loginDTO);
    }

    @PostMapping("/register")
    public ResponseEntity<String> register(@RequestBody @Valid RegisterDto registerDTO) {
        try {
            authService.register(registerDTO);
            return new ResponseEntity<>(HttpStatus.CREATED);
        } catch (ResponseException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.CONFLICT);
        }
    }

}
