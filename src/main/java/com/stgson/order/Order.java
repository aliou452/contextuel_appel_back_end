package com.stgson.order;

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
@DiscriminatorValue("2")
@Getter
@Setter
public class Order extends Transaction {

    public Order(AppUser dist, LocalDateTime doneAt, double amount, TransactionType transactionType) {
        super(dist, doneAt, amount, transactionType);
    }
}
