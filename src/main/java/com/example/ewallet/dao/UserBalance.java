package com.example.ewallet.dao;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserBalance {
    @Id
    private String userName;
    @Column(columnDefinition = "BIGINT default 0.00",name = "balance")
    private Long balance;

    @OneToOne
    @MapsId
    @JoinColumn(name="user_name")
    private Account user_balance;

}
