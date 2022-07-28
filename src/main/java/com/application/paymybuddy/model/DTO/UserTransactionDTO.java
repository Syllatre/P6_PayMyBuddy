package com.application.paymybuddy.model.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import java.io.Serializable;
import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserTransactionDTO implements Serializable {

    @NotNull
    private Long userDestinationId;

    @Positive
    @NotNull
    private BigDecimal amount;

    @NotBlank
    private String comments;
}
