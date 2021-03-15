package com.stgson.registration;

import com.stgson.auth.AppUser;
import com.stgson.auth.AppUserRole;
import com.stgson.auth.AppUserService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/v1/registration")
@AllArgsConstructor
public class RegistrationController {

    private final AppUserService appUserService;

    @PostMapping
    public Boolean register(@RequestBody RegistrationRequest request) {
        appUserService.signUpUser(
                new AppUser(
                        request.getFirstName(),
                        request.getLastName(),
                        request.getNumber(),
                        request.getCode(),
                        AppUserRole.CLIENT,
                        false,
                        true
                )
        );

        return true;

    }
}
