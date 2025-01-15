package com.example.commandeservice2;

import com.example.commandeservice2.client.ProductRestProduit;
import com.example.commandeservice2.entities.Commande2;
import com.example.commandeservice2.entities.Produit;
import com.example.commandeservice2.repository.CommandeRepository2;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;

import java.util.Date;

@SpringBootApplication
@EnableDiscoveryClient
@EnableFeignClients
public class CommandeService2Application {

	public static void main(String[] args) {
		SpringApplication.run(CommandeService2Application.class, args);
	}

	@Bean
	CommandLineRunner commandLineRunner(CommandeRepository2 commandeRepository, ProductRestProduit produitClient) {
		return args -> {
			// Récupérer les produits via Feign Client
			Produit produit1 = produitClient.getProduitById(1L);
			Produit produit2 = produitClient.getProduitById(2L);

			if (produit1 != null) {
				commandeRepository.save(Commande2.builder()
						.description("Ordinateur portable")
						.quantite(1)
						.date(new Date())
						.montant(produit1.getPrix() * 1) // Calcul du montant
						.idProduit(produit1.getIdProduit()) // Stocker l'ID produit
						.build());
			}

			if (produit2 != null) {
				commandeRepository.save(Commande2.builder()
						.description("Smartphone")
						.quantite(2)
						.date(new Date())
						.montant(produit2.getPrix() * 2) // Calcul du montant
						.idProduit(produit2.getIdProduit()) // Stocker l'ID produit
						.build());
			}

			// Affichage des commandes
			commandeRepository.findAll().forEach(commande -> {
				System.out.println("---------------------------------------");
				System.out.println("ID: " + commande.getId());
				System.out.println("Description: " + commande.getDescription());
				System.out.println("Quantité: " + commande.getQuantite());
				System.out.println("Date: " + commande.getDate());
				System.out.println("Montant: " + commande.getMontant());
				System.out.println("ID Produit: " + commande.getIdProduit());
			});
		};
	}
}
