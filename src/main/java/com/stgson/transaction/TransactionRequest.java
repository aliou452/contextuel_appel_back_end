package com.stgson.transaction;

import com.stgson.auth.AppUser;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@Getter
@Setter
@NoArgsConstructor
public class TransactionRequest {
    private String receiver;
    private Double amount;
}
