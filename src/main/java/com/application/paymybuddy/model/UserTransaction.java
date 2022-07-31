package com.application.paymybuddy.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "user_transaction")
public class UserTransaction {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_transaction_id")
    private Long userTransactionId;

    @ManyToOne(optional = false)
    @JoinColumn(name = "user_source_id", nullable = false)
    private User userSource;

    @ManyToOne(optional = false)
    @JoinColumn(name = "user_destination_id")
    private User userDestination;


    @Column(name = "date_user_transaction")
    private LocalDateTime dateUserTransaction;

    @NotBlank
    @Column(name = "comments", length = 200)
    private String comments;


    @Column(name = "amount")
    private BigDecimal amount;


    @Column(name = "fees")
    private BigDecimal fees;

}
