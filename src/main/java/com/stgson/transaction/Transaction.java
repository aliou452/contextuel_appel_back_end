package com.stgson.transaction;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.stgson.auth.AppUser;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;

@Entity(name = "transactions")
@Table(name = "transactions")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name="transactions_type",
        discriminatorType = DiscriminatorType.INTEGER)
@NoArgsConstructor
@Getter
@Setter
public class Transaction implements Serializable {

    @SequenceGenerator(
            name = "transaction_sequence",
            sequenceName = "transaction_sequence",
            allocationSize = 1
    )
    @Id
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "transaction_sequence"
    )
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "sender_id")
    @JsonIgnoreProperties(value = {"applications", "hibernateLazyInitializer"})
    private AppUser dist;

    private LocalDateTime doneAt;

    @Column(nullable = false)
    private double amount;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private TransactionType transactionType;

    public Transaction(AppUser dist,
                       LocalDateTime doneAt,
                       double amount,
                       TransactionType transactionType) {
        this.dist = dist;
        this.doneAt = doneAt;
        this.amount = amount;
        this.transactionType = transactionType;
    }
}


