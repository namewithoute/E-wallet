package com.example.ewallet.controller;

import com.example.ewallet.dto.MyUserDetail;
import com.example.ewallet.repo.AccountRepo;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.security.Principal;

@Controller
public class HomepageController {
    @Autowired
    AccountRepo accountRepo;
    @GetMapping(value = {"/","/homepage"})
    public String renderHomepage(HttpSession session, Model model, Principal principal, Authentication auth){
        System.out.println(principal.getName());
        MyUserDetail account = (MyUserDetail) auth.getPrincipal();
        System.out.println(account.getAuthorities());
        return "homepage";
    }
}
