package com.example.ewallet.controller;

import com.example.ewallet.dao.Transaction;
import com.example.ewallet.dao.WithdrawTrx;
import com.example.ewallet.dto.TransactionResultDTO;
import com.example.ewallet.dto.WithdrawDTO;
import com.example.ewallet.repo.TransactionRepo;
import com.example.ewallet.repo.UserBalanceRepo;
import com.example.ewallet.repo.WithdrawRepo;
import lombok.With;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.security.Principal;

@Controller
public class WithdawController {
    @Autowired
    WithdrawDTO withdrawDTO;
    @Autowired
    UserBalanceRepo userBalanceRepo;
    @Autowired
    TransactionRepo transactionRepo;
    @Autowired
    WithdrawRepo withdrawRepo;


    @GetMapping("/withdraw")
    public String withdrawPage(Model model){
        model.addAttribute("withdrawDTO",withdrawDTO);
        return "withdraw";
    }

    @PostMapping("/withdraw")
    @Transactional
    public String withdrawResult(@ModelAttribute(name="withdrawDTO") WithdrawDTO withdraw, Principal principal, RedirectAttributes redirectAttributes){
        TransactionResultDTO transactionResultDTO=null;
        try {
            if (withdraw.getAmount() > userBalanceRepo.findById(principal.getName()).get().getBalance()) {
                throw new Exception("SỐ DƯ KHÔNG HỢP LỆ VUI LÒNG THỬ LẠI!!!");
            }
            else if(withdraw.getAmount()%50000!=0){
                throw new Exception("SỐ TIỀN RÚT PHẢI LÀ BỘI CỦA 50.000VNĐ");
            }
            else{
                Transaction transaction = new Transaction();
                WithdrawTrx withdrawTrxDAO=new WithdrawTrx();
                Long fees=(long) (withdraw.getAmount()*0.05);
                if(withdraw.getAmount()>=5000000){
                    transaction.setTransactionStatus("Pending");
                    transactionResultDTO=new TransactionResultDTO("ĐANG XÁC NHẬN RÚT TIỀN","Giao dịch rút tiền của bạn đang được xử lý.","/img/pending.jpg");
                }
                else{
                    transaction.setTransactionStatus("Success");
                    userBalanceRepo.updateBalanceDecrease( (withdraw.getAmount()+fees), principal.getName());
                    transactionResultDTO=new TransactionResultDTO("XÁC NHẬN RÚT TIỀN THÀNH CÔNG","Giao dịch rút tiền thành công, cảm ơn bạn đã sử dụng dịch vụ.","/img/success_symbol.png");

                }
                transaction.setTransactionType("Withdraw");

                withdrawTrxDAO.setWithdrawId(transaction.getId());
                withdrawTrxDAO.setAmount(withdraw.getAmount());
                withdrawTrxDAO.setFees(fees);
                withdrawTrxDAO.setCreditCard(withdraw.getCreditCard());
                withdrawTrxDAO.setWithdraw(transaction);
                withdrawTrxDAO.setUserName(principal.getName());
                transactionRepo.save(transaction);
                withdrawRepo.save(withdrawTrxDAO);
            }
        }
        catch (Exception e){
            System.out.println(e);
            redirectAttributes.addFlashAttribute("error",e.getMessage());

            return "redirect:/withdraw";
        }
        redirectAttributes.addFlashAttribute("res",transactionResultDTO);

        return "redirect:/withdraw/result";
    }
}
