//package com.application.paymybuddy.model;
//
//import lombok.AllArgsConstructor;
//import lombok.Getter;
//import lombok.NoArgsConstructor;
//import lombok.Setter;
//
//import javax.persistence.*;
//import java.math.BigDecimal;
//import java.time.LocalDateTime;
//
//@Getter
//@Setter
//@AllArgsConstructor
//@NoArgsConstructor
//@Entity
//@Table(name = "transactions_bank")
//public class BankTransaction {
//
//    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    @Column(name = "bank_transaction_id")
//    private Long bankTransactionId;
//
//    @ManyToOne(optional = false)
//    @JoinColumn(name = "user_id",nullable = false)
//    private User user;
//
//    private String bankAccountNumber;
//
//    private LocalDateTime datetime;
//
//    private BigDecimal amount;
//}
