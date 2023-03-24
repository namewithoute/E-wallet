package com.example.ewallet.controller;

import com.example.ewallet.dto.LoginDTO;
import com.example.ewallet.dao.Account;
import com.example.ewallet.repo.AccountRepo;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class LoginController {
    @Autowired
    LoginDTO account;
    @Autowired
    AccountRepo accountRepo;
    @GetMapping(value="/wallet-login")
    public String renderLoginPage(Model model,@RequestParam(name="err",required = false)String invalidErr){
        System.out.println(invalidErr);
        model.addAttribute("account",account);
        return "login-page";
    }

    @PostMapping("/login")
    public String postLoginPage(@ModelAttribute LoginDTO accountDTO , HttpSession session, @PathVariable("username")String username){
        System.out.println(username);
        Account account = accountRepo.findByUserNameAndPassword(accountDTO.getUsername(), accountDTO.getPassword());
        if(account==null){
            return "redirect:/login";
        }
        else{
            session.setAttribute("username",accountDTO.getUsername());
            return "redirect:/homepage";
        }
    }
    @GetMapping(value = "/admin/tesst123")
    public ResponseEntity test(){
        return ResponseEntity.ok("123");
    }
}
