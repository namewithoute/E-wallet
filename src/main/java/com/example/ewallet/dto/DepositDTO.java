package com.example.ewallet.dto;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

import java.sql.Date;
@Component
@Data
@NoArgsConstructor
@AllArgsConstructor
public class DepositDTO {
    private String creditCode;
    private Date expireDate;
    private String cvv;
    private Long amount;
}
