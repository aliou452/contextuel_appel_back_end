package com.stgson.registration;

import com.stgson.auth.AppUser;
import com.stgson.auth.AppUserRole;
import com.stgson.auth.AppUserService;
import com.stgson.jwt.JwtConfig;
import com.stgson.jwt.JwtUtils;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import lombok.AllArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.crypto.SecretKey;

@RestController
@RequestMapping("api/v1/")
@AllArgsConstructor
public class RegistrationController {

    private final AppUserService appUserService;
    private final SecretKey secretKey;
    private final JwtConfig jwtConfig;
    private final JwtUtils jwtUtils;

    @PostMapping("registration")
    public Boolean register(@RequestBody RegistrationRequest request) {
        appUserService.signUpUser(
                new AppUser(
                        request.getFirstName(),
                        request.getLastName(),
                        request.getNumber(),
                        request.getCode(),
                        100000.0,
                        AppUserRole.CLIENT,
                        false,
                        true
                )
        );

        return true;
    }

    @GetMapping("info")
    public AppUser getInfo(@RequestHeader("authorization") String authHeader) {
        String token = authHeader.replace(jwtConfig.getTokenPrefix(), "");
//
//        Jws<Claims> claimsJws = Jwts.parser()
//                .setSigningKey(secretKey)
//                .parseClaimsJws(token);
//
//        Claims body = claimsJws.getBody();

//        String number = body.getSubject();
        String number = jwtUtils.extractUsername(token);

        return (AppUser) appUserService.loadUserByUsername(number);
    }
}
