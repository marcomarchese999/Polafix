package com.polafix.polafix.pojos;

import java.time.LocalDate;
import java.util.Objects;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;



@Entity
public class Charge {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private LocalDate date;
    private String name;
    private int season;
    private int number;
    private float price;

    public Charge() {}

    public Charge(LocalDate date, String name, int season, int number, float price) {
        this.date = date;
        this.name = name;
        this.season = season;
        this.number = number;
        this.price = price;
    }

    public LocalDate getDate() {
        return date;
    }

    public String getName() {
        return name;
    }

    public int getSeason() {
        return season;
    }

    public int getNumber() {
        return number;
    }

    public float getPrice() {
        return price;
    }

    @Override
    public boolean equals(Object o) {
        if (o == this)
            return true;
        if (!(o instanceof Charge)) {
            return false;
        }
        Charge charge = (Charge) o;
        return Objects.equals(id, charge.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }


}
