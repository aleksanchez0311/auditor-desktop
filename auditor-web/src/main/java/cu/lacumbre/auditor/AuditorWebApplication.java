package cu.lacumbre.auditor;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

/**
 * Aplicación principal del sistema de auditoría web La Cumbre
 * 
 * @author La Cumbre Team
 * @version 2.0.0
 */
@SpringBootApplication
@EnableJpaAuditing
public class AuditorWebApplication {

    public static void main(String[] args) {
        SpringApplication.run(AuditorWebApplication.class, args);
    }
}