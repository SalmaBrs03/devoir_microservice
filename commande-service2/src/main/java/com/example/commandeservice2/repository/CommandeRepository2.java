package com.example.commandeservice2.repository;


import com.example.commandeservice2.entities.Commande2;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Date;
import java.util.List;

public interface CommandeRepository2 extends JpaRepository<Commande2, Long> {
    List<Commande2> findByDateAfter(Date date);
}