package com.chisw.dynamicFunctions.configuration;

import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@Configuration
@ComponentScan("com.chisw.dynamicFunctions")
@EnableJpaRepositories("com.chisw.dynamicFunctions.persistence.jpa.repository")
@EntityScan(basePackages = "com.chisw.dynamicFunctions.entity")
public class AppConfiguration {
}
