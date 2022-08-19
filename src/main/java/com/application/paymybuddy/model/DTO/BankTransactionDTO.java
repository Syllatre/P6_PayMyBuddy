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

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class BankTransactionDTO implements Serializable {


    @NotBlank
    private String bankAccountNumber;

    @Positive(message = "veuillez saisir un nombre positif")
    @NotNull
    private BigDecimal amount;
}
