package com.application.paymybuddy.model.DTO;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import java.io.Serializable;
import java.math.BigDecimal;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class BankTransactionDTO implements Serializable {


    @NotBlank
    private String bankAccountNumber;

    @Positive
    @NotNull
    private BigDecimal amount;
}
