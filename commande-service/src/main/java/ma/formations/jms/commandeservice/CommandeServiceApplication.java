package ma.formations.jms.commandeservice;

import ma.formations.jms.commandeservice.entities.Commande;
import ma.formations.jms.commandeservice.repository.CommandeRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;


@SpringBootApplication
public class CommandeServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(CommandeServiceApplication.class, args);
    }

    @Bean
    CommandLineRunner commandLineRunner(CommandeRepository commandeRepository) {
        return args -> {
            // Première commande
            commandeRepository.save(Commande.builder()
                    .description("Ordinateur portable")
                    .quantite(1)
                    .date(new Date()) // Date actuelle
                    .montant(999.99)
                    .build());

            // Deuxième commande
            commandeRepository.save(Commande.builder()
                    .description("Smartphone")
                    .quantite(2)
                    .date(new Date())
                    .montant(599.99)
                    .build());

            // Troisième commande
            commandeRepository.save(Commande.builder()
                    .description("Écouteurs sans fil")
                    .quantite(3)
                    .date(new Date())
                    .montant(149.99)
                    .build());

            // 20 commande
            commandeRepository.save(Commande.builder()
                    .description("Smartphone")
                    .quantite(2)
                    .date(Date.from(LocalDate.of(LocalDate.now().getYear(), 1, 1)
                            .atStartOfDay(ZoneId.systemDefault())
                            .toInstant()))
                    .montant(599.99)
                    .build());

            // Affichage des commandes pour vérification
            commandeRepository.findAll().forEach(commande -> {
                System.out.println("---------------------------------------");
                System.out.println("ID: " + commande.getId());
                System.out.println("Description: " + commande.getDescription());
                System.out.println("Quantité: " + commande.getQuantite());
                System.out.println("Date: " + commande.getDate());
                System.out.println("Montant: " + commande.getMontant());
            });
        };
    }
}