package com.pqd.integration;

import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.util.TestPropertyValues;
import org.springframework.context.ApplicationContextInitializer;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.test.context.ContextConfiguration;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Testcontainers;

@Testcontainers
@SpringBootTest
@ContextConfiguration(initializers = TestContainerBase.Initializer.class)
public class TestContainerBase {
    private static final PostgreSQLContainer POSTGRE_SQL_CONTAINER;

    static {
        POSTGRE_SQL_CONTAINER = new PostgreSQLContainer();
        POSTGRE_SQL_CONTAINER.start();
        // If it fails then check testcontainer versions (org.testcontainers from build.gradle) -
        // maybe they don't provide image from dockerhub anymore (happened before when they moved from Quay.io to Docker Hub)
        // https://github.com/testcontainers/testcontainers-java/issues/3574
    }


    public static class Initializer implements
                                    ApplicationContextInitializer<ConfigurableApplicationContext> {

        @Override
        public void initialize(ConfigurableApplicationContext context) {
            TestPropertyValues.of(
                    "spring.datasource.url=" + POSTGRE_SQL_CONTAINER.getJdbcUrl(),
                    "spring.datasource.username=" + POSTGRE_SQL_CONTAINER.getUsername(),
                    "spring.datasource.password=" + POSTGRE_SQL_CONTAINER.getPassword()
                                 ).applyTo(context.getEnvironment());
        }
    }

}
