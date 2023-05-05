package com.epam.esm;

import com.epam.esm.config.db.impl.DatasourceConnectorImpl;
import com.epam.esm.model.dao.impl.GiftCertificateDaoImpl;
import com.epam.esm.model.dao.impl.TagDaoImpl;
import com.epam.esm.model.dao.interfaces.entity.GiftCertificateDao;
import com.epam.esm.model.dao.interfaces.entity.TagDao;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

import javax.sql.DataSource;

@Configuration
public class ConnectionConfigTest {

    @Bean
    public DataSource dataSource() {
        DriverManagerDataSource dataSource = new DriverManagerDataSource();
        dataSource.setDriverClassName("org.h2.Driver");
        dataSource.setUrl("jdbc:h2:tcp://localhost/~/test;SCHEMA=PUBLIC");
        dataSource.setUsername("sa");
        dataSource.setPassword("");
        return dataSource;
    }

    @Bean
    public JdbcTemplate jdbcTemplate() {
        return new JdbcTemplate(dataSource());
    }

    @Bean
    public Resource setup() {
        return new ClassPathResource("setup.sql");
    }

    @Bean
    public GiftCertificateDao giftCertificateDao() {
        return new GiftCertificateDaoImpl(new DatasourceConnectorImpl(dataSource()));
    }

    @Bean
    public TagDao tagDao() {
        return new TagDaoImpl(new DatasourceConnectorImpl(dataSource()));
    }
}