package com.polafix.polafix.controller;

import java.util.List;
import com.polafix.polafix.pojos.Serie;

public interface SerieService {
    List<Serie> getAllSerie();
    List<Serie> getSerieByName(String name);
    Serie getSerieById(Long id);
    Serie createSerie(Serie name);
    boolean deleteSerie(Long id);
}