package com.polafix.polafix.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.polafix.polafix.pojos.*;
import com.polafix.polafix.repositories.SerieRepository;

@Service
public class SerieServiceImpl implements SerieService{

    @Autowired
    private SerieRepository serieRepository;

    @Override
    public List<Serie> getAllSerie() {
        return serieRepository.findAll();
    }

    @Override
    public List<Serie> getSerieByName(String name) {
        List<Serie> series = serieRepository.findByName(name);
        if(!series.isEmpty())
            return series;
        else{
            return getSerieByChar(name);
        }
    }

    private List<Serie> getSerieByChar(String c) {
        List<Serie> all = serieRepository.findAll();
        List<Serie> serieChart = new ArrayList<Serie>();
        for (Serie serie : all) {
            if(serie.getName().startsWith(c)){
                serieChart.add(serie);
            }
        }
        return serieChart;
    }

    @Override
    public Serie createSerie(Serie serie) {
        return serieRepository.save(serie);
    }

    @Override
    public boolean deleteSerie(Long id) {
        boolean deleted = false;
        if (serieRepository.existsById(id)) {
            serieRepository.deleteById(id);
            deleted = true;
        }
        return deleted;
    }

    @Override
    public Serie getSerieById(Long id) {
        return serieRepository.findById(id).orElse(null);
    }
}