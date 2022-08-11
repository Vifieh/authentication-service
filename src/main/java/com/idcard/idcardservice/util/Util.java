package com.idcard.idcardservice.util;

import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class Util {

    public String generateId() {
        UUID id = UUID.randomUUID();
        return id.toString();
    }

    public String generateToken() {
        UUID token = UUID.randomUUID();
        return token.toString();
    }
}