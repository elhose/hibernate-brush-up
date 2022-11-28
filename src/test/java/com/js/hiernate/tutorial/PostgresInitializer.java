package com.js.hiernate.tutorial;

import org.junit.jupiter.api.BeforeAll;
import org.springframework.boot.test.util.TestPropertyValues;
import org.springframework.context.ApplicationContextInitializer;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.test.context.ContextConfiguration;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.utility.DockerImageName;

@ContextConfiguration(initializers = {PostgresInitializer.PostgresqlConfigurer.class})
public class PostgresInitializer  {

    private static final PostgreSQLContainer<?> postgreSQLContainer = new PostgreSQLContainer<>(
            DockerImageName.parse("postgres:14-alpine"));

    @BeforeAll
    static void asd() {
        postgreSQLContainer.start();
    }


    static class PostgresqlConfigurer implements ApplicationContextInitializer<ConfigurableApplicationContext> {
        @Override
        public void initialize(ConfigurableApplicationContext applicationContext) {
            TestPropertyValues.of(
                    "spring.datasource.url=" + postgreSQLContainer.getJdbcUrl(),
                    "spring.datasource.username= " + postgreSQLContainer.getUsername(),
                    "spring.datasource.password=" + postgreSQLContainer.getPassword()
                                 ).applyTo(applicationContext);

        }
    }
}
