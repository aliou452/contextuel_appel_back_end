package com.stgson.transaction;

import com.stgson.auth.AppUser;
import com.stgson.auth.AppUserService;
import com.stgson.jwt.JwtConfig;
import com.stgson.jwt.JwtUtils;
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
                                 AppUserService appUserService
                                 ) {
        this.transactionService = transactionService;
        this.appUserService = appUserService;
    }

    @PostMapping("transfers")
    public void doTransfer(
            @RequestBody TransactionRequest request,
            @RequestHeader("authorization") String authHeader
    ){

        AppUser receiver = (AppUser) appUserService.loadUserByUsername(request.getReceiver());
        AppUser sender = transactionService.amIUser(authHeader, request.getCode());

        if(sender.getPocket() < request.getAmount()){
            throw new IllegalStateException("Solde insuffisant");
        }

        if(sender.getNumber().equals(receiver.getNumber())){
            throw new IllegalStateException("Vous ne pouvez pas vous envoyer de l'argent");
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

    @GetMapping("transfers")
    public List<Transaction> getTransfers(@RequestHeader("authorization") String authHeader){
        Long id = transactionService.amIUser(authHeader, "").getId();
        return transactionService.getAllTransaction(id);
    }
}
