package com.example.commandeservice2.web;


import com.example.commandeservice2.repository.CommandeRepository2;
import org.springframework.boot.actuate.health.Health;
import org.springframework.boot.actuate.health.HealthIndicator;
import org.springframework.stereotype.Component;

@Component
public class CommandeHealthIndicator2 implements HealthIndicator {
    private final CommandeRepository2 commandeRepository;

    public CommandeHealthIndicator2(CommandeRepository2 commandeRepository) {
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