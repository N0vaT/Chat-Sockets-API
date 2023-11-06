package edu.school21.sockets.config;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.sql.DataSource;

@Configuration
@PropertySource("classpath:db.properties")
public class SocketsApplicationConfig {

    @Bean
    public HikariConfig hikariConfig(Environment env){
        HikariConfig hikariConfig = new HikariConfig();
        hikariConfig.setMaximumPoolSize(10);
        hikariConfig.setDataSourceClassName("org.postgresql.ds.PGSimpleDataSource");
        hikariConfig.setMaxLifetime(3000);
        hikariConfig.setIdleTimeout(3000);
        hikariConfig.setJdbcUrl(env.getProperty("db.url"));
        hikariConfig.setUsername(env.getProperty("db.user"));
        hikariConfig.setPassword(env.getProperty("db.password"));
        return hikariConfig;
    }
    @Bean
    public HikariDataSource hikariDataSource(HikariConfig hikariConfig){
        return new HikariDataSource(hikariConfig);
    }
    @Bean
    public DataSource dataSource(HikariDataSource hikariDataSource){
        return hikariDataSource;
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
