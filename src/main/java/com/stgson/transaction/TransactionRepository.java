package com.stgson.transaction;

import com.stgson.deposit.Deposit;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TransactionRepository extends JpaRepository<Transaction, Long> {
    List<Transaction> findByDistId(Long id);
}
