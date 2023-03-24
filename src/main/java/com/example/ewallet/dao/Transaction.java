package com.example.ewallet.dao;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Transaction extends Auditable {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;
    private String transactionType;
    private String transactionStatus;
    @OneToOne(mappedBy = "transfer", cascade = CascadeType.ALL)
    @PrimaryKeyJoinColumn
    private TransferTrx transferTrx;

    @OneToOne(mappedBy = "withdraw", cascade = CascadeType.ALL)
    @PrimaryKeyJoinColumn
    private WithdrawTrx withdrawTrx;
}
