package com.stgson.facture;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.time.Month;
import java.time.MonthDay;
import java.time.Year;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class OneFactureRequest {
    private String date;
    private Double amount;
    private Long id;
}
