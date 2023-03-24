package com.example.ewallet.controller;

import com.example.ewallet.dao.CreditCard;
import com.example.ewallet.dao.Transaction;
import com.example.ewallet.dao.UserBalance;
import com.example.ewallet.dto.DepositDTO;
import com.example.ewallet.dto.TransactionResultDTO;
import com.example.ewallet.repo.CreditCardRepo;
import com.example.ewallet.repo.TransactionRepo;
import com.example.ewallet.repo.UserBalanceRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.security.Principal;
import java.util.Optional;

@Controller
public class DepositController {
    @Autowired
    DepositDTO depositDTO;
    @Autowired
    UserBalanceRepo userBalanceRepo;
    @Autowired
    TransactionRepo transactionRepo;
    @Autowired
    CreditCardRepo creditCardRepo;
    @GetMapping("/deposit")
    public String renderDepositPage(Model model){
        model.addAttribute("deposit",depositDTO);
        return "deposit";
    }

    @PostMapping("/deposit")
    @Transactional
    public String handleDepositPOST(@ModelAttribute DepositDTO deposit, Principal principal, RedirectAttributes redirectAttributes){
            try {
                CreditCard card = creditCardRepo.findByCardNumberAndExpiredAtAndCvvCode(deposit.getCreditCode(), deposit.getExpireDate(), deposit.getCvv());
                if (card == null) {
                    throw new Exception("THÔNG TIN THẺ KHÔNG HỢP LỆ, VUI LÒNG THỬ LẠI");
                }
                Transaction transaction = new Transaction();
                transaction.setTransactionType("Deposit");
                transaction.setTransactionStatus("Success");

                userBalanceRepo.updateBalanceIncrease(deposit.getAmount(), principal.getName());
                transactionRepo.save(transaction);
            }
            catch (Exception e ){
                redirectAttributes.addFlashAttribute("error",e.getMessage());
                return "redirect:/deposit";
            }
        TransactionResultDTO transactionResultDTO=new TransactionResultDTO("XÁC NHẬN NẠP TIỀN","Nạp tiền thành công, cảm ơn bạn đã sử dụng dịch vụ","/img/success_symbol.png");
        redirectAttributes.addFlashAttribute("res",transactionResultDTO);

        return "redirect:/deposit/result";
    }


}
