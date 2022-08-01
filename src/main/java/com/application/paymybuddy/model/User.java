package com.application.paymybuddy.model;

import lombok.*;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
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
    @Column(name = "user_id", nullable = false)
    private Long userId;

    @NotEmpty
    @Column(name = "firstname", nullable = false, length = 50)
    private String firstName;

    @NotEmpty
    @Column(name = "lastname", nullable = false, length = 50)
    private String lastName;

    @NotEmpty
    @Column(name = "username", unique = true, nullable = false, length = 50)
    private String userName;

    @NotEmpty
    @Email
    @Column(name = "email", unique = true, nullable = false, length = 100)
    private String email;

    @NotEmpty
    @Column(name = "password", nullable = false, length = 255)
    private String password;
    @NotNull
    @Column(name = "balance", nullable = false)
    private BigDecimal balance;

    //utilisation de joinTable, https://qastack.fr/programming/5478328/in-which-case-do-you-use-the-jpa-jointable-annotation
    @ManyToMany
    @JoinTable(name = "user_connection",
            joinColumns = @JoinColumn(name = "user_source_id",
                    referencedColumnName = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "user_destination_id",
                    referencedColumnName = "user_id"))
    private Set<User> connections;

    @OneToMany(mappedBy = "user")
    private Set<BankTransaction> bankTransactions;

    @OneToMany(mappedBy = "userSource")
    private Set<UserTransaction> userTransactions;

    @NotNull
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "user_roles",
            joinColumns = @JoinColumn(name = "user_id",
                    referencedColumnName = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id",
                    referencedColumnName = "role_id"))
    Set<Role> roles;

    @NotNull
    private boolean active;

    public User(String firstname, String lastname, String userName, String email, String password, BigDecimal balance, Set<Role> roles, boolean active) {
        this.firstName = firstname;
        this.lastName = lastname;
        this.userName = userName;
        this.email = email;
        this.password = password;
        this.balance = balance;
        this.roles = roles;
        this.active = active;
    }
}