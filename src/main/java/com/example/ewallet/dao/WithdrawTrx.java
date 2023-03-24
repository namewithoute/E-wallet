package com.example.ewallet.dao;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class WithdrawTrx {
    @Id
    private String withdrawId;
    @OneToOne
    @MapsId
    @JoinColumn(name="withdraw_id")
    private Transaction withdraw;

    @Column(name = "user_name")
    private String userName;


    private String creditCard;

    @OneToOne
    @JoinColumn(name = "user_name",updatable = false,insertable = false)
    private Account account;

    private Long amount,fees;

}
