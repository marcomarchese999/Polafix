package com.polafix.polafix.pojos;

import java.util.ArrayList;
import java.util.Objects;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.Table;


@Entity
@Table(name = "series")
public class Serie {

    @Id
    private String idSerie;
    @Column(name = "name")
    private String name;
    @Column(name = "type")
    private Type type;
    @Column(name = "shortDescription")
    private String shortDescription;
    @OneToMany(cascade = CascadeType.ALL)
    private ArrayList<Season> seasons;
    @ManyToMany(cascade = CascadeType.ALL)
    private ArrayList<Actor> actors;
    @ManyToMany(cascade = CascadeType.ALL)
    private ArrayList<Creator> creators;

    public Serie(String idSerie, String name, Type type, String shortDescription) {
        setIdSerie(idSerie);
        setName(name);
        setType(type);
        setShortDescription(shortDescription);
        this.seasons = new ArrayList<Season>();
        this.actors = new ArrayList<Actor>();
        this.creators = new ArrayList<Creator>();
    } 


    public String getIdSerie() {
        return this.idSerie;
    }

    public void setIdSerie(String idSerie) {
        this.idSerie = idSerie;
    }
    public void setShortDescription(String shortDescription) {
        this.shortDescription = shortDescription;
    }

    public String getName() {
        return this.name;
    }

    public Type getType() {
        return type;
    }

    public String getShortDescription() {
        return shortDescription;
    }

    public ArrayList<Season> getSeasons() {
        return seasons;
    }

    public ArrayList<Actor> getActors() {
        return actors;
    }

    public ArrayList<Creator> getCreators() {
        return creators;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setType(Type type) {
        this.type = type;
    }

    public void addActor(Actor actor) {
        if(!actors.contains(actor))
            this.actors.add(actor);
    }

    public void addCreator(Creator creator) {
        if(!creators.contains(creator))
            this.creators.add(creator);
    }

    public void addSeason(Season season) {
        if(!seasons.contains(season))
            this.seasons.add(season);
    }

    public void setSeasons(ArrayList<Season> stagioni){
        for(int i=0; i<stagioni.size(); i++){
            if(!seasons.contains(stagioni.get(i)))
                this.addSeason(stagioni.get(i));
        }
    }

    public void setActors(ArrayList<Actor> attori){
        for(int i=0; i<attori.size(); i++){
            if(!actors.contains(attori.get(i)))
                this.addActor(attori.get(i));
        }
    }

    public void setCreators(ArrayList<Creator> creatori){
        for(int i=0; i<creatori.size(); i++){
            if(!creators.contains(creatori.get(i)))
                this.addCreator(creatori.get(i));
        }
    }
    
    public Season getSeason(String title){
        for(int i=0; i<this.getSeasons().size(); i++){
            if(this.getSeasons().get(i).getTitle().equals(title)){
                return this.getSeasons().get(i);
            }
        }
        return null;
    }

    public Chapter getChapter(Season season, String title){
        return season.getChapter(title);
    }

    public Season getSeason(int number){
        for(int i=0; i<this.getSeasons().size(); i++){
            if(this.getSeasons().get(i).getNumber()==number){
                return this.getSeasons().get(i);
            }
        }
        return null;
    }

    public Chapter getChapter(Season season, int number){
        return season.getChapter(number);
    }

    @Override
    public boolean equals(Object o) {
        if (o == this)
            return true;
        if (!(o instanceof Serie)) {
            return false;
        }
        Serie serie = (Serie) o;
        return Objects.equals(idSerie, serie.idSerie);
    }

    @Override
    public int hashCode() {
        return Objects.hash(idSerie, name, type, shortDescription, seasons, actors, creators);
    }
}
