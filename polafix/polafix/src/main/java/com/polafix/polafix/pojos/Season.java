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



@Entity
public class Season {

    public Season() {}

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private int number;
    private String title;
    @OneToMany(cascade = CascadeType.ALL)
    private List<Chapter> chapters;

    public Season(String title, int number) {
        setTitle(title);
        setNumber(number);
        this.chapters = new ArrayList<Chapter>();
    }

    public String getTitle() {
        return this.title;
    }

    public int getNumber() {
        return this.number;
    }

    public List<Chapter> getChapters() {
        return this.chapters;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public void addChapter(Chapter chapter) {
        if(!chapters.contains(chapter))
            this.chapters.add(chapter);
    }

    public Chapter getChapter(String title){
        for(int i=0; i<this.getChapters().size(); i++){
            if(this.getChapters().get(i).getTitle().equals(title)){
                return this.getChapters().get(i);
            }
        }
        return null;
    }

    public Chapter getChapter(int number){
        for(int i=0; i<this.getChapters().size(); i++){
            if(this.getChapters().get(i).getNumber()==number){
                return this.getChapters().get(i);
            }
        }
        return null;
    }
    
    @Override
    public boolean equals(Object o) {
        if (o == this)
            return true;
        if (!(o instanceof Season)) {
            return false;
        }
        Season season = (Season) o;
        return Objects.equals(title, season.title) && number == season.number && Objects.equals(chapters, season.chapters);
    }

    @Override
    public int hashCode() {
        return Objects.hash(title, number, chapters);
    } 


}
