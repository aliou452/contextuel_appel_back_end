package com.stgson.order;

import com.fasterxml.jackson.annotation.JsonCreator;

public enum TypeOrder {
    MONEY,
    SEDDO;

    private String text;

    TypeOrder() {

    }

    @Override
    public String toString() {
        return text;
    }

    TypeOrder(String text){
        this.text = text;
    }

    public String getText() {
        return this.text;
    }

    @JsonCreator
    public static TypeOrder fromText(String text) {
        for( TypeOrder typeOrder: TypeOrder.values()) {
            if(typeOrder.name().equals(text)) {
                return typeOrder;
            }
        }
        throw new IllegalArgumentException();
    }



}
