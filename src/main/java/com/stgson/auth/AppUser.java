package com.stgson.auth;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import java.util.Collection;
import java.util.Collections;

@Getter
@Setter
@EqualsAndHashCode
@NoArgsConstructor
@Entity(name = "app_user")
@Table(
        name = "app_user",
        uniqueConstraints = {
                @UniqueConstraint(name = "app_user_number_unique", columnNames = "number")
        }
)
public class AppUser implements UserDetails {

    @SequenceGenerator(
            name = "user_sequence",
            sequenceName = "user_sequence",
            allocationSize = 1
    )
    @Id
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "user_sequence"
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
            name = "code",
            nullable = false
    )
    private String code;

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

    @Enumerated(EnumType.STRING)
    private AppUserRole appUserRole;

    private boolean locked = false;

    private boolean enabled = false;
    public AppUser(String firstName,
                   String lastName,
                   String number,
                   String code,
                   Double pocket,
                   Double seddo,
                   AppUserRole appUserRole,
                   boolean locked,
                   boolean enabled) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.number = number;
        this.code = code;
        this.appUserRole = appUserRole;
        this.locked = locked;
        this.enabled = enabled;
        this.pocket = pocket;
        this.seddo = seddo;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        SimpleGrantedAuthority authority = new SimpleGrantedAuthority(appUserRole.name());
        return Collections.singletonList(authority);
    }

    @Override
    public String getPassword() {
        return code;
    }

    @Override
    public String getUsername() {
        return number;
    }

    public Double getPocket() {
        return pocket;
    }

    public void setPocket(Double pocket) {
        this.pocket = pocket;
    }

    public Double getSeddo() {
        return seddo;
    }

    public void setSeddo(Double seddo) {
        this.seddo = seddo;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return !locked;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return enabled;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }
}
