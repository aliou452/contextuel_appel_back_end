package com.stgson.transaction;

import com.stgson.auth.AppUser;
import com.stgson.auth.AppUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("api/v1/")
public class TransactionController {

    TransactionService transactionService;
    AppUserService appUserService;

    @Autowired
    public TransactionController(TransactionService transactionService,
                                 AppUserService appUserService) {
        this.transactionService = transactionService;
        this.appUserService = appUserService;
    }

    @PostMapping("{id}/transfers")
    public void doTransfer(@PathVariable Long id, @RequestBody TransactionRequest request){
        AppUser sender = appUserService.userExist(id);
        AppUser receiver = (AppUser) appUserService.loadUserByUsername(request.getReceiver());
        if(sender.getPocket() < request.getAmount()){
            throw new IllegalStateException("Solde insuffisant");
        }
        sender.setPocket(sender.getPocket() - request.getAmount());
        receiver.setPocket(receiver.getPocket() + request.getAmount());
        Transaction transaction = new Transaction();
        transaction.setSender(sender);
        transaction.setReceiver(receiver);
        transaction.setAmount(request.getAmount());
        transaction.setTypeTransaction(TypeTransaction.DEPOT);
        transaction.setDoneAt(LocalDateTime.now());
        transactionService.addTransaction(transaction);
    }

    @GetMapping("{id}/transfers")
    public List<Transaction> getTransfers(@PathVariable Long id){
        return transactionService.getAllTransaction(id);
    }
}
