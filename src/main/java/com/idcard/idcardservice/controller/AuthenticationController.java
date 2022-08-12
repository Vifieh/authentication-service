package com.idcard.idcardservice.controller;

import com.idcard.idcardservice.config.security.JwtUtils;
import com.idcard.idcardservice.dto.*;
import com.idcard.idcardservice.exception.TokenRefreshException;
import com.idcard.idcardservice.model.RefreshToken;
import com.idcard.idcardservice.service.serviceImpl.AuthenticationServiceImpl;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
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
