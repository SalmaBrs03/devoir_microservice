package com.example.produitservice;

import com.example.produitservice.entities.Produit;
import com.example.produitservice.repository.ProduitRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
@EnableDiscoveryClient
public class ProduitServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(ProduitServiceApplication.class, args);
	}

	@Bean
	CommandLineRunner start(ProduitRepository produitRepository) {
		return args -> {
			// Utilisation de la méthode statique createProduit
			produitRepository.save(Produit.createProduit("Produit 1", "test 1", 100.0, 50));
			produitRepository.save(Produit.createProduit("Produit 2", "test 2", 200.0, 30));
			produitRepository.save(Produit.createProduit("Produit 3", "test 3", 300.0, 20));
			produitRepository.save(Produit.createProduit("Produit 4", "test 4", 400.0, 10));

			// Afficher tous les produits enregistrés dans la base de données
			produitRepository.findAll().forEach(produit -> {
				System.out.println(produit);
			});
		};
	}
}
