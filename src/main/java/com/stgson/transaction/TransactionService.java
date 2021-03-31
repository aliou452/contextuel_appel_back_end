package com.stgson.transaction;

import com.stgson.auth.AppUser;
import com.stgson.auth.AppUserService;
import com.stgson.jwt.JwtConfig;
import com.stgson.jwt.JwtUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class TransactionService {

    private final TransactionRepository transactionRepository;
    private final JwtConfig jwtConfig;
    private final JwtUtils jwtUtils;
    private final AppUserService appUserService;
    public final PasswordEncoder passwordEncoder;

    @Autowired
    public TransactionService(TransactionRepository transactionRepository, JwtConfig jwtConfig, JwtUtils jwtUtils, AppUserService appUserService, PasswordEncoder passwordEncoder) {
        this.transactionRepository = transactionRepository;
        this.jwtConfig = jwtConfig;
        this.jwtUtils = jwtUtils;
        this.appUserService = appUserService;
        this.passwordEncoder = passwordEncoder;
    }

    public AppUser amIUser(String authHeader, Long id, TransactionRequest request) {
        String token = authHeader.replace(jwtConfig.getTokenPrefix(), "");
        String SenderNumber = jwtUtils.extractUsername(token);

        AppUser sender = appUserService.userExist(id);
        if (!sender.getNumber().equals(SenderNumber)) {
            throw new IllegalStateException("Vous ne pouvez pas utiliser un compte qui n'est pas le votre");
        }

        if(!passwordEncoder.matches(request.getCode(), sender.getPassword())) {
            throw new IllegalStateException("Code secret invalide");
        }

        return sender;
    }
    public List<Transaction> getAllTransaction(Long id){
        List<Transaction> transactions = new ArrayList<>();
        transactions.addAll(transactionRepository.findBySenderId(id)
                .stream()
                .peek(
                        receipt -> receipt.setTypeTransaction(TypeTransaction.RETRAIT)
                ).collect(Collectors.toList()));
        transactions.addAll(transactionRepository.findByReceiverId(id)
                .stream()
                .peek(
                        receipt -> receipt.setTypeTransaction(TypeTransaction.DEPOT)
                ).collect(Collectors.toList()));
        transactions.sort(Comparator.comparing(Transaction::getDoneAt).reversed());
        return transactions;
    }

    public void addTransaction(Transaction transaction){
        transactionRepository.save(transaction);
    }
}
