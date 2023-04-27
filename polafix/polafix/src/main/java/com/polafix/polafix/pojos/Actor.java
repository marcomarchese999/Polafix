package com.polafix.polafix.pojos;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;


@Entity
public class Actor {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String name;
    private String surname;
    @ManyToMany(mappedBy = "actors")
    public List<Serie> series;


    public Actor() {}


    public Actor(String name, String surname, List<Serie> series) {
        
        this.name = name;
        this.surname = surname;
        setSeries(series);
    }

    public String getName() {
        return name;
    }

    public String getSurname() {
        return surname;
    }

    public List<Serie> getSeries() {
        return series;
    }

    public void setSeries(List<Serie> series) {
        this.series = new ArrayList<Serie>(series);
    }
    
    public void addSerie(Serie serie){
        this.getSeries().add(serie);
    }

    @Override
    public boolean equals(Object o) {
        if (o == this)
            return true;
        if (!(o instanceof Actor)) {
            return false;
        }
        Actor actor = (Actor) o;
        return Objects.equals(id, actor.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
