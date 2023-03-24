package com.example.ewallet.controller;

import com.example.ewallet.dao.Account;
import com.example.ewallet.dao.Transaction;
import com.example.ewallet.dao.TransferTrx;
import com.example.ewallet.dao.UserInformation;
import com.example.ewallet.dto.TransactionResultDTO;
import com.example.ewallet.dto.TransferDTO;
import com.example.ewallet.repo.TransactionRepo;
import com.example.ewallet.repo.TransferRepo;
import com.example.ewallet.repo.UserBalanceRepo;
import com.example.ewallet.repo.UserInformationRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.security.Principal;

@Controller
public class TransferController {
    @Autowired
    TransferDTO transferDTO;
    @Autowired
    UserInformationRepo userInformationRepo;

    @Autowired
    TransactionRepo transactionRepo;
    @Autowired
    TransferRepo transferRepo;
    @Autowired
    UserBalanceRepo userBalanceRepo;

    @GetMapping(value = "/transfer")
    public String transferPage(Model model){
        model.addAttribute("transferDTO",transferDTO);
        return "transfer";
    }
    @GetMapping(value = "/get-name-user")
    public ResponseEntity getUser(@RequestParam(name="phone") String phone){
        System.out.println(phone);
        UserInformation userInformation=userInformationRepo.findByPhone(phone);
        System.out.println(userInformation);
        return ResponseEntity.ok(userInformation);
    }

    @PostMapping(value = "/transfer")
    @Transactional
    public String transferHandle(@ModelAttribute TransferDTO transfer,
                                 RedirectAttributes redirectAttributes,
                                 Principal principal){
            System.out.println(principal);
            Long fees= (long) (transfer.getAmount()*0.05);
            Long amount = transfer.getAmount();
            TransactionResultDTO transactionResultDTO = null;

        try {
            Account from = userInformationRepo.findByUserName(principal.getName()).getAccount();
            Account to = userInformationRepo.findByPhone(transfer.getReceiver()).getAccount();
            System.out.println(from);
            System.out.println(to);

            if(userBalanceRepo.findById(principal.getName()).get().getBalance()<transfer.getAmount()){
                throw new Exception("SỐ DƯ KHÔNG HỢP LỆ, VUI LÒNG THỬ LẠI!...");
            }

            Transaction transaction = new Transaction();
            TransferTrx transferTrx = new TransferTrx();
            if (transfer.getAmount() >= 5000000) {
                transaction.setTransactionStatus("Pending");
                transactionResultDTO=new TransactionResultDTO("ĐANG XÁC NHẬN","Giao dịch của bạn đang chờ được xử lý","/img/pending.jpg");
            } else {
                transaction.setTransactionStatus("Success");
                System.out.println(to.getUserName());
                System.out.println(principal.getName());
                System.out.println(transfer.getPayerOption());
                if(transfer.getPayerOption().equals("Người chuyển trả")){
                    userBalanceRepo.updateBalanceDecrease((amount+fees),principal.getName());
                    userBalanceRepo.updateBalanceIncrease(amount,to.getUserName());
                }
                else if(transfer.getPayerOption().equals("Người nhận trả")){
                    userBalanceRepo.updateBalanceDecrease(amount,principal.getName());
                    userBalanceRepo.updateBalanceIncrease((amount-fees),to.getUserName());
                }

                transactionResultDTO=new TransactionResultDTO("ĐÃ XÁC NHẬN CHUYỂN TIỀN","Giao dịch thành công, cảm ơn bạn đã sử dụng dịch vụ","/img/success_symbol.png");
            }
            transaction.setTransactionType("Transfer");

            transferTrx.setTransferId(transaction.getId());
            transferTrx.setTransferFees(fees);
            transferTrx.setAmount(transfer.getAmount());
            transferTrx.setFromAccount(from.getUserName());
            transferTrx.setToAccount(to.getUserName());
            transferTrx.setPayerOption(transfer.getPayerOption());

            transferTrx.setNote(transfer.getNote());
            transferTrx.setTransfer(transaction);
            transaction.setTransferTrx(transferTrx);
            transactionRepo.save(transaction);
            transferRepo.save(transferTrx);
        }
        catch (Exception e){
            redirectAttributes.addFlashAttribute("error",e.getMessage());
            return "redirect:/transfer";

        }
        redirectAttributes.addFlashAttribute("res",transactionResultDTO);
        return "redirect:/transfer/result";
    }
}
