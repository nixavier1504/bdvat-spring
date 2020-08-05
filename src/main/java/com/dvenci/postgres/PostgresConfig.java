package com.dvenci.postgres;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@Configuration
@EnableJpaRepositories(basePackages = "com.dvenci.postgres")
public class PostgresConfig {

}
