package com.polafix.polafix.pojos;

import java.util.Objects;

public class ChapterSeen {
    
    private ChapterState state;
    private int numberSeason;
    private int numberChapter;

    public ChapterSeen(ChapterState state, int numberSeason, int numberChapter) {
        this.state = state;
        this.numberSeason = numberSeason;
        this.numberChapter = numberChapter;
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
