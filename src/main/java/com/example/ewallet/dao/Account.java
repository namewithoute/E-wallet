package com.example.ewallet.dao;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.stereotype.Component;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Account   {
    @Id
    private String userName;
    private String password;
    private String role;
    @OneToOne(mappedBy = "account",cascade = CascadeType.ALL)
    @PrimaryKeyJoinColumn
    private UserInformation user;

    @OneToOne(mappedBy = "user_balance",cascade = CascadeType.ALL)
    @PrimaryKeyJoinColumn
    private UserBalance balance;

}
