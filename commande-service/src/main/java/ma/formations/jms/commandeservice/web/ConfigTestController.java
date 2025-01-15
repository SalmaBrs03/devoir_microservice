package ma.formations.jms.commandeservice.web;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RefreshScope
@RestController
public class ConfigTestController {
    @Value("${mes-config-ms.commandes-last:10}")
    private int commandesLast;

    @GetMapping("/config")
    public String getConfig() {
        return "Commandes Last Days Configured: " + commandesLast;
    }
}
