package com.goku.gokubackend.integration;

import com.goku.gokubackend.domain.repository.*;
import com.goku.gokubackend.infrastructure.mysql.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.jdbc.support.JdbcTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.sql.DataSource;

@PropertySource("database.properties")
@EnableTransactionManagement
public class TestPersistenceConfig {

    @Autowired
    private Environment env;

    @Bean
    public DataSource dataSource() {
        DriverManagerDataSource dataSource = new DriverManagerDataSource();
        dataSource.setDriverClassName(env.getProperty("jdbc.driverClassName"));
        dataSource.setUrl(env.getProperty("jdbc.url"));
        dataSource.setUsername(env.getProperty("jdbc.user"));
        dataSource.setPassword(env.getProperty("jdbc.pass"));

        return dataSource;
    }

    @Bean("transactionManager")
    public PlatformTransactionManager transactionManager(DataSource dataSource) {
        return new JdbcTransactionManager(dataSource);
    }

    @Bean
    public JdbcTemplate jdbcTemplate(DataSource dataSource) {
        return new JdbcTemplate(dataSource);
    }

    @Bean
    public UserRepository userRepo(JdbcTemplate jdbc) {
        return new MySQLUserRepository(jdbc);
    }

    @Bean
    public CityRepository cityRepository(JdbcTemplate jdbcTemplate) {
        return new MySQLCityRepository(jdbcTemplate);
    }

    @Bean
    public AddressRepository addressRepository(JdbcTemplate jdbcTemplate, CityRepository cityRepository) {
        return new MySQLAddressRepository(cityRepository, jdbcTemplate);
    }

    @Bean
    public CustomerAddressRepository customerAddressRepository(JdbcTemplate jdbcTemplate, AddressRepository addressRepository) {
        return new MySQLCustomerAddressRepository(jdbcTemplate, addressRepository);
    }

    @Bean
    public CustomerRepository customerRepository(JdbcTemplate jdbcTemplate,
                                                 AddressRepository addressRepository,
                                                 CustomerAddressRepository customerAddressRepository) {
        return new MySQLCustomerRepository(jdbcTemplate, addressRepository, customerAddressRepository);
    }


}
