package com.example.ewallet.repo;

import com.example.ewallet.dao.TransferTrx;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TransferRepo extends JpaRepository<TransferTrx,String> {
}
