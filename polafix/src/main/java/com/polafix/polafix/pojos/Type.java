package com.polafix.polafix.pojos;

public enum Type {
   
    GOLD(1.5f),
    SILVER(0.75f),
    STANDARD(0.5f);

    private final float price;

    private Type(float price) {
        this.price = price;
    }

    public float getprice() {
        return price;
    }
}
