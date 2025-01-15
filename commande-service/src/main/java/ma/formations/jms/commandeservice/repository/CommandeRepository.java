package ma.formations.jms.commandeservice.repository;

import ma.formations.jms.commandeservice.entities.Commande;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;
import java.util.Date;

public interface CommandeRepository extends JpaRepository<Commande, Long> {
    List<Commande> findByDateAfter(Date date); // Pour afficher les commandes reçues récemment
}

