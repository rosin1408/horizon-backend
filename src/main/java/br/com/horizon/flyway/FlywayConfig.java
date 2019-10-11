package br.com.horizon.flyway;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import javax.sql.DataSource;
import org.flywaydb.core.Flyway;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class FlywayConfig {

    public static final String DEFAULT_SCHEMA = "public";

    private Logger logger = LoggerFactory.getLogger(getClass());

    @Bean
    public Flyway flyway(DataSource dataSource) {
        logger.info("Migrating default schema ");
        Flyway flyway = new Flyway();
        flyway.setLocations("db/migration/default");
        flyway.setDataSource(dataSource);
        flyway.setSchemas(DEFAULT_SCHEMA);
        flyway.migrate();
        return flyway;
    }

    @Bean
    public Boolean tenantsFlyway(DataSource dataSource) {
        List<String> tenants = Arrays.asList("cliente_1", "cliente_2");
        tenants.forEach(tenant -> {
            String schema = tenant;
            Flyway flyway = new Flyway();
            flyway.setLocations("db/migration/tenants");
            flyway.setDataSource(dataSource);
            flyway.setSchemas(schema);
            flyway.migrate();
        });
        return true;
    }

}
