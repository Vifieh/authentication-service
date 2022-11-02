package com.authentication.authenticationservice.service;


import com.authentication.authenticationservice.model.User;

public interface UserService {

    User getAuthUser();

    User getUser(String userId);

    User checkUsername(String username);

}
