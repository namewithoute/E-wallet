package com.example.ewallet.repo;

import com.example.ewallet.dao.Account;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AccountRepo extends JpaRepository<Account,String> {
    Account findByUserNameAndPassword(String username,String password);
    Account findByUserName(String username);}
