package com.example.ewallet.dao;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.DynamicUpdate;

import java.sql.Date;

@Entity
@AllArgsConstructor
@DynamicUpdate
@NoArgsConstructor
@Data
public class UserInformation extends Auditable {
    @Id
    private String userName;
    private String address, fullName;
    @Column(unique = true)
    private String phone;
    @Column(unique = true)
    private String email;
    private int gender;
    private Date dob;
    @Column(columnDefinition = "integer default 1")
    private int accountStatus;
    @Column(columnDefinition = "boolean default false")
    private boolean isUpdatePassword;


    @OneToOne
    @MapsId
    @JoinColumn(name="user_name")
    @JsonIgnore
    private Account account;

}
