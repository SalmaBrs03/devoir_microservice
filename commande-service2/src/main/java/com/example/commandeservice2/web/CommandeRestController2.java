package com.example.commandeservice2.web;

import com.example.commandeservice2.client.ProductRestProduit;
import com.example.commandeservice2.entities.Commande2;
import com.example.commandeservice2.entities.Produit;
import com.example.commandeservice2.repository.CommandeRepository2;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@RestController
@RequestMapping("/api/commandes2")
public class CommandeRestController2 {

    private final CommandeRepository2 commandeRepository2;
    private final ProductRestProduit produitClient;
    private static final Logger log = LoggerFactory.getLogger(CommandeRestController2.class);


    @Value("${mes-config-ms.commandes-last:10}")
    private int commandesLast;

    public CommandeRestController2(CommandeRepository2 commandeRepository2, ProductRestProduit produitClient) {
        this.commandeRepository2 = commandeRepository2;
        this.produitClient = produitClient;
    }

    @GetMapping
    public List<Commande2> getAllCommandes() {
        // Récupérer toutes les commandes
        List<Commande2> commandes = commandeRepository2.findAll();

        // Récupérer les produits pour chaque commande
        for (Commande2 commande : commandes) {
            Long produitId = commande.getIdProduit();
            if (produitId != null && produitId > 0) {
                try {
                    Produit produit = produitClient.getProduitById(produitId); // Appel FeignClient pour récupérer le produit
                    commande.setProduit(produit); // Associer le produit à la commande
                } catch (Exception e) {
                    log.error("Erreur lors de la récupération du produit pour la commande {}", commande.getId(), e);
                    commande.setProduit(null); // Si l'appel échoue, associer un produit null
                }
            }
        }

        return commandes; // Retourner la liste des commandes avec les produits associés
    }


    // Cette méthode récupère seulement la commande par son ID

    @GetMapping("/simple/{id}")
    public ResponseEntity<?> getCommandeById(@PathVariable Long id) {
        Commande2 commande2 = commandeRepository2.findById(id).orElse(null);
        if (commande2 == null) {
            return ResponseEntity.status(404).body(Map.of("errorMessage", "Commande not found"));
        }

        return ResponseEntity.ok(commande2);
    }

    @GetMapping("/with-produit/{id}")
    public ResponseEntity<?> getCommandeWithProduit(@PathVariable Long id) {
        // Récupérer la commande avec son ID
        Commande2 commande2 = commandeRepository2.findById(id).orElse(null);

        if (commande2 == null) {
            log.warn("Commande non trouvée avec l'ID: {}", id);
            return ResponseEntity.status(404).body(Map.of("errorMessage", "Commande not found"));
        }

        Long produitId = commande2.getIdProduit();
        if (produitId == null || produitId <= 0) {
            log.warn("Produit ID invalide dans la commande {}", id);
            return ResponseEntity.status(404).body(Map.of("errorMessage", "Produit ID invalide dans la commande"));
        }

        log.info("Tentative de récupération du produit avec ID: {}", produitId);

        // Récupérer le produit via Feign client
        try {
            Produit produit = produitClient.getProduitById(produitId);
            log.info("Produit récupéré: {}", produit);
            commande2.setProduit(produit);
            return ResponseEntity.ok(commande2);
        } catch (Exception e) {
            log.error("Erreur lors de la récupération du produit", e);
            return ResponseEntity.status(500).body(Map.of("errorMessage", "Erreur interne du serveur"));
        }
    }



    @GetMapping("/recent")
    public List<Commande2> getRecentCommandes() {
        LocalDate currentDate = LocalDate.now();
        LocalDate cutoffDate = currentDate.minusDays(commandesLast); // Date limite pour les commandes récentes
        Date cutoffDateAsDate = Date.from(cutoffDate.atStartOfDay(ZoneId.systemDefault()).toInstant());

        // Récupérer toutes les commandes et filtrer celles qui sont après la date limite
        List<Commande2> allCommandes = commandeRepository2.findAll();
        return allCommandes.stream()
                .filter(commande -> commande.getDate().after(cutoffDateAsDate))
                .collect(Collectors.toList());
    }
}