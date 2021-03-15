package com.stgson.model;

import javax.persistence.*;
import java.util.Collection;

@Entity
@Table(
        name = "userAuth",
        uniqueConstraints = @UniqueConstraint(
                columnNames = "number"
        )
)
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "first_name")
    private String firstName;

    @Column(name = "last_name")
    private String lastName;

    @Column(name = "number")
    private String number;

    @Column(name = "code")
    private String code;

    @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinTable(
            name = "users_roles",
            joinColumns = @JoinColumn(
                    name = "userAuth_id", referencedColumnName = "id"
            ),
            inverseJoinColumns = @JoinColumn(
                    name = "role_id",
                    referencedColumnName = "id"
            )
    )
    private Collection <Role> roles;


    public User() {
    }

    public User(String firstName,
                String lastName,
                String number,
                String code,
                Collection<Role> roles) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.number = number;
        this.code = code;
        this.roles = roles;
    }

    public String getFirst_name() {
        return firstName;
    }

    public String getLast_name() {
        return lastName;
    }

    public String getNumber() {
        return number;
    }

    public String getCode() {
        return code;
    }

    public Collection<Role> getRoles() {
        return roles;
    }

    public void setFirst_name(String first_name) {
        this.firstName = first_name;
    }

    public void setLast_name(String last_name) {
        this.lastName = last_name;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public void setRoles(Collection<Role> roles) {
        this.roles = roles;
    }
}
