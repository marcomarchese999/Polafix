package com.polafix.polafix.pojos;

import java.util.Objects;

import javax.persistence.Embeddable;

import com.fasterxml.jackson.annotation.JsonProperty;

@Embeddable
public class ChapterSeen {
    
    @JsonProperty("state")
    private ChapterState state;
    @JsonProperty("numSeason")
    private int numberSeason;
    @JsonProperty("numChapter")
    private int numberChapter;
    @JsonProperty("title")
    private String title;
    @JsonProperty("description")
    private String description;

    public ChapterSeen() {}

    public ChapterSeen(ChapterState state, int numberSeason, int numberChapter, String title, String description) {
        this.state = state;
        this.numberSeason = numberSeason;
        this.numberChapter = numberChapter;
        this.title=title;
        this.description = description;
    }

    public int getNumberSeason() {
        return this.numberSeason;
    }

    public void setNumberSeason(int numberSeason) {
        this.numberSeason = numberSeason;
    }

    public int getNumberChapter() {
        return this.numberChapter;
    }

    public void setNumberChapter(int numberChapter) {
        this.numberChapter = numberChapter;
    }

    
    public ChapterState getState() {
        return state;
    }
    public void setState(ChapterState state) {
        this.state = state;
    }

    public String getTitle() {
        return this.title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return this.description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public boolean equals(Object o) {
        if (o == this)
            return true;
        if (!(o instanceof ChapterSeen)) {
            return false;
        }
        ChapterSeen chapterSeen = (ChapterSeen) o;
        return Objects.equals(state, chapterSeen.state) && numberSeason == chapterSeen.numberSeason && numberChapter == chapterSeen.numberChapter;
    }

    @Override
    public int hashCode() {
        return Objects.hash(state, numberSeason, numberChapter);
    }
    
}
