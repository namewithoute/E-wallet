package com.example.ewallet.repo;

import com.example.ewallet.dao.UserBalance;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

public interface UserBalanceRepo extends JpaRepository<UserBalance,String> {
    @Modifying
    @Query("update UserBalance u set u.balance = u.balance + :balance where u.userName = :username")
    void updateBalanceIncrease(@Param(value = "balance") long balance, @Param(value = "username") String username);
    @Modifying
    @Transactional
    @Query("update UserBalance u set u.balance = u.balance - :balance where u.userName = :username")
    void updateBalanceDecrease(@Param(value = "balance") long balance, @Param(value = "username") String username);

}
