package com.example.ewallet.dao;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Data;

import java.sql.Date;

@Entity
@Data
public class CreditCard {
    @Id
    private String cardNumber;
    private String cvvCode;
    private Date expiredAt;
}

