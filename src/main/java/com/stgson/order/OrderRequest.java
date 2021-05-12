package com.stgson.order;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.lang.reflect.Type;

@AllArgsConstructor
@Getter
@Setter
@NoArgsConstructor
public class OrderRequest {
    private Double amount;
    private String code;
    private String typeOrder;
}
