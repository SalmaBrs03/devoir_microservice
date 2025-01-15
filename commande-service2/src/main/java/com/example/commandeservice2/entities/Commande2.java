package com.example.commandeservice2.entities;

import jakarta.persistence.*;
import lombok.*;

import java.util.Date;

@Entity
@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Commande2 {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String description;
    private int quantite;
    private Date date;
    private double montant;
    private Long idProduit;

    @Transient // Ne sera pas persisté en base
    private Produit produit;

    public void setDate(Date date) {
        this.date = date;
    }
}
