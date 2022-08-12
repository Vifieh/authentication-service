package com.idcard.idcardservice.service.serviceImpl;

import com.idcard.idcardservice.exception.ResourceNotFoundException;
import com.idcard.idcardservice.model.User;
import com.idcard.idcardservice.repository.UserRepository;
import com.idcard.idcardservice.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    @Override
    public User getAuthUser() {
        Authentication authentication;
        authentication = SecurityContextHolder.getContext().getAuthentication();
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        return checkUsername(userDetails.getUsername());
    }

    @Override
    public User getUser(String userId) {
        return userRepository.findById(UUID.fromString(userId)).orElseThrow(() ->
               new ResourceNotFoundException("User not found with id - " + userId));
    }

    @Override
    public User checkUsername(String username) {
        return userRepository.findByUsername(username).
                orElseThrow(() -> new ResourceNotFoundException("User not found with name: "+ username));
    }

}
