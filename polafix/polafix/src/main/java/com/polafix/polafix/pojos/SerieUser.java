package com.polafix.polafix.pojos;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;

import com.fasterxml.jackson.annotation.JsonProperty;

@Entity
public class SerieUser {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JsonProperty("id")
    private Long id;
    @OneToOne
    @JsonProperty("serie")  
    private Serie serie;
    @JsonProperty("title")
    private String title;
    @JsonProperty("currentSeason")
    private int currentSeason;
    @ElementCollection
    private List<ChapterSeen> userChapters;

    public SerieUser() {}

    public SerieUser(Serie serie) {
        this.serie = serie;
        this.currentSeason = 1;
        this.userChapters = new ArrayList<ChapterSeen>();
        this.setTitle(serie.getName());
        

        List<Season> seasons = this.serie.getSeasons();
        for(int i=0; i<seasons.size(); i++){
            Season season = seasons.get(i);
            List<Chapter> chapters = seasons.get(i).getChapters();
            for(int j=0; j<chapters.size(); j++){
                ChapterSeen c = new ChapterSeen(ChapterState.NOTSEEN, season.getNumber(), chapters.get(j).getNumber(), chapters.get(j).getTitle(), chapters.get(j).getDescription());
                userChapters.add(c);
            }
        }
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return this.title;
    }

    public void setTitle(String title) {
        this.title = title;
    }


    public Long getId() {
        return this.id;
    }

    public void setSerie(Serie serie) {
        this.serie = serie;
    }
    public void setUserChapters(List<ChapterSeen> userChapters) {
        this.userChapters = userChapters;
    }

    public Serie getSerie() {
        return serie;
    }

    public int getCurrentSeason() {
        return currentSeason;
    }

    public void setCurrentSeason(int currentSeason) {
        this.currentSeason = currentSeason;
    }

    public List<ChapterSeen> getUserChapters() {
        return userChapters;
    }

    public boolean isLastChapter(ChapterSeen chapter){
        if(this.getUserChapters().get(this.getUserChapters().size()-1).equals(chapter))
            return true;
        else
            return false;
    }

    public void setNextSeason(ChapterSeen currentChapter, int num_chapters) { 
        if(currentChapter.getNumberChapter()==num_chapters && !isLastChapter(currentChapter)) 
            this.currentSeason = currentChapter.getNumberSeason()+1; 
        else { 
            if(isLastChapter(currentChapter)) 
                this.currentSeason = 1; 
            else 
                this.currentSeason = currentChapter.getNumberSeason();
        } 
    }

    public ChapterSeen findChapter(List<ChapterSeen> chapters, int numSeason, int numChapter){
        ChapterSeen cs = null;
        for(int i=0; i<chapters.size(); i++){
            cs = chapters.get(i);
            int season = chapters.get(i).getNumberSeason();
            int chap = chapters.get(i).getNumberChapter();
            if(season==numSeason && chap==numChapter)
                return cs;
        }
        return cs;
    }

    public void addChapterSeen(int season, int chapter){
        List<ChapterSeen> chapters = this.getUserChapters();
        ChapterSeen cs = findChapter(chapters, season, chapter);
        cs.setState(ChapterState.SEEN);
    }

    public List<ChapterSeen> getChapterForSeason(int season){ 
        List<ChapterSeen> lista = new ArrayList<>(); 
        for(ChapterSeen cs : this.getUserChapters()){ 
            if(cs.getNumberSeason()==season) 
                lista.add(cs); 
        } 
        return lista; 
    }

    @Override
    public boolean equals(Object o) {
        if (o == this)
            return true;
        if (!(o instanceof SerieUser)) {
            return false;
        }
        SerieUser serieU = (SerieUser) o;
        return Objects.equals(id, serieU.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}

