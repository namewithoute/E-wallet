package com.example.ewallet.dao;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class TransferTrx {
    @Id
    private String transferId;

    @OneToOne
    @MapsId
    @JoinColumn(name = "transfer_id")
    private Transaction transfer;

    @Column(name="from_account")
    private String fromAccount;
    @Column(name="to_account")
    private String toAccount;

    @OneToOne
    @JoinColumn(name="from_account",insertable = false,updatable = false)
    private Account from;

    @OneToOne
    @JoinColumn(name="to_account",insertable = false,updatable = false)
    private Account to;

    private Long amount;
    private Long transferFees;
    private String payerOption;
    private String note;
}
