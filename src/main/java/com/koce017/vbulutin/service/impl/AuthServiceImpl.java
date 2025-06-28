package com.koce017.vbulutin.service.impl;

import com.koce017.vbulutin.data.dto.LoginDto;
import com.koce017.vbulutin.data.dto.RegisterDto;
import com.koce017.vbulutin.data.entity.User;
import com.koce017.vbulutin.error.ResponseException;
import com.koce017.vbulutin.repository.UserRepository;
import com.koce017.vbulutin.service.AuthService;
import com.nimbusds.jose.jwk.source.ImmutableSecret;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.jose.jws.MacAlgorithm;
import org.springframework.security.oauth2.jwt.*;
import org.springframework.stereotype.Service;

import java.time.Instant;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    @Value("${security.jwt.secret}")
    private String secret;

    @Value("${security.jwt.duration}")
    private Integer duration;

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;

    @Override
    public String login(LoginDto loginDto) {
        User user = userRepository.findByUsername(loginDto.getUsername())
                .orElseThrow(() -> new BadCredentialsException("Invalid username"));

        if (!passwordEncoder.matches(loginDto.getPassword(), user.getPassword())) {
            throw new BadCredentialsException("Invalid password");
        }

        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                loginDto.getUsername(), loginDto.getPassword()
        ));

        Instant now = Instant.now();

        JwtClaimsSet claims = JwtClaimsSet.builder()
                .issuedAt(now)
                .issuer("vBulutin")
                .subject(loginDto.getUsername())
                .expiresAt(now.plusSeconds(duration))
                .build();

        JwtEncoder encoder = new NimbusJwtEncoder((new ImmutableSecret<>(secret.getBytes())));
        JwtEncoderParameters parameters = JwtEncoderParameters.from(JwsHeader.with(MacAlgorithm.HS256).build(), claims);

        return encoder.encode(parameters).getTokenValue();
    }

    @Override
    public void register(RegisterDto registerDto) throws ResponseException {
        if (userRepository.findByUsername(registerDto.getUsername()).isPresent()) {
            throw new ResponseException("Username is already taken!");
        }
        if (userRepository.findByEmail(registerDto.getEmail()).isPresent()) {
            throw new ResponseException("Email is already taken!");
        }
        User user = User.builder()
                .email(registerDto.getEmail())
                .username(registerDto.getUsername())
                .password(passwordEncoder.encode(registerDto.getPassword()))
                .build();
        userRepository.save(user);
    }

}
