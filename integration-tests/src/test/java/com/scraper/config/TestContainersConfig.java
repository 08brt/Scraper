package com.scraper.config;

import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.utility.DockerImageName;

@TestConfiguration
public class TestContainersConfig {

    @Bean
    public PostgreSQLContainer<?> postgreSQLContainer() {
        DockerImageName postgresImage = DockerImageName.parse("postgres:13");
        PostgreSQLContainer<?> postgresContainer = new PostgreSQLContainer<>(postgresImage)
                .withDatabaseName("testdb")
                .withUsername("test")
                .withPassword("test");
        postgresContainer.start();
        return postgresContainer;
    }
}