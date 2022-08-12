package com.idcard.idcardservice.service.serviceImpl;

import com.idcard.idcardservice.config.security.JwtUtils;
import com.idcard.idcardservice.constants.ERole;
import com.idcard.idcardservice.dto.SigninDto;
import com.idcard.idcardservice.dto.SigninPayload;
import com.idcard.idcardservice.dto.SignupPayload;
import com.idcard.idcardservice.exception.BadRequestException;
import com.idcard.idcardservice.exception.ResourceNotFoundException;
import com.idcard.idcardservice.exception.TokenRefreshException;
import com.idcard.idcardservice.model.RefreshToken;
import com.idcard.idcardservice.model.Role;
import com.idcard.idcardservice.model.User;
import com.idcard.idcardservice.repository.RefreshTokenRepository;
import com.idcard.idcardservice.repository.UserRepository;
import com.idcard.idcardservice.service.AuthenticationService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;


@Service
@RequiredArgsConstructor
public class AuthenticationServiceImpl implements AuthenticationService {

    BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
    private final UserRepository userRepository;
    private final RefreshTokenRepository refreshTokenRepository;
    private final RoleServiceImpl roleService;
    private final AuthenticationManager authenticationManager;
    private final JwtUtils jwtUtils;
    private final UserDetailsServiceImpl userDetailsService;


    @Value("${attendance.app.jwtRefreshExpirationMs}")
    private Long refreshTokenDurationMs;


    @Override
    public void register(SignupPayload signupPayload) {
        User user = new User();
        List<Role> roleList = new ArrayList<>();
        checkUsername(signupPayload.getUsername());
        user.setUsername(signupPayload.getUsername());
        user.setEmail(signupPayload.getEmail());
        user.setPassword(passwordEncoder.encode(signupPayload.getPassword()));
        if (signupPayload.getRole().toString().equals("ROLE_STUDENT")) {
            roleList.add(roleService.getRoleByName(ERole.ROLE_USER));
        }
        user.setRoles(roleList);
        userRepository.save(user);
    }


    @Override
    public SigninDto signinUser(SigninPayload signinPayload) {
        userDetailsService.loadUserByUsername(signinPayload.getUsername());

        Authentication authentication;
        try{
            authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(signinPayload.getUsername(), signinPayload.getPassword()));
        }catch (BadCredentialsException ex) {
            throw new BadRequestException("Invalid email or password");
        }
        SecurityContextHolder.getContext().setAuthentication(authentication);
        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
        String jwt = jwtUtils.generateJwtToken(authentication);
        List<String> roles = userDetails.getAuthorities().stream().map(GrantedAuthority::getAuthority)
                .collect(Collectors.toList());
        RefreshToken refreshToken = createRefreshToken(userDetails.getUser().getId().toString());

        return new SigninDto(jwt, refreshToken.getToken(), refreshToken.getExpiryDate(), userDetails.getUser().getId().toString(),
                userDetails.getUsername(), userDetails.getUser().getEmail(), roles);
    }

    @Override
    public RefreshToken verifyExpiration(RefreshToken token) {
        if (token.getExpiryDate().compareTo(Instant.now()) < 0) {
            refreshTokenRepository.delete(token);
            throw new TokenRefreshException(token.getToken(), "Refresh token was expired. Please make a new signin request");
        }
        return token;
    }

    @Override
    public RefreshToken createRefreshToken(String userId) {
        RefreshToken refreshToken = new RefreshToken();

        refreshToken.setUser(userRepository.findById(UUID.fromString(userId)).get());
        refreshToken.setExpiryDate(Instant.now().plusMillis(refreshTokenDurationMs));
        refreshToken.setToken(UUID.randomUUID().toString());

        refreshToken = refreshTokenRepository.save(refreshToken);
        return refreshToken;
    }

    @Override
    public Optional<RefreshToken> findByToken(String token) {
        return refreshTokenRepository.findByToken(token);
    }

    @Override
    public User getUserById(String userId) {
        Optional<User> optionalUser = userRepository.findById(UUID.fromString(userId));
        if(optionalUser.isEmpty()){
            throw new ResourceNotFoundException("User not found with id - "+ userId);
        }
        return optionalUser.get();
    }

    @Override
    public void checkUsername(String username) {
        Optional<User> user1 = userRepository.findByUsername(username);
        if (user1.isPresent()) {
            throw new ResourceNotFoundException("User already exist with name: " + username);
        }
    }
}
