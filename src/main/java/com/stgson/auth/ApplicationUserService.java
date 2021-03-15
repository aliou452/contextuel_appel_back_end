package com.stgson.auth;

import com.stgson.model.User;
import com.stgson.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class ApplicationUserService implements UserDetailsService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public ApplicationUserService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public UserDetails loadUserByUsername(String number) throws UsernameNotFoundException {
        User user = userRepository
                .findByNumber(number)
                .orElseThrow(() ->
                        new UsernameNotFoundException(String.format("Username %s note found", number)));
        user.setCode(passwordEncoder.encode(user.getCode()));

        return new ApplicationUser(user);
    }
}
