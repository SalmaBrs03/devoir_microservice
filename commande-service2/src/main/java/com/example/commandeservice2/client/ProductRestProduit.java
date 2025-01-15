package com.example.commandeservice2.client;

import com.example.commandeservice2.entities.Produit;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;
@FeignClient(name = "produit-service")
public interface ProductRestProduit {
    @GetMapping("/api/produits/{id}")
    Produit getProduitById(@PathVariable Long id);
}
/*
@FeignClient(name = "PRODUIT-SERVICE")
public interface ProductRestProduit {

    @GetMapping("/produits/{id}")
    @CircuitBreaker(name = "produitService", fallbackMethod = "getDefaultProduit")
    Produit getProduitById(@PathVariable Long id);

    // Méthode fallback pour gérer les erreurs de produit
    default Produit getDefaultProduit(Long id, Exception e) {
        return Produit.builder()
                .idProduit(id)
                .nom("Produit Indisponible")
                .description("Description non disponible")
                .prix(0.0)
                .quantiteEnStock(0)
                .build();
    }
}*/
