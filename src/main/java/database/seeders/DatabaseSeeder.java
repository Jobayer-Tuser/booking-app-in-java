package database.seeders;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import java.util.List;

@Slf4j
@Component
@Profile("dev")
@RequiredArgsConstructor
public class DatabaseSeeder implements ApplicationRunner {

    private final List<Seeder> seeders;

    @Override
    public void run(ApplicationArguments args) throws Exception {
        if (args.containsOption("seed")){
            log.info("Starting database seeding...");
            try {
                seeders.forEach(Seeder::run);
                log.info("Database seeding completed successfully.");
            } catch (Exception e) {
                log.error("Error during database seeding: {}", e.getMessage());
            }
        }
    }
}
