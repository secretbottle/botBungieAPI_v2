package net.bungie.config;

import net.bungie.telegram.Bot;
import org.hibernate.cfg.AvailableSettings;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;

import java.util.HashMap;
import java.util.Map;

@Configuration
@ComponentScan
@EnableJpaRepositories("net.bungie.repository")
@PropertySource("classpath:db/hsqldb.properties")
@PropertySource("classpath:telegramBotAPI-Key.properties")
@PropertySource("classpath:bungieAPI-Key.properties")
public class ApplicationConfig {
    @Value("${database.url}")
    private String url;
    @Value("${database.username}")
    private String username;
    @Value("${database.password}")
    private String password;


    @Bean
    public Bot bot(){
        return new Bot();
    }

    @Bean
    public DriverManagerDataSource dataSource(){
        DriverManagerDataSource dataSource = new DriverManagerDataSource();
        dataSource.setDriverClassName("org.hsqldb.jdbcDriver");
        dataSource.setUrl(url);
        dataSource.setUsername(username);
        dataSource.setPassword(password);
        return dataSource;
    }

    @Bean
    public LocalContainerEntityManagerFactoryBean entityManagerFactory(){
        LocalContainerEntityManagerFactoryBean entityManager = new LocalContainerEntityManagerFactoryBean();
        entityManager.setDataSource(dataSource());
        entityManager.setPackagesToScan("net.bungie.model");

        Map <String, Boolean> jpaPropertyMap = new HashMap<>();
        jpaPropertyMap.put(AvailableSettings.FORMAT_SQL, true);
        jpaPropertyMap.put(AvailableSettings.USE_SQL_COMMENTS, true);
        jpaPropertyMap.put(AvailableSettings.JPA_PROXY_COMPLIANCE, true);
        entityManager.setJpaPropertyMap(jpaPropertyMap);

        HibernateJpaVendorAdapter jpaVendorAdapter = new HibernateJpaVendorAdapter();
        jpaVendorAdapter.setShowSql(true);
        entityManager.setJpaVendorAdapter(jpaVendorAdapter);

        return entityManager;
    }

}
