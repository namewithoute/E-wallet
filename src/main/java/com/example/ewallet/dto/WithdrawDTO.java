package com.example.ewallet.dto;

import lombok.*;
import org.springframework.stereotype.Component;

import java.sql.Date;

@Component
@Data
@NoArgsConstructor
@AllArgsConstructor
public class WithdrawDTO {
    private String creditCard;
    private Date expireDate;
    private String cvvCode;
    private Long amount;
    private String note;
}
