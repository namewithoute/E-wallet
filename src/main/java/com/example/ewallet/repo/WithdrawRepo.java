package com.example.ewallet.repo;

import com.example.ewallet.dao.WithdrawTrx;
import org.springframework.data.jpa.repository.JpaRepository;

public interface WithdrawRepo extends JpaRepository<WithdrawTrx,String> {
}
