package com.application.paymybuddy.model;

import lombok.*;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.validator.constraints.UniqueElements;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

@Data
@Entity
@Table(name = "user")
@DynamicUpdate
@NoArgsConstructor
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int userId;

    @Size(min = 2, max = 50)
    @NotEmpty
    @Column(name = "firstname",length = 50,nullable = false)
    private String firstName;

    @Size(min = 2, max = 50)
    @NotEmpty
    @Column(name = "lastname",length = 50,nullable = false)
    private String lastName;

    @Size(min = 2, max = 50)
    @NotEmpty
    @Column(name = "username", length = 50,nullable = false)
    private String userName;


    @Size(min = 6, max = 50)
    @NotEmpty
    @Email
    @Column(name = "email",unique = true,nullable = false)
    private String email;

    @Size(min = 8, max = 50)
    @NotEmpty
    @Column(name = "password",nullable = false)
    private String password;

    @Column(name = "balance")

    private double balance;

    @Override
    public String toString() {
        return "User{" +
                "userId=" + userId +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", userName='" + userName + '\'' +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                ", balance='" + balance + '\'' +
                '}';
    }
}
