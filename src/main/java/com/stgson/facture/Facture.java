package com.stgson.facture;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;
import java.util.stream.Collectors;

@Entity(name = "facture")
@Table(
        name = "facture",
        uniqueConstraints = {
                @UniqueConstraint(name = "facture_contractNum_unique", columnNames = "contract")
        })

@NoArgsConstructor
@Getter
@Setter
public class Facture implements Serializable {

    @SequenceGenerator(
            name = "facture_sequence",
            sequenceName = "facture_sequence",
            allocationSize = 1
    )
    @Id
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "facture_sequence"
    )
    private Long id;

    @Column(
            nullable = false,
            name = "contract"
    )
    private Long contract;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private FactureType type;

    @ElementCollection
    private List<OneFacture> list;

    public Facture(Long contract, FactureType type, List<OneFacture> list) {
        this.contract = contract;
        this.type = type;
        this.list = list;
    }

    public Facture(Long contract, FactureType type) {
        this.contract = contract;
        this.type = type;
    }

    public void addTo(OneFacture oneFacture) {
        this.list.add(oneFacture);
    }

    public OneFacture getOneFacture(Long fact_id) {
        return list.stream().filter(oneFacture -> !oneFacture.getPaid() && oneFacture.getId().equals(fact_id))
                .collect(Collectors.toList()).get(0);
    }

    public List<OneFacture> oneFactNotPaid() {
        return list.stream().filter(oneFacture -> !oneFacture.getPaid()).collect(Collectors.toList());
    }
}
