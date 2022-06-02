package com.application.paymybuddy.service;

import com.application.paymybuddy.model.UserTransaction;
import com.application.paymybuddy.repository.TransfertRepository;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;


import java.util.List;

@AllArgsConstructor
@Service
public class TransfertService {

    private TransfertRepository transfertRepository;

}
