package com.example.ewallet.repo;

import com.example.ewallet.dao.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TransactionRepo extends JpaRepository<Transaction,String> {
}
