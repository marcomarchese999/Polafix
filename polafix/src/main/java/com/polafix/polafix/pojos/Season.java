package com.polafix.polafix.pojos;

import java.util.ArrayList;
import java.util.Objects;

public class Season {
    private String title;
    private int number;
    private ArrayList<Chapter> chapters;

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

    public ArrayList<Chapter> getChapters() {
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
