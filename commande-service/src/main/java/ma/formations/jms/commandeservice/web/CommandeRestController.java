package ma.formations.jms.commandeservice.web;

import ma.formations.jms.commandeservice.entities.Commande;
import ma.formations.jms.commandeservice.repository.CommandeRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/commandes")
public class CommandeRestController {
    private final CommandeRepository commandeRepository;

    @Value("${mes-config-ms.commandes-last:10}")
    private int commandesLast;

    public CommandeRestController(CommandeRepository commandeRepository) {
        this.commandeRepository = commandeRepository;
    }

    @GetMapping
    public List<Commande> getAllCommandes() {
        return commandeRepository.findAll();
    }

    @PostMapping
    public Commande createCommande(@RequestBody Commande commande) {
        commande.setDate(new Date());// Utilisez LocalDate pour la date actuelle
        return commandeRepository.save(commande);
    }

    @GetMapping("/recent")
    public List<Commande> getRecentCommandes() {
        LocalDate currentDate = LocalDate.now();
        LocalDate cutoffDate = currentDate.minusDays(commandesLast); // Date limite pour les commandes récentes
        Date cutoffDateAsDate = Date.from(cutoffDate.atStartOfDay(ZoneId.systemDefault()).toInstant());

        // Récupérer toutes les commandes et filtrer celles qui sont après la date limite
        List<Commande> allCommandes = commandeRepository.findAll();
        return allCommandes.stream()
                .filter(commande -> commande.getDate().after(cutoffDateAsDate))
                .collect(Collectors.toList());
    }


}
