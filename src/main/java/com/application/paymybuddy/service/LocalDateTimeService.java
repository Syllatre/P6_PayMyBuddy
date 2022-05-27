package com.application.paymybuddy.service;

import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class LocalDateTimeService {
    public LocalDateTime now() {
        return LocalDateTime.now();
    }
}
