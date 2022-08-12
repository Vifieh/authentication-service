package com.idcard.idcardservice.service;

import com.idcard.idcardservice.dto.SigninDto;
import com.idcard.idcardservice.dto.SigninPayload;
import com.idcard.idcardservice.dto.SignupPayload;
import com.idcard.idcardservice.model.RefreshToken;
import com.idcard.idcardservice.model.User;

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