package com.application.paymybuddy.model.DTO;

import lombok.*;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import java.io.Serializable;
import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserTransactionDTO implements Serializable {

    @NotNull
    private Long userDestinationId;

    @Positive(message = "veuillez saisir un nombre positif")
    @NotNull
    private BigDecimal amount;

    @NotBlank
    private String comments;
}
