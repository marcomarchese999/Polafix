package com.polafix.polafix.pojos;


import java.util.ArrayList;
import java.util.Objects;

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
    @OneToMany
    private ArrayList<Charge> charges;

    public Balance(float amount, Month month, Year year) {
        this.amount = amount;
        this.month = month;
        this.year = year;
        this.charges = new ArrayList<Charge>();
    }

    public void setAmount(float amount) {
        this.amount = amount;
    }

    public ArrayList<Charge> getCharges() {
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
    

    public ArrayList<Charge> getAllCharges() {
        return charges;
    }

    /*public ArrayList<Charge> getCharges(int month, int year){
        ArrayList<Charge> charges = getAllCharges();
        ArrayList<Charge> chargesInDate = new ArrayList<Charge>();

        for(int i=0; i<charges.size(); i++){
            LocalDate date = charges.get(i).getDate();
            int anno = date.getYear();
            int mese = date.getMonthValue();
            if(year==anno && mese==month){
                chargesInDate.add(charges.get(i));
            }
        }
        return chargesInDate;
    }*/

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
