package com.stgson.transaction;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class TransactionService {

    private final TransactionRepository transactionRepository;

    @Autowired
    public TransactionService(TransactionRepository transactionRepository) {
        this.transactionRepository = transactionRepository;
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
        transactions.sort(Comparator.comparing(Transaction::getDoneAt));
        return transactions;
    }

    public void addTransaction(Transaction transaction){
        transactionRepository.save(transaction);
    }
}
