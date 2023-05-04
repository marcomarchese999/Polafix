package com.polafix.polafix.controller;

import java.util.List;

import com.polafix.polafix.pojos.*;

public class SerieUserResponse {
    
    String title;
    int currentSeason;
    Type type;
    List<ChapterSeen> lista;
    
    public SerieUserResponse(String title, int currentSeason, Type type, List<ChapterSeen> lista){
        this.title=title;
        this.currentSeason=currentSeason;
        this.type=type;
        this.lista=lista;
    }

    public String getTitle() {
        return this.title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getCurrentSeason() {
        return this.currentSeason;
    }

    public void setCurrentSeason(int currentSeason) {
        this.currentSeason = currentSeason;
    }

    public Type getType() {
        return this.type;
    }

    public void setType(Type type) {
        this.type = type;
    }

    public List<ChapterSeen> getLista() {
        return this.lista;
    }

    public void setLista(List<ChapterSeen> lista) {
        this.lista = lista;
    }
}