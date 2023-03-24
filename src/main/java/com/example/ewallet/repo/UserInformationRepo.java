package com.example.ewallet.repo;

import com.example.ewallet.dao.UserInformation;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserInformationRepo extends JpaRepository<UserInformation,String> {
    UserInformation findByPhone(String phone);
    UserInformation findByUserName(String username);
}
