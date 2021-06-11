package com.stgson.auth;

import com.stgson.transaction.TransactionType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

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

    public void signUpUser(AppUser appUser) {
        boolean userExists = appUserRepository
                .findByNumber(appUser.getNumber())
                .isPresent();
        if (userExists) {
            throw new IllegalStateException("Phone number has already Orange Money Account");
        }

        String encodedPassword = passwordEncoder.encode(appUser.getCode());
        appUser.setCode(encodedPassword);

        appUserRepository.save(appUser);

    }

    public void updateUser(AppUser appUser, Double amount, String transType){
        if(transType.equals(TransactionType.MONEY.name())
                || transType.equals(TransactionType.MONEY_WITHDRAW.name())
        ){
            appUser.setPocket(appUser.getPocket() + amount);
        } else{
            appUser.setSeddo(appUser.getSeddo() + amount);
        }
        appUserRepository.save(appUser);

    }

    public void saveUser(AppUser appUser) {
        appUserRepository.save(appUser);
    }


}