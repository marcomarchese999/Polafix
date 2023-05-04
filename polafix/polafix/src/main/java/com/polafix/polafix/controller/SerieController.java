package com.polafix.polafix.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.polafix.polafix.pojos.Serie;

@RestController
@RequestMapping("/series")
public class SerieController {

    @Autowired
    private SerieService serieService;
    
    @GetMapping("")
    public List<Serie> getAllSerie() {
        return serieService.getAllSerie();
    }

    @GetMapping("/{name}")
    public List<Serie> getSerieByName(@PathVariable String name) {
        return serieService.getSerieByName(name);
    }

    @PostMapping("")
    public Serie createSerie(@RequestBody Serie serie) {
        return serieService.createSerie(serie);
    }

    @DeleteMapping("/{name}")
    public boolean deleteSerie(@PathVariable Long id) {
        return serieService.deleteSerie(id);
    }

}