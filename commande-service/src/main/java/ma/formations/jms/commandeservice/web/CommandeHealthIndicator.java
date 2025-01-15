package ma.formations.jms.commandeservice.web;

import ma.formations.jms.commandeservice.repository.CommandeRepository;
import org.springframework.boot.actuate.health.Health;
import org.springframework.boot.actuate.health.HealthIndicator;
import org.springframework.stereotype.Component;

@Component
public class CommandeHealthIndicator implements HealthIndicator {
    private final CommandeRepository commandeRepository;

    public CommandeHealthIndicator(CommandeRepository commandeRepository) {
        this.commandeRepository = commandeRepository;
    }

    @Override
    public Health health() {
        long count = commandeRepository.count();
        if (count > 0) {
            return Health.up().withDetail("Commandes count", count).build();
        } else {
            return Health.down().withDetail("Commandes count", count).build();
        }
    }
}
