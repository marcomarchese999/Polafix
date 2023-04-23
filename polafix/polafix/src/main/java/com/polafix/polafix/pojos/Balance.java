package com.polafix.polafix.pojos;


import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

import java.time.Year;
import java.time.Month;

@Entity
public class Balance {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private float amount;
    private Month month;
    private Year year;
    @OneToMany(cascade = CascadeType.ALL)
    private List<Charge> charges;


    public Balance() {}


    public Balance(float amount, Month month, Year year) {
        this.amount = amount;
        this.month = month;
        this.year = year;
        this.charges = new ArrayList<Charge>();
    }

    public void setAmount(float amount) {
        this.amount = amount;
    }

    public List<Charge> getCharges() {
        return this.charges;
    }

    public float getAmount() {
        return this.amount;
    }

    public void setBalance(float amount) {
        this.amount = amount;
    }

    public Month getMonth() {
        return this.month;
    }

    public void setMonth(Month month) {
        this.month = month;
    }

    public Year getYear() {
        return this.year;
    }

    public void setYear(Year year) {
        this.year = year;
    }
    public void setCharges(ArrayList<Charge> charges) {
        this.charges = charges;
    }
    

    public List<Charge> getAllCharges() {
        return charges;
    }

    public void addCharge(Charge charge){
        this.getAllCharges().add(charge);
        float newAmount = this.getAmount() + charge.getPrice();
        setAmount(newAmount);
    }

    @Override
    public boolean equals(Object o) {
        if (o == this)
            return true;
        if (!(o instanceof Balance)) {
            return false;
        }
        Balance balance = (Balance) o;
        return Objects.equals(month, balance.month) && Objects.equals(year, balance.year);
    }

    @Override
    public int hashCode() {
        return Objects.hash(month, year);
    }

}
