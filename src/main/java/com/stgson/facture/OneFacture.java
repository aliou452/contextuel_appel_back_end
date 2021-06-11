package com.stgson.facture;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity(name = "one_facture")
@Getter
@Setter
@NoArgsConstructor
public class OneFacture {

    @Id
    private Long id;

    private LocalDate paymentDay;

    private Double amount;

    private Boolean paid;

    public OneFacture(Long id, LocalDate paymentDay, Double amount, Boolean paid) {
        this.id = id;
        this.paymentDay = paymentDay;
        this.amount = amount;
        this.paid = paid;
    }
}
