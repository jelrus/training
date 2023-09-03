package epam.com.esm.config;

import epam.com.esm.exception.types.UnexpectedException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.orm.hibernate5.HibernateTransactionManager;
import org.springframework.orm.hibernate5.LocalSessionFactoryBean;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.sql.DataSource;
import java.io.IOException;
import java.util.Properties;

/**
 * HibernateConfig is the configuration class, responsible for setting Hibernate properties
 * <p>
 * This class contains configuration for following properties:
 * <ul>
 *     <li>JDBC URL, specifies datasource URL</li>
 *     <li>JDBC Username, specifies datasource username</li>
 *     <li>JDBC Password, specifies datasource password</li>
 *     <li>JDBC Driver, specifies datasource driver</li>
 *     <li>Hibernate Dialect, specifies Hibernate dialect</li>
 *     <li>Envers Audit Table Suffix, specifies Hibernate Envers suffix for audit tables</li>
 *     <li>Envers Store Data at Delete, specifies for Hibernate Envers how to store data at delete operations</li>
 *     <li>Data Source Init, allows/disallows data initialization at application start</li>
 *     <li>Hibernate DDL Auto, specifies DDL mode</li>
 *     <li>Hibernate Show SQL, allows/disallows showing sql queries execution in console</li>
 *     <li>JDBC Max Size, specifies max number of connections for datasource connection pool</li>
 *     <li>JDBC Min Size, specifies min number of connections</li>
 *     <li>JDBC Batch Size, specifies number of rows for update operations</li>
 *     <li>JDBC Fetch Size, specifies number of rows for select operations</li>
 *     <li>Hibernate Lazy Load No Trans, specifies lazy load for Hibernate</li>
 *     <li></li>
 * </ul>
 */
@Configuration
@EnableTransactionManagement
@EnableJpaRepositories("epam.com.esm.persistence.repository")
@EnableScheduling
@EnableJpaAuditing
public class HibernateConfig {

    /**
     * Property name holder for Hibernate Dialect property
     */
    private static final String HIBERNATE_DIALECT = "hibernate.dialect";

    /**
     * Property name holder for Hibernate Envers Audit Tables Suffix property
     */
    private static final String HIBERNATE_SUFFIX = "org.hibernate.envers.audit_table_suffix";

    /**
     * Property name holder for Hibernate Envers Store Data At Delete property
     */
    private static final String HIBERNATE_STORE_AT_DELETE = "org.hibernate.envers.store_data_at_delete";

    /**
     * Property name holder for Data Source Init property
     */
    private static final String HIBERNATE_DATASOURCE_INITIALIZATION = "spring.jpa.defer-datasource-initialization";

    /**
     * Property name holder for Hibernate DDL Auto property
     */
    private static final String HIBERNATE_HBM2DDL_AUTO = "hibernate.hbm2ddl.auto";

    /**
     * Property name holder for Hibernate Show SQL property
     */
    private static final String HIBERNATE_SHOW_SQL = "hibernate.show_sql";

    /**
     * Property name holder for Hibernate Lazy Load No Trans property
     */
    private static final String HIBERNATE_ENABLE_LAZY_LOAD_NO_TRANS = "hibernate.enable_lazy_load_no_trans";

    /**
     * Property name holder for JDBC Max Size property
     */
    private static final String MAX_SIZE = "hibernate.c3p0.max_size";

    /**
     * Property name holder for JDBC Min Size property
     */
    private static final String MIN_SIZE = "hibernate.c3p0.min_size";

    /**
     * Property name holder for JDBC Batch Size property
     */
    private static final String BATCH_SIZE = "hibernate.jdbc.batch_size";

    /**
     * Property name holder for JDBC Fetch Size property
     */
    private static final String FETCH_SIZE = "hibernate.jdbc.fetch_size";

    /**
     * Property value holder, holds packages location for entity manager to scan
     */
    private static final String ENTITY_MANAGER_PACKAGES_TO_SCAN = "epam.com.esm";

    /**
     * Value holder for JDBC URL property
     */
    @Value("${spring.datasource.url}")
    private String jdbcUrl;

    /**
     * Value holder for JDBC Username property
     */
    @Value("${spring.datasource.username}")
    private String username;

    /**
     * Value holder for JDBC Password property
     */
    @Value("${spring.datasource.password}")
    private String password;

    /**
     * Value holder for JDBC Driver property
     */
    @Value("${spring.datasource.driver-class-name}")
    private String driver;

    /**
     * Value holder for Hibernate Dialect property
     */
    @Value("${spring.jpa.properties.hibernate.dialect}")
    private String dialect;

