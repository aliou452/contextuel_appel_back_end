package com.stgson.deposit;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@NoArgsConstructor
@Entity(name = "client")
@Table(
        name = "client",
        uniqueConstraints = {
                @UniqueConstraint(name = "Client_number_unique", columnNames = "number")
        })
@Getter
@Setter
public class Client {
    @SequenceGenerator(
            name = "client_sequence",
            sequenceName = "client_sequence",
            allocationSize = 1
    )
    @Id
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "client_sequence"
    )
    private Long id;

    @Column(
            name = "first_name",
            nullable = false,
            columnDefinition = "TEXT"
    )
    private String firstName;

    @Column(
            name = "last_name",
            nullable = false,
            columnDefinition = "TEXT"
    )
    private String lastName;

    @Column(
            name = "number",
            nullable = false
    )
    private String number;

    @Column(
            name = "pocket",
            nullable = false
    )
    private Double pocket;

    @Column(
            name = "seddo",
            nullable = false
    )
    private Double seddo;

    public Client(String firstName, String lastName, String number, Double pocket, Double seddo) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.number = number;
        this.pocket = pocket;
        this.seddo = seddo;
    }
}
