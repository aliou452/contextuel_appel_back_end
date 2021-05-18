package com.stgson.transaction;

import com.stgson.auth.AppUser;
import com.stgson.auth.AppUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1/")
public class TransactionController {

    TransactionService transactionService;

    @Autowired
    public TransactionController(TransactionService transactionService) {
        this.transactionService = transactionService;
    }

    @GetMapping("transactions")
    public List<Transaction> getTransactions(@RequestHeader("authorization") String authHeader){
        Long id = transactionService.amIUser(authHeader, "").getId();
        return transactionService.getAllTransactions(id);
    }
}
