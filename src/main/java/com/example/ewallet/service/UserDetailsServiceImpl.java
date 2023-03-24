package com.example.ewallet.service;

import com.example.ewallet.dao.Account;
import com.example.ewallet.dto.MyUserDetail;
import com.example.ewallet.repo.AccountRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private AccountRepo accountRepo;

    @Override
        public UserDetails loadUserByUsername(String username)
            throws UsernameNotFoundException {
        Account account = accountRepo.findByUserName(username);

        if (account == null) {
            throw new UsernameNotFoundException("Could not find user");
        }

        return new MyUserDetail(account);
    }

}
