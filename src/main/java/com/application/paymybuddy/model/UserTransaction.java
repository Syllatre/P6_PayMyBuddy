package com.application.paymybuddy.model;

import lombok.*;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
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
    @JoinColumn(name = "user_source_id",nullable = false)
    private User userSource;

    @ManyToOne(optional = false)
    @JoinColumn(name = "user_destination_id")
    private User userDestination;

    private LocalDateTime dateUserTransaction;

    private String comments;

    private BigDecimal amount;

    private BigDecimal fees;

}
