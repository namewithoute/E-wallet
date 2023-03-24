package com.example.ewallet.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class TransactionResultController {
    @GetMapping(value ={ "/transfer/result" ,"/deposit/result","/withdraw/result"})
    public String renderResultPage(){
        return "transactionResult";
    }

}
