package br.com.horizon.email;

import java.net.URI;
import java.net.URISyntaxException;
import org.springframework.stereotype.Component;

@Component
public class UriEmailBuilder {

    private static final String URL = "https://api.mailgun.net/v3/${DOMAIN_NAME}/messages";

    public URI build(String domainName) throws URISyntaxException {
        return new URI(URL.replace("${DOMAIN_NAME}", domainName));
    }
}
