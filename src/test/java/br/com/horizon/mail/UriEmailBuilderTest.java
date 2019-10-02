package br.com.horizon.mail;

import static org.assertj.core.api.Assertions.assertThat;

import br.com.horizon.email.UriEmailBuilder;
import java.net.URI;
import java.net.URISyntaxException;
import org.junit.jupiter.api.Test;

public class UriEmailBuilderTest {

    @Test
    public void shouldBuildUriWithDomainName() throws URISyntaxException {
        URI uri = new UriEmailBuilder().build("domain_name");

        assertThat(uri.toString()).isEqualTo("https://api.mailgun.net/v3/domain_name/messages");
    }
}
