package com.stgson.order;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.stgson.auth.AppUser;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;

@NoArgsConstructor
@Entity(name = "order")
@Table(name = "orders")
@Getter
@Setter
public class Order {

    @SequenceGenerator(
            name = "order_sequence",
            sequenceName = "order_sequence",
            allocationSize = 1
    )
    @Id
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "order_sequence"
    )
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "author_id")
    @JsonIgnoreProperties(value = {"applications", "hibernateLazyInitializer"})
    private AppUser author;

    private LocalDateTime doneAt;

    @Column(nullable = false)
    private double amount;

    @Column(nullable = false)
    private TypeOrder typeOrder;

    public Order(AppUser author, LocalDateTime doneAt, double amount, TypeOrder typeOrder) {
        this.author = author;
        this.doneAt = doneAt;
        this.amount = amount;
        this.typeOrder = typeOrder;
    }
}
