package com.example.ewallet.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Component
public class TransferDTO {
    private String receiver;
    private Long amount;
    private String note;
    private String payerOption;
}
