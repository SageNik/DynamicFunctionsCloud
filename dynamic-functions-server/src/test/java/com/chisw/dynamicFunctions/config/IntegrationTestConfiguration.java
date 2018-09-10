package com.chisw.dynamicFunctions.config;

import com.chisw.dynamicFunctions.entity.Function;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

/**
 * Configuration for integration tests
 */
@EnableAutoConfiguration(exclude = {DataSourceAutoConfiguration.class})
@ComponentScan("com.chisw.microservices.nodes")
@EnableJpaRepositories("com.chisw.microservices.nodes.persistence.jpa.repository")
@EntityScan(basePackageClasses =Function.class)
public class IntegrationTestConfiguration {
}
