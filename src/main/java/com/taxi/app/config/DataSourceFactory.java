//package com.taxi.app.config;
//
//import com.zaxxer.hikari.HikariConfig;
//import com.zaxxer.hikari.HikariDataSource;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//
//import javax.sql.DataSource;
//
//@Configuration
//public class DataSourceFactory {
//
//    private static final Logger logger = LoggerFactory.getLogger(DataSourceFactory.class);
//
//    @Bean
//    public DataSource dataSource() {
//        HikariConfig config = new HikariConfig();
//        config.setJdbcUrl("jdbc:mariadb://localhost:3306/taxi");
//        config.setUsername("root");
//        config.setPassword("sa");
//        config.setDriverClassName("org.mariadb.jdbc.Driver");
//        config.setMaximumPoolSize(10);
//        config.setMinimumIdle(2);
//        config.setIdleTimeout(30000);
//        config.setConnectionTimeout(20000);
//        config.setMaxLifetime(1800000);
//
//        logger.info("✅ DataSource inicializado para MariaDB em {}", config.getJdbcUrl());
//
//        return new HikariDataSource(config);
//    }
//}

package com.taxi.app.config;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;

@Configuration
public class DataSourceFactory {

    private static final Logger logger = LoggerFactory.getLogger(DataSourceFactory.class);

    @Bean
    public DataSource dataSource() {
        HikariConfig config = new HikariConfig();
        // URL do H2 em modo MEMORIA
        config.setJdbcUrl("jdbc:h2:mem:taxi;DB_CLOSE_DELAY=-1;DB_CLOSE_ON_EXIT=FALSE");
        config.setUsername("sa");
        config.setPassword("");
        config.setDriverClassName("org.h2.Driver");
        config.setMaximumPoolSize(10);
        config.setMinimumIdle(2);
        config.setIdleTimeout(30000);
        config.setConnectionTimeout(20000);
        config.setMaxLifetime(1800000);

        logger.info("✅ DataSource inicializado para H2 em {}", config.getJdbcUrl());

        return new HikariDataSource(config);
    }
}
