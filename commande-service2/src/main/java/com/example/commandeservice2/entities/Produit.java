package com.example.commandeservice2.entities;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Produit {
    private Long idProduit;
    private String nom;
    private String description;
    private double prix;
    private int quantiteEnStock;

}