    /**
     * Value holder for Hibernate Envers Audit Tables Suffix property
     */
    @Value("${org.hibernate.envers.audit_table_suffix}")
    private String suffix;

    /**
     * Value holder for Hibernate Envers Store Data At Delete property
     */
    @Value("${org.hibernate.envers.store_data_at_delete}")
    private String storeAtDelete;

    /**
     * Value holder for Data Source Init property
     */
    @Value("${spring.jpa.defer-datasource-initialization}")
    private Boolean datasourceInit;

    /**
     * Value holder for Hibernate DDL Auto property
     */
    @Value("${spring.jpa.hibernate.ddl-auto}")
    private String hbm2ddl;

    /**
     * Value holder for Hibernate Show SQL property
     */
    @Value("${spring.jpa.show-sql}")
    private Boolean showSql;

    /**
     * Value holder for JDBC Max Size property
     */
    @Value("${spring.jpa.properties.hibernate.jdbc.max_size}")
    private String maxSize;

    /**
     * Value holder for JDBC Min Size property
     */
    @Value("${spring.jpa.properties.hibernate.jdbc.min_size}")
    private String minSize;

    /**
     * Value holder for JDBC Batch Size property
     */
    @Value("${spring.jpa.properties.hibernate.jdbc.batch_size}")
    private String batchSize;

    /**
     * Value holder for JDBC Fetch Size property
     */
    @Value("${spring.jpa.properties.hibernate.jdbc.fetch_size}")
    private String fetchSize;

    /**
     * Value holder for Hibernate Lazy Load No Trans property
     */
    @Value("${spring.jpa.properties.hibernate.enable_lazy_load_no_trans}")
    private String lazyLoad;

    /**
     * Creates and configures datasource bean by setting driver, url, username and password properties
     *
     * @return {@code DataSource} configured datasource
     */
    @Bean
    public DataSource dataSource() {
        DriverManagerDataSource dataSource = new DriverManagerDataSource();
        dataSource.setDriverClassName(driver);
        dataSource.setUrl(jdbcUrl);
        dataSource.setUsername(username);
        dataSource.setPassword(password);
        return dataSource;
    }

    /**
     * Creates and configures LocalSessionFactoryBean bean by setting datasource, packages for scan,
     * Hibernate properties
     * <p>
     * May throw UnexpectedException if IOException occurs due Hibernate incorrect configuration
     *
     * @return {@code LocalSessionFactoryBean} configured local session factory bean
     */
    @Bean(name="entityManagerFactory")
    public LocalSessionFactoryBean sessionFactoryBean() {
        LocalSessionFactoryBean sessionFactoryBean = new LocalSessionFactoryBean();
        sessionFactoryBean.setDataSource(dataSource());
        sessionFactoryBean.setPackagesToScan(ENTITY_MANAGER_PACKAGES_TO_SCAN);
        sessionFactoryBean.setHibernateProperties(hibernateProperties());

        try {
            sessionFactoryBean.afterPropertiesSet();
        } catch (IOException e) {
            throw new UnexpectedException("Properties were set incorrectly");
        }

        return sessionFactoryBean;
    }

    /**
     * Creates and configures HibernateTransactionManager bean by setting session factory bean
     *
     * @return {@code HibernateTransactionManager} configured Hibernate transaction manager
     */
    @Bean
    public HibernateTransactionManager transactionManager() {
        HibernateTransactionManager manager = new HibernateTransactionManager();
        manager.setSessionFactory(sessionFactoryBean().getObject());
        return manager;
    }

    /**
     *  Generates properties object for Hibernate configuring
     *
     * @return {@code Properties} generated properties object for Hibernate
     */
    private Properties hibernateProperties() {
        Properties properties = new Properties();
        properties.put(HIBERNATE_DIALECT, dialect);
        properties.put(HIBERNATE_HBM2DDL_AUTO, hbm2ddl);
        properties.put(HIBERNATE_SHOW_SQL, showSql);
        properties.put(HIBERNATE_ENABLE_LAZY_LOAD_NO_TRANS, lazyLoad);
        properties.put(MAX_SIZE, maxSize);
        properties.put(MIN_SIZE, minSize);
        properties.put(BATCH_SIZE, batchSize);
        properties.put(FETCH_SIZE, fetchSize);
        properties.put(HIBERNATE_SUFFIX, suffix);
        properties.put(HIBERNATE_STORE_AT_DELETE, storeAtDelete);
        properties.put(HIBERNATE_DATASOURCE_INITIALIZATION, datasourceInit);
        return properties;
    }
}