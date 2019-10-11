package br.com.horizon.config;

import org.hibernate.context.spi.CurrentTenantIdentifierResolver;
import org.springframework.stereotype.Component;

@Component
public class TenantSchemaResolver implements CurrentTenantIdentifierResolver {

    private static final String DEFAULT_SCHEMA ="public";


    @Override
    public String resolveCurrentTenantIdentifier() {
        String tenant = TenantContext.getTenantSchema();

        if (tenant != null) {
            return tenant;
        } else {
            return DEFAULT_SCHEMA;
        }
    }

    @Override
    public boolean validateExistingCurrentSessions() {
        return true;
    }
}
