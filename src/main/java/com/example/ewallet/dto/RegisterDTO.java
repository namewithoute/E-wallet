package com.example.ewallet.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.sql.Date;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Component
public class RegisterDTO {
    private String email,fullName,phone,address;
    private MultipartFile idFront,idBack;
    private int gender;
    private Date dob;
}
