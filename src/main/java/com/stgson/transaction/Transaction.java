package com.stgson.transaction;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.stgson.auth.AppUser;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;

@NoArgsConstructor
@Entity(name = "transaction")
@Table(name = "transaction")
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
    private AppUser sender;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "receiver_id")
    @JsonIgnoreProperties(value = {"applications", "hibernateLazyInitializer"})
    private AppUser receiver;

    private LocalDateTime doneAt;

    @Column(nullable = false)
    private double amount;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private TypeTransaction typeTransaction;

    public Transaction(AppUser sender,
                       AppUser receiver,
                       LocalDateTime doneAt,
                       double amount,
                       TypeTransaction typeTransaction) {
        this.sender = sender;
        this.receiver = receiver;
        this.doneAt = doneAt;
        this.amount = amount;
        this.typeTransaction = typeTransaction;
    }
}
