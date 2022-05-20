package com.application.paymybuddy.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Set;

@Entity
@Table(name = "user")
@DynamicUpdate
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id",nullable = false)
    private Long userId;

    @NotBlank
    @Column(name = "firstname",nullable = false,length = 50)
    private String firstName;

    @NotBlank
    @Column(name = "lastname",nullable = false,length = 50)
    private String lastName;

    @NotBlank
    @Column(name = "username",nullable = false,length = 50)
    private String userName;

    @NotBlank
    @Email
    @Column(name = "email",unique = true,nullable = false)
    private String email;

    @NotEmpty
    @Column(name = "password",nullable = false)
    private String password;

    @Column(name = "balance",nullable = false)
    private BigDecimal balance;

    //utilisation de joinTable, https://qastack.fr/programming/5478328/in-which-case-do-you-use-the-jpa-jointable-annotation
    @ManyToMany
    @JoinTable(name = "user_connection",
    joinColumns = @JoinColumn(name ="user_id",
            referencedColumnName = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "connection_id",
            referencedColumnName = "user_id"))
    private Set<User> connections;

    @OneToMany(mappedBy = "user")
    private Set<BankTransaction> bankTransactions;

    @OneToMany(mappedBy = "userSource")
    private Set<UserTransaction> userTransactions;

}
