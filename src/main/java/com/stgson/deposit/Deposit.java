package com.stgson.deposit;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.stgson.auth.AppUser;
import com.stgson.transaction.TypeTransaction;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;

@NoArgsConstructor
@Entity(name = "deposit")
@Table(name = "deposit")
@Getter
@Setter
public class Deposit {

    @SequenceGenerator(
            name = "deposit_sequence",
            sequenceName = "deposit_sequence",
            allocationSize = 1
    )
    @Id
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "deposit_sequence"
    )
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "dist_id")
    @JsonIgnoreProperties(value = {"applications", "hibernateLazyInitializer"})
    private AppUser dist;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "client_id")
    @JsonIgnoreProperties(value = {"applications", "hibernateLazyInitializer"})
    private Client client;

    private LocalDateTime doneAt;

    @Column(nullable = false)
    private double amount;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private TypeDeposit typeDeposit;

    public Deposit(AppUser dist, Client client, LocalDateTime doneAt, double amount, TypeDeposit typeDeposit) {
        this.dist = dist;
        this.client = client;
        this.doneAt = doneAt;
        this.amount = amount;
        this.typeDeposit = typeDeposit;
    }
}
