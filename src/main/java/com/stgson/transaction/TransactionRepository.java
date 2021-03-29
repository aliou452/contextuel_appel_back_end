package com.stgson.transaction;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TransactionRepository extends JpaRepository<Transaction, Long> {

    List<Transaction> findBySenderId(Long id);
    List<Transaction> findByReceiverId(Long id);
}