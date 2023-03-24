package com.example.ewallet.repo;

import com.example.ewallet.dao.CreditCard;
import org.springframework.data.jpa.repository.JpaRepository;

import java.sql.Date;

public interface CreditCardRepo  extends JpaRepository<CreditCard,String> {
     CreditCard findByCardNumberAndExpiredAtAndCvvCode(String creditCode, Date validDate, String cvv);
}
