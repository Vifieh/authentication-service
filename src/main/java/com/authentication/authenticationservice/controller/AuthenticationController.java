package com.authentication.authenticationservice.controller;

import com.authentication.authenticationservice.dto.*;
import com.authentication.authenticationservice.model.RefreshToken;
import com.authentication.authenticationservice.service.serviceImpl.AuthenticationServiceImpl;
import com.authentication.authenticationservice.config.security.JwtUtils;
import com.authentication.authenticationservice.exception.TokenRefreshException;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RequestMapping("/api/v1/public/auth/")
@RestController
@CrossOrigin()
@AllArgsConstructor
public class AuthenticationController {

    private final AuthenticationServiceImpl authenticationService;
    private final JwtUtils jwtUtils;


    @PostMapping("register")
    public ResponseEntity<ResponseMessage> register(@Valid @RequestBody SignupPayload signupPayload) {
        authenticationService.register(signupPayload);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("login")
    public ResponseEntity<SigninDto> login(@Valid @RequestBody SigninPayload signinPayload) {
        SigninDto signinDto = authenticationService.signinUser(signinPayload);
        return new ResponseEntity<>(signinDto, HttpStatus.CREATED);
    }

    @PostMapping("refreshToken")
    public ResponseEntity<?> refreshToken(@Valid @RequestBody TokenRefreshPayload request) {
        String requestRefreshToken = request.getRefreshToken();
        return authenticationService.findByToken(requestRefreshToken).map(authenticationService::verifyExpiration)
                .map(RefreshToken::getUser).map(user -> {
                    String token = jwtUtils.generateTokenFromUsername(user.getUsername());
                    return ResponseEntity.ok(new TokenRefreshDto(token, requestRefreshToken));
                })
                .orElseThrow(() -> new TokenRefreshException(requestRefreshToken, "Refresh token is not in database!"));
    }
}
