package com.polafix.polafix.controller;

import java.util.List;
import com.polafix.polafix.pojos.Serie;

public interface SerieService {
    List<Serie> getSerieByName(String name);
    Serie getSerieById(Long id);

    /*
    List<Serie> getAllSerie();
    Serie createSerie(Serie name);
    boolean deleteSerie(Long id);
    */
}