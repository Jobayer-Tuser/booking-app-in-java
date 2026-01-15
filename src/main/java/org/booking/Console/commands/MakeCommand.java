package org.booking.Console.commands;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.shell.core.command.annotation.Command;
import org.springframework.shell.core.command.annotation.Option;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;


@Component
public class MakeCommand {

    @Value("${spring.base.package}")
    private String BASE_PACKAGE;

    @Value("${spring.base.path}")
    private String BASE_PATH;

    @Command(name = "make:model", description = "Create a new JPA Entity", group = "Make Commands")
    public String makeModel(
            @Option(shortName = 'm', longName = "model", description = "Name of the model") String name) {
        return createFile(name, "Entity", this::generateModelContent);
    }

    @Command(name = "make:service", description = "Create a new Service", group = "Make Commands")
    public String makeService(
            @Option(shortName = 's', longName = "service", description = "Name of the service") String name) {
        return createFile(name, "Service", this::generateServiceContent);
    }

    @Command(name = "make:controller", description = "Create a new RestController", group = "Make Commands")
    public String makeController(
            @Option(shortName = 'c', longName = "controller", description = "Name of the controller") String name) {
        return createFile(name, "Controller", this::generateControllerContent);
    }

    @Command(name = "make:repository", description = "Create a new Repository", group = "Make Commands")
    public String makeRepository(
            @Option(shortName = 'r', longName = "repository", description = "Name of the repository") String name) {
        return createFile(name, "Repository", this::generateRepositoryContent);
    }

    @Command(name = "make:enum", description = "Create a new Enum", group = "Make Commands")
    public String makeEnum(@Option(shortName = 'e', longName = "enum", description = "Name of the enum") String name) {
        return createFile(name, "Enum", this::generateEnumContent);
    }

    @Command(name = "make:exception", description = "Create a new Exception", group = "Make Commands")
    public String makeException(
            @Option(shortName = 'e', longName = "exception", description = "Name of the exception") String name) {
        return createFile(name, "Exception", this::generateExceptionContent);
    }

    @Command(name = "make:interface", description = "Create a new Interface", group = "Make Commands")
    public String makeInterface(
            @Option(shortName = 'i', longName = "interface", description = "Name of the interface") String name) {
        return createFile(name, "Interface", this::generateInterfaceContent);
    }

    @Command(name = "make:dto", description = "Create a new DTO", group = "Make Commands")
    public String makeDTO(@Option(shortName = 'd', longName = "dto", description = "Name of the dto") String name) {
        return createFile(name, "DTO", this::generateDTOContent);
    }

    private String createFile(String name, String type, ContentGenerator generator) {
        String packageName = getPackageName(name); // e.g., org.booking.users
        String simpleName = getSimpleName(name); // e.g., User
        String className = getString(type, simpleName);

        // Directory: properties -> src/main/java/org/booking/properties
        // If name is "User", package is "users".
        // distinct logic: User -> users
        // NOTE: User might want specific package logic. Let's try to infer or just use
        // lower case plural.

        String relativePath = camelToSnake(simpleName) + "s";
        String fullPath = BASE_PATH + "/" + relativePath;

        try {
            Files.createDirectories(Paths.get(fullPath));
            File file = new File(fullPath + "/" + className + ".java");
            if (file.exists()) {
                return "Error: File " + file.getPath() + " already exists.";
            }

            try (FileWriter writer = new FileWriter(file)) {
                writer.write(generator.generate(packageName, className));
            }
            return "Created " + type + ": " + file.getPath();
        } catch (IOException e) {
            return "Error creating file: " + e.getMessage();
        }
    }

    private static String getString(String type, String simpleName) {
        String className = simpleName;

        if (!type.equals("Entity") && !type.equals("Enum")) {
            // For Service/Controller/Repository, we might append the type if not already
            // present,
            // but user usually provides full name like UserService.
            // Let's assume user provides "User" and we append specific suffix OR user
            // provides "UserService".
            // Standard Laravel `make:controller User` creates `UserController`.
            // Let's check if name ends with Type.
            if (!className.endsWith(type)) {
                className += type; // User -> UserService
            }
        }
        return className;
    }

    private String getSimpleName(String name) {
        return name; // Simplified for now, assuming just "User" passed
    }

    private String getPackageName(String name) {
        return BASE_PACKAGE + "." + camelToSnake(name) + "s";
    }

    private String camelToSnake(String str) {
        // Simple lowercase for now as is common in Java packages (e.g. User -> user,
        // but plural -> users)
        return str.toLowerCase();
    }

    @FunctionalInterface
    interface ContentGenerator {
        String generate(String packageName, String className);
    }

    private String generateModelContent(String packageName, String className) {
        return """
                package %s;

                import jakarta.persistence.*;
                import lombok.*;
                
                import java.time.Instant;

                @Entity
                @Table(name = "%s")
                @Data
                @NoArgsConstructor
                @AllArgsConstructor
                @Builder
                public class %s {

                    @Id
                    @GeneratedValue(strategy = GenerationType.IDENTITY)
                    private Long id;

                    private Instant createdAt;
                    private Instant updatedAt;
                }
                """.formatted(packageName, camelToSnake(className), className);
    }

    private String generateRepositoryContent(String packageName, String className) {
        return """
                package %s;

                import org.springframework.data.jpa.repository.JpaRepository;
                import org.springframework.stereotype.Repository;

                @Repository
                public interface %s extends JpaRepository<%s, Long> {
                }
                """.formatted(packageName, className, className.replace("Repository", ""));
    }

    private String generateServiceContent(String packageName, String className) {
        return """
                package %s;

                import org.springframework.stereotype.Service;
                import lombok.RequiredArgsConstructor;

                @Service
                @RequiredArgsConstructor
                public class %s {
                }
                """.formatted(packageName, className);
    }

    private String generateControllerContent(String packageName, String className) {
        String entityName = className.replace("Controller", "");
        String entitySimpleName = camelToSnake(entityName);
        String entityPluralName = entitySimpleName + "s";
        return """
                package %s;

                import org.springframework.web.bind.annotation.*;
                import lombok.RequiredArgsConstructor;

                @RestController
                @RequestMapping("/api/v1/%s")
                @RequiredArgsConstructor
                public class %s {
                }
                """.formatted(packageName, entityPluralName, className);
    }

    private String generateEnumContent(String packageName, String className) {
        return """
                package %s;

                public enum %s {
                }
                """.formatted(packageName, className);
    }

    private String generateExceptionContent(String packageName, String className) {
        return """
                package %s;

                public class %s extends RuntimeException {
                    public %s(String message) {
                        super(message);
                    }
                }
                """.formatted(packageName, className, className);
    }

    private String generateDTOContent(String packageName, String className) {
        return """
                package %s;

                public record %s() {
                }
                """.formatted(packageName, className);
    }

    private String generateInterfaceContent(String packageName, String className) {
        return """
                package %s;

                public interface %s {
                }
                """.formatted(packageName, className);
    }
}
