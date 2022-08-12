package com.idcard.idcardservice.service;


import com.idcard.idcardservice.model.User;

public interface UserService {

    User getAuthUser();

    User getUser(String userId);

}
