package com.uni.project.Config;

import javax.sql.DataSource;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;

/**
 * DatabaseConfig
 */
@Configuration
public class DatabaseConfig {

	@Bean
	public JdbcTemplate jdbcTemplate(final DataSource dataSource) {
		return new JdbcTemplate(dataSource);
	}
}
