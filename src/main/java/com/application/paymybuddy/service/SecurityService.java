package com.application.paymybuddy.service;

import com.application.paymybuddy.model.User;

public interface SecurityService {
    User loadUserByEmail(String email);

}
