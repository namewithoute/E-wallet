package com.example.ewallet.controller;

import com.example.ewallet.dao.UserBalance;
import com.example.ewallet.dto.RegisterDTO;
import com.example.ewallet.dao.Account;
import com.example.ewallet.dao.UserInformation;
import com.example.ewallet.repo.AccountRepo;
import com.example.ewallet.repo.UserBalanceRepo;
import com.example.ewallet.repo.UserInformationRepo;
import com.example.ewallet.service.SendMailService;
import jakarta.servlet.ServletContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;

@Controller
public class RegisterController {
    @Autowired
    AccountRepo accountRepo;
    @Autowired
    UserBalanceRepo userBalanceRepo;

    @Autowired
    BCryptPasswordEncoder passwordEncoder;
    @Autowired
    RegisterDTO registerDTO;
    @Autowired
    UserInformationRepo userInformationRepo;
    @Autowired
    ServletContext app;
    @Autowired
    SendMailService sendMailService;
    @GetMapping("/register")
    public String renderRegisterForm(Model model){
        model.addAttribute("register",registerDTO);
        return "register";
    }

    @PostMapping("/register")
    @Transactional
    public String postForm(@ModelAttribute RegisterDTO registerDTO, Model model, RedirectAttributes redirectAttributes){
        String uploadRootPath = app.getRealPath("/img/id-cart");
        System.out.println(registerDTO);
        File uploadRootDir= new File(uploadRootPath);
        System.out.println(uploadRootPath);
        if(!uploadRootDir.exists()){
            uploadRootDir.mkdirs();
        }
        try {
            String imgIdFront = registerDTO.getIdFront().getOriginalFilename();
            String imgIdBack = registerDTO.getIdBack().getOriginalFilename();
            File serverImgFront = new File(uploadRootDir.getAbsoluteFile() + File.separator + imgIdFront);
            File serverImgBack = new File(uploadRootDir.getAbsoluteFile() + File.separator + imgIdBack);
            BufferedOutputStream streamFront = new BufferedOutputStream(new FileOutputStream(serverImgFront));
            BufferedOutputStream streamBack = new BufferedOutputStream(new FileOutputStream(serverImgBack));

            streamFront.write(registerDTO.getIdFront().getBytes());
            streamBack.write(registerDTO.getIdBack().getBytes());
            streamFront.close();
            streamBack.close();
        }
            catch(Exception e){
                System.out.println(e);
                redirectAttributes.addFlashAttribute("message","Tải ảnh lên thất bại, vui lòng thử lại!!!");
                return "redirect:/register";
        }
        try{
            Account account = new Account();
            UserInformation userInformation = new UserInformation();
            UserBalance userBalance = new UserBalance();

            String username =generateUsername();
            String password= generatePassword();
            System.out.println(password);
            account.setUserName(username);
            account.setPassword(passwordEncoder.encode(password));
            account.setRole("USER");

            userInformation.setUserName(username);
            userInformation.setEmail(registerDTO.getEmail());
            userInformation.setAddress(registerDTO.getAddress());
            userInformation.setGender(registerDTO.getGender());
            userInformation.setDob(registerDTO.getDob());
            userInformation.setFullName(registerDTO.getFullName());
            userInformation.setAccount(account);
            userInformation.setPhone(registerDTO.getPhone());

            userBalance.setUserName(username);
            userBalance.setUser_balance(account);
            userBalance.setBalance(0L);

            account.setUser(userInformation);
            account.setBalance(userBalance);
            accountRepo.save(account);

            userInformationRepo.save(userInformation);
            userBalanceRepo.save(userBalance);


            sendMailService.sender(registerDTO.getEmail(),username,password);
        }
        catch (Exception e){
            System.out.println(e);
            redirectAttributes.addFlashAttribute("message","Email hoặc số điện thoại đã được sử dụng. Vui lòng thử lại !!!");
            redirectAttributes.addFlashAttribute("flashRegisterDTO",registerDTO);
            return "redirect:/register";
        }
        return "redirect:/homepage";
    }
    private String generateUsername(){
        int max = 999999999;
        int min = 100000000;
        int range = max - min + 1;
        int password= (int) ((Math.random() * range) + min);
        String rand = Integer.toString(password);
        return  rand;
    }
    private String generatePassword() {
            // choose a Character random from this String
            String AlphaNumericString = "ABCDEFGHIJKLMNOPQRSTUVWXYZ"
                    + "0123456789"
                    + "abcdefghijklmnopqrstuvxyz";

            StringBuilder sb = new StringBuilder(10);
            for(int i = 0 ; i <10 ; i ++){
                    int index
                    = (int)(AlphaNumericString.length()
                    * Math.random());

            // add Character one by one in end of sb
            sb.append(AlphaNumericString
                    .charAt(index));
        }
                return sb.toString();
        }
}
