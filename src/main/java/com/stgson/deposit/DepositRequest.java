package com.stgson.deposit;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@Getter
@Setter
@NoArgsConstructor
public class DepositRequest {
    private Double amount;
    private String code;
    private String number;
    private String type;
}
