package com.stgson.registration;

import lombok.*;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class RegistrationRequest {
    private String firstName;
    private String lastName;
    private String number;
    private String code;
}
