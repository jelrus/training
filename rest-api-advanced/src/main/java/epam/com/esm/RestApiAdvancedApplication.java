package epam.com.esm;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.autoconfigure.jdbc.DataSourceTransactionManagerAutoConfiguration;
import org.springframework.boot.autoconfigure.orm.jpa.HibernateJpaAutoConfiguration;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;


/**
 * RestApiAdvancedApplication class is the main class of the application
 */
@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class,
                                  DataSourceTransactionManagerAutoConfiguration.class,
                                  HibernateJpaAutoConfiguration.class})
@EnableJpaAuditing
public class RestApiAdvancedApplication extends SpringBootServletInitializer {

    /**
     * Starts application
     *
     * @param args requested arguments
     */
    public static void main(String[] args) {
        SpringApplication.run(RestApiAdvancedApplication.class, args);
    }
}