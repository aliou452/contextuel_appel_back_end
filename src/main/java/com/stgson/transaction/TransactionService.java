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

    public AppUser amIUser(String authHeader, String code) {
        String token = authHeader.replace(jwtConfig.getTokenPrefix(), "");
        String distNumber = jwtUtils.extractUsername(token);

        AppUser dist = (AppUser) appUserService.loadUserByUsername(distNumber);

        if(!passwordEncoder.matches(code, dist.getPassword()) && !code.isBlank()) {
            throw new IllegalStateException("Code secret invalide");
        }
        return dist;
    }

    public List<Transaction> getAllTransactions(Long id){
        List<Transaction> transactions = new ArrayList<>(transactionRepository.findByDistId(id));
        transactions.sort(Comparator.comparing(Transaction::getDoneAt).reversed());
        return transactions;
    }

}
