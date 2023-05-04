package com.polafix.polafix.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.polafix.polafix.pojos.*;

@Repository
public interface SerieRepository extends JpaRepository<Serie, Long> {
    List<Serie> findByName(String name);
}
