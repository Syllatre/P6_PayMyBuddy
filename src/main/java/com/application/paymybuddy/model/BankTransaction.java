package com.application.paymybuddy.model;

import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "transactions_bank")
public class BankTransaction {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "bank_transaction_id")
    private Long bankTransactionId;

    @ManyToOne(optional = false)
    @JoinColumn(name = "user_id",nullable = false)
    private User user;


    @Column(name = "bank_account_number",length = 20)
    private String bankAccountNumber;

    @NotNull
    @Column(name = "datetime")
    private LocalDateTime datetime;

    private BigDecimal amount;
}
