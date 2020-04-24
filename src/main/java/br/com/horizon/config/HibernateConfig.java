package br.com.horizon.config;

import static org.hibernate.cfg.AvailableSettings.MULTI_TENANT;
import static org.hibernate.cfg.AvailableSettings.MULTI_TENANT_CONNECTION_PROVIDER;
import static org.hibernate.cfg.AvailableSettings.MULTI_TENANT_IDENTIFIER_RESOLVER;

import br.com.horizon.HorizonBackendApplication;
import java.util.HashMap;
import java.util.Map;
import javax.sql.DataSource;
import org.hibernate.MultiTenancyStrategy;
import org.hibernate.context.spi.CurrentTenantIdentifierResolver;
import org.hibernate.engine.jdbc.connections.spi.MultiTenantConnectionProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.orm.jpa.JpaProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.orm.jpa.JpaVendorAdapter;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;

@Configuration
public class HibernateConfig {

    @Autowired
    private JpaProperties jpaProperties;

    @Bean
    public JpaVendorAdapter jpaVendorAdapter() {
        return new HibernateJpaVendorAdapter();
    }

    @Bean
    public LocalContainerEntityManagerFactoryBean entityManagerFactory(DataSource dataSource,
                                                                       MultiTenantConnectionProvider multiTenantConnectionProvider,
                                                                       CurrentTenantIdentifierResolver tenantIdentifierResolver) {

        LocalContainerEntityManagerFactoryBean em = new LocalContainerEntityManagerFactoryBean();
        em.setDataSource(dataSource);
        em.setPackagesToScan(HorizonBackendApplication.class.getPackage().getName());
        em.setJpaVendorAdapter(this.jpaVendorAdapter());

        Map<String, Object> jpaPropertiesMap = new HashMap<>(jpaProperties.getProperties());
        jpaPropertiesMap.put(MULTI_TENANT, MultiTenancyStrategy.SCHEMA);
        jpaPropertiesMap.put(MULTI_TENANT_CONNECTION_PROVIDER, multiTenantConnectionProvider);
        jpaPropertiesMap.put(MULTI_TENANT_IDENTIFIER_RESOLVER, tenantIdentifierResolver);
        em.setJpaPropertyMap(jpaPropertiesMap);

        return em;
    }
}
