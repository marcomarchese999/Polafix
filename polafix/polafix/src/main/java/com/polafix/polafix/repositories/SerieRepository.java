package com.polafix.polafix.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.polafix.polafix.pojos.*;

@Repository
public interface SerieRepository extends JpaRepository<Serie, String> {
    Serie findByName(String name);
}
