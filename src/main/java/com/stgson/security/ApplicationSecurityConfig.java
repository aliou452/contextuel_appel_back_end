package com.stgson.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.csrf.CookieCsrfTokenRepository;

import static com.stgson.security.ApplicationUserRole.*;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class ApplicationSecurityConfig extends WebSecurityConfigurerAdapter {

    private final PasswordEncoder passwordEncoder;

    @Autowired
    public ApplicationSecurityConfig(PasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
    }


    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .csrf().csrfTokenRepository(CookieCsrfTokenRepository.withHttpOnlyFalse())
                .and()
                .authorizeRequests()
//                .antMatchers("/", "/css/*", "/js/*").permitAll()
//                .antMatchers("/api/v1/home").hasAnyRole(ADMIN.name(), VERIFICATEUR.name())
//                .antMatchers(GET, "/api/v1/dash").hasAuthority(DEPOT_WRITE.getPermission())
                .anyRequest()
                .authenticated()
                .and()
                .httpBasic();
    }

    @Override
    @Bean
    protected UserDetailsService userDetailsService() {

        UserDetails abdallah = User.builder()
                .username("abdallah")
                .password(passwordEncoder.encode("password"))
//                .roles(DISTRIBUTEUR.name()) //ROLE_DISTRIBUTEUR
                .authorities(DISTRIBUTEUR.getSimpleGrantedAuthority())
                .build();
        UserDetails aliou = User.builder()
                .username("aliou")
                .password(passwordEncoder.encode("password"))
//                .roles(VERIFICATEUR.name())
                .authorities(ADMIN.getSimpleGrantedAuthority())
                .build();
        UserDetails zakaria = User.builder()
                .username("zakaria")
                .password(passwordEncoder.encode("password"))
//                .roles(ADMIN.name())
                .authorities(VERIFICATEUR.getSimpleGrantedAuthority())
                .build();

        return new InMemoryUserDetailsManager(abdallah, aliou, zakaria);
    }
}
