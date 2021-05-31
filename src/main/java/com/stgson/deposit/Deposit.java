package com.stgson.deposit;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.stgson.auth.AppUser;
import com.stgson.transaction.Transaction;
import com.stgson.transaction.TransactionType;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;

@NoArgsConstructor
@Entity
@DiscriminatorValue("1")
@Getter
@Setter
public class Deposit extends Transaction {

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "client_id")
//    @JsonIgnoreProperties(value = {"applications", "hibernateLazyInitializer"})
    private Client client;

    public Deposit(AppUser dist, Client client, LocalDateTime doneAt, double amount, TransactionType transactionType) {
        super(dist, doneAt, amount, transactionType);
        this.client = client;
    }
}
