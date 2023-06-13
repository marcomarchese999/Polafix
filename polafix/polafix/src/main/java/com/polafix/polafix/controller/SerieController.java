package com.polafix.polafix.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.fasterxml.jackson.annotation.JsonView;
import com.polafix.polafix.pojos.Serie;
 
 
@RestController 
@CrossOrigin("*") 
@RequestMapping("/series") 
public class SerieController { 
 
    @Autowired 
    private SerieService serieService; 
     
    @GetMapping("")
    @JsonView({Views.SerieDescription.class})
    public ResponseEntity<List<Serie>> getSerieByName(@RequestParam String name) {
        List<Serie> l = serieService.getSerieByName(name);
        if(l!=null){
            return ResponseEntity.ok(l);
        }else{
            return ResponseEntity.badRequest().build();
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<Serie> getSerieById(@PathVariable Long id) {
        Serie s = serieService.getSerieById(id);
        if(s!=null){
            return ResponseEntity.ok(s);
        }else{
            return ResponseEntity.notFound().build();
        }
    }

    
    /*
    OPERACIONES PARA EL SISTEMA, NO UTILIZADAS EN LAS INTERFACES DE LA DESCRIPCION DEL SISTEMA
    
    @GetMapping("") 
    public List<Serie> getAllSeries() { 
        return serieService.getAllSeries(); 
    }
 
    @PostMapping("") 
    public Serie createSerie(@RequestBody Serie serie) { 
        return serieService.createSerie(serie); 
    } 
 
    @DeleteMapping("/{name}") 
    public boolean deleteSerie(@PathVariable Long id) { 
        return serieService.deleteSerie(id); 
    } */ 
}

