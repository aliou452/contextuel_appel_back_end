package com.stgson.auth;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AppUserService implements UserDetailsService {

    private final AppUserRepository appUserRepository;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public AppUserService(AppUserRepository appUserRepository, PasswordEncoder passwordEncoder) {
        this.appUserRepository = appUserRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public UserDetails loadUserByUsername(String number) throws UsernameNotFoundException {
        return appUserRepository
                .findByNumber(number)
                .orElseThrow(() ->
                        new UsernameNotFoundException(String.format("Username %s note found", number)));
    }

    public AppUser userExist(Long id){
        return appUserRepository
                .findById(id)
                .orElseThrow(() ->
                        new UsernameNotFoundException(String.format("Username %s note found", id)));
    }

    public boolean signUpUser(AppUser appUser) {
        boolean userExists = appUserRepository
                .findByNumber(appUser.getNumber())
                .isPresent();
        if (userExists) {
            throw new IllegalStateException("Phone number has already Orange Money Account");
        }

        String encodedPassword = passwordEncoder.encode(appUser.getCode());
        appUser.setCode(encodedPassword);

        appUserRepository.save(appUser);

        return true;
    }

    public void updateUser(AppUser appUser, Double amount){
        appUser.setPocket(appUser.getPocket() + amount);
        appUserRepository.save(appUser);
    }

}