package org.booking.console.commands;

import database.seeders.DatabaseSeeder;
import org.springframework.shell.core.command.annotation.Command;
import org.springframework.shell.core.command.annotation.Option;
import org.springframework.stereotype.Component;

@Component
public class SeeedCommand {

    private final DatabaseSeeder databaseSeeder;

    public SeeedCommand(DatabaseSeeder databaseSeeder) {
        this.databaseSeeder = databaseSeeder;
    }

    @Command(name = "db:seed", description = "Run a data seed operation", group = "Database Commands")
    public void applyDataSeeding(@Option(shortName = 's', longName = "seed", description = "Name of the Seeder class") String name) {
        databaseSeeder.runMigration(name);
    }
}
