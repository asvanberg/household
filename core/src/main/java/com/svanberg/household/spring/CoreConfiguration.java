package com.svanberg.household.spring;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.dao.support.PersistenceExceptionTranslator;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.jndi.JndiObjectFactoryBean;
import org.springframework.orm.hibernate4.HibernateExceptionTranslator;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;

/**
 * @author Andreas Svanberg (andreass) <andreas.svanberg@mensa.se>
 */
@Configuration
@EnableJpaRepositories("com.svanberg.household.repository")
@EnableTransactionManagement
@ComponentScan("com.svanberg.household")
@PropertySource("classpath:database.properties")
public class CoreConfiguration {

    @Value("${database.url}")       String url;
    @Value("${database.username}")  String username;
    @Value("${database.password}")  String password;
    @Value("${database.driver}")    String driver;

    @Bean
    public DataSource dataSource() {
        DriverManagerDataSource dataSource = new DriverManagerDataSource(url, username, password);
        dataSource.setDriverClassName(driver);
        return dataSource;
    }

    @Bean
    public JndiObjectFactoryBean jndiDataSource() throws Exception {
        JndiObjectFactoryBean jndi = new JndiObjectFactoryBean();
        jndi.setJndiName("java:comp/env/jdbc/household");
        jndi.setDefaultObject(dataSource());
        jndi.afterPropertiesSet();
        return jndi;
    }

    // Spring fails to load the application context if this is not the exact name of the bean
    @Bean(name = "entityManagerFactory")
    public EntityManagerFactory entityManagerFactory() throws Exception {
        HibernateJpaVendorAdapter adapter = new HibernateJpaVendorAdapter();
        adapter.setGenerateDdl(true);
        adapter.setShowSql(false);

        LocalContainerEntityManagerFactoryBean factory = new LocalContainerEntityManagerFactoryBean();
        factory.setJpaVendorAdapter(adapter);
        factory.setDataSource( (DataSource) jndiDataSource().getObject());
        factory.setPackagesToScan("com.svanberg.household.domain");
        factory.afterPropertiesSet();

        return factory.getObject();
    }

    @Bean(name = "transactionManager")
    public PlatformTransactionManager txManager() throws Exception {
        return new JpaTransactionManager(entityManagerFactory());
    }

    @Bean
    public PersistenceExceptionTranslator exceptionTranslator() {
        return new HibernateExceptionTranslator();
    }

    @Bean
    public static PropertySourcesPlaceholderConfigurer propertySourcesPlaceholderConfigurer() {
        PropertySourcesPlaceholderConfigurer propertySources = new PropertySourcesPlaceholderConfigurer();
        propertySources.setIgnoreUnresolvablePlaceholders(true);
        propertySources.setLocalOverride(true);
        return propertySources;
    }
}
