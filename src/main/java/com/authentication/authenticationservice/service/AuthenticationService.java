package com.authentication.authenticationservice.service;

import com.authentication.authenticationservice.dto.SignupPayload;
import com.authentication.authenticationservice.dto.SigninDto;
import com.authentication.authenticationservice.dto.SigninPayload;
import com.authentication.authenticationservice.model.RefreshToken;
import com.authentication.authenticationservice.model.User;

import java.util.Optional;

/**
 * @author Vifieh Ruth
 * on 5/27/22
 */

public interface AuthenticationService {

    void register(SignupPayload signupPayload);

    SigninDto signinUser(SigninPayload signinPayload);

    RefreshToken createRefreshToken(String userId);

    RefreshToken verifyExpiration(RefreshToken token);

    Optional<RefreshToken> findByToken(String token);

    User getUserById(String userId);

    void checkUsername(String username);

}